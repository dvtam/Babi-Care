package com.fihou.babicare.Network;

import android.content.Context;
import android.util.Log;

import com.fihou.babicare.Data.BusinessMessage;
import com.fihou.babicare.Data.DataOutput;
import com.fihou.babicare.Data.PrimitiveMessage;
import com.fihou.babicare.Data.Save;
import com.fihou.babicare.Data.ZlibCompressionUtil;
import com.fihou.babicare.config.Config;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by Administrator on 10/03/2017.
 */

public class Connection {
    private static final int MAX_WRITE_BUFFER = 1 * 1024;
    private static final int MAX_READ_BUFFER = 1 * 1024;
    private static final int READ_BUFFER = 100;
    private static final int SOCKET_TIMEOUT = 30 * 1000;    // milisecondsde
    private static final long READ_TIMEOUT = 15 * 1000;    // miliseconds
    private static final long READ_TIME_DELAY = 60;            // miliseconds
    private Socket mconnection;
    private DataOutputStream mRequestStream;
    private DataInputStream mResponseStream;
    private Context context;
    private String mHost;
    private int mPort;
    private Thread mReaderThread;
    private boolean isWritingData = false;
    private boolean mustClose = false;
    private long sendTime;
    private long responseTime;
    public static int signalLevelInternet;

    public Connection(Context context, String mHost, int mPort) {
        this.context = context;
        this.mHost = mHost;
        this.mPort = mPort;
    }

    public synchronized void connect() {
        if (!isConnected() && createSocket()) {
            startReaderThread();
        }
    }

    public boolean isConnected() {
        if (mconnection != null && mconnection.isConnected() && !mconnection.isClosed()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean createSocket() {
        try {
            isWritingData = false;
            mustClose = false;
            if (mconnection != null) {
                mconnection = null;
            }

            mconnection = new Socket(mHost, mPort);
            mconnection.setSendBufferSize(MAX_WRITE_BUFFER);
            mconnection.setReceiveBufferSize(MAX_READ_BUFFER);
            mconnection.setSoLinger(true, 15);
            mconnection.setSoTimeout(SOCKET_TIMEOUT);

            if (mRequestStream != null) {
                mRequestStream.close();
                mRequestStream = null;
            }
            mRequestStream = new DataOutputStream(mconnection.getOutputStream());

            if (mResponseStream != null) {
                mResponseStream.close();
                mResponseStream = null;
            }
            mResponseStream = new DataInputStream(mconnection.getInputStream());
            Save.ping_times = System.currentTimeMillis();
        } catch (Throwable t) {
            t.printStackTrace();
            Save.titlerro = "Thông báo";
            if (Save.isConnectingToInternet(context)) {
                Save.msgErro = "Server đang bảo trì hoặc kết nối mạng không ổn định. Vui lòng thử lại sau.";
            } else {
                Save.msgErro = "Vui lòng bật kết nối mạng trước khi sử dụng trò chơi.";
            }
            mconnection = null;
            Save.closeConnection();
            Save.broadcastActivityListener(null, true);
            return false;
        }
        return true;
    }

    private void startReaderThread() {
        Log.d(Config.Tag, "start Socket");
        ReaderResponse reader = new ReaderResponse();
        if (mReaderThread != null) {
            mReaderThread = null;
        }
        mReaderThread = new Thread(reader);
        mReaderThread.start();
    }

    public void close() {
        mustClose = true;
    }

    private class PingRunnable extends TimerTask {
        int i = 0;

        @Override
        public void run() {
            Log.d(Config.Tag, "--- PING : " + i++);
            Save.sendJSONObject(BusinessMessage.pingServer());
        }


    }

    private Timer mPingTimer;
    private PingRunnable mPingRunnable;

    private void stopPing() {
        if (mPingTimer != null) {
            mPingTimer.cancel();
            mPingTimer = null;
            mPingRunnable = null;
            Log.d(Config.Tag, "Ping stop");
        }
    }

    private void closeThread() {
        Log.d(Config.Tag, "Close Socket");
        if (isConnected()) {
            try {
                if (mRequestStream != null) {
                    mRequestStream.close();
                    mRequestStream = null;
                }
                if (mResponseStream != null) {
                    mResponseStream.close();
                    mResponseStream = null;
                }
                if (mconnection != null) {
                    mconnection.close();
                    mconnection = null;
                }
                stopPing();
                if (mReaderThread != null) {
                    mReaderThread.join();
                    mReaderThread = null;
                }
            } catch (Throwable t) {
                t.printStackTrace();

            }
        }
    }
    int lastMid = 0;

    public synchronized void addRequestMessage(JSONObject aMsg, boolean... isParallel) {
        if (aMsg != null && (!isWritingData || (isParallel.length > 0 && isParallel[0]))) {
            try {
                lastMid = aMsg.getInt(MessageKey.mid);
//                Log.d(Config.Tag, "SEND CODE: " + aMsg.getInt(MessageKey.mid));
                isWritingData = true;
                packRequest(BinaryMessage.processReq(aMsg));
            } catch (Exception e) {
                isWritingData = false;
                close();
                Save.readTimeOut();
                Save.manualReconnect();
                e.printStackTrace();
            }
        }
    }
    public synchronized void packRequest(byte[] request) throws Exception {
        synchronized (mRequestStream) {
            Log.d(Config.Tag, "send data: " + request.length);
            int dataSize = request.length + Save.sessionId.getBytes("UTF-8").length + 2;

//            int dataSize = request.length + new String("").getBytes("UTF-8").length + 2;
            DataOutput op = new DataOutput();
            //Test hiện tượng GIẬT, LAG
            if (Save.BUG_LAG_ENABLE) {
                op.writeByte((byte) -125);
                op.writeShort((short) (dataSize + 14));
                op.writeLong(sendTime);
                op.writeInt((int) responseTime);
                op.writeShort((short) Save.signalLevelInternet);
            } else {
                //cờ để cho server biết là xử lý theo binary
                op.writeByte((byte) -126);
                op.writeShort((short) dataSize);
            }

            op.writeUTF(Save.sessionId);
//            op.writeUTF("");
            op.write(request);
            byte[] out = op.getBytes();
            if (out != null && out.length > 0) {
                mRequestStream.write(out);
                mRequestStream.flush();
            }
            sendTime = System.currentTimeMillis();
            op.close();
        }
    }
    public class ReaderResponse implements Runnable {

        @SuppressWarnings("static-access")
        public void run() {
            BlockData pkgBlock = new BlockData();

            while (isConnected()) {
                if (mustClose || !Save.isConnectingToInternet()) {
                    mustClose = false;
                    pkgBlock.close();
                    closeThread();
                    return;
                }
                try {
                    Thread.currentThread().sleep(100L);
                    int pkgSize = -1;
                    byte compress = -1;
                    long mTimeOut = 0;
                    if (pkgBlock.isStarting()) {
                        try {
                            if (mResponseStream.available() >= 5) {
//							if (mResponseStream.available() >= 2) {
                                pkgSize = mResponseStream.readShort();
                                compress = (byte) mResponseStream.read();
                                pkgBlock.setBlockSize(pkgSize - 1);
                                pkgBlock.setIsCompress(compress == 1);
                            } else if (mResponseStream.available() == 3) {
                                isWritingData = false;
                                byte[] data = new byte[3];
                                mResponseStream.read(data);
//                                Log.d(Config.Tag, "==== Response PING: " + data.toString());
                            }
                        } catch (Throwable t) {
                            continue;
                        }
                    }
                    // get the current pkg size
                    pkgSize = pkgBlock.getBlockSize();
                    // decode this package
                    if (pkgSize > 0) {
                        while (!pkgBlock.canDecode()) {
                            int blkAvail = pkgBlock.size();
                            int needRead = pkgSize - blkAvail;
                            if (needRead > READ_BUFFER) needRead = READ_BUFFER;
                            byte[] partialData = new byte[needRead];
                            int realRead = mResponseStream.read(partialData);

                            // bi delay ko doc dc. sleep
                            if (realRead == 0) {
                                try {
                                    Thread.sleep(READ_TIME_DELAY);
                                    mTimeOut += READ_TIME_DELAY;
                                    if (mTimeOut >= READ_TIMEOUT) {
                                        mTimeOut = 0;
                                        Log.d(Config.Tag, "DISCONNECTED");
                                        //notify disconnect
                                        Save.readTimeOut();
                                        try {
                                            //tracking delay ko doc dc DATA
                                            if (Config.GOOGLE_ANALYTICS_ENABLE) {
//												Tracker tracker = GoogleAnalytics.getInstance(context).getTracker(Constant.APP_GAID);
//												tracker.send(MapBuilder.createEvent("readData", "internet= "+Save.isConnectingToInternet(context), null, null).build());
                                            }
                                        } catch (Exception e) {
                                        }
                                        break;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                pkgBlock.put(partialData, 0, realRead);
                            }
                            if (pkgBlock.canDecode()) {
                                if (Save.BUG_LAG_ENABLE && isWritingData && context != null) {
                                    responseTime = (System.currentTimeMillis() - sendTime);
//                                    Save.signalLevelInternet = Utility.getSignalLevelInternet(context);
                                }
                                isWritingData = false;
                                processBinaryResponses(pkgBlock.getAllMessages());
                                pkgBlock.reset();
                                break;
                            }
                        }
                    } else {
                        pkgBlock.reset();
                    }
                } catch (Exception et) {
                    et.printStackTrace();
                }
                if (isWritingData && sendTime > 0 && (System.currentTimeMillis() - sendTime) > READ_TIMEOUT) {
                    if (Save.BUG_LAG_ENABLE) {
                        responseTime = -1;
                    }
                    sendTime = 0;
                    isWritingData = false;
                    Save.readTimeOut();
                }
            }
        }





        public synchronized void processBinaryResponses(Vector<PrimitiveMessage> msg) throws Exception {
            for (PrimitiveMessage aMessage : msg) {
//				Log.d(Config.Tag, "RESPONSE CODE: " + aMessage.getMessageID());
                Save.last_times = System.currentTimeMillis();
                switch (aMessage.getMessageID()) {
//                    case MessagesID.LOAD_CONFIG:
//                        ProcessMessageLogin.parserLoadConfig(aMessage, context);
//                        break;
//                    case MessagesID.GET_SERVER_DATA:
//                        processDisconnect(aMessage);
//                        break;
//				case MessagesID.GET_USER_DATA:
//					processUserData(aMessage);
//					break;
                }
            }
            if (msg != null && msg.size() > 0) {
                Save.broadcastActivityListener(msg);
            }
        }
    }

    private class BlockData {

        final int INIT_SIZE = -1;
        int mBlockSize;
        DataOutput mBufferData;
        private boolean isCompress;

        public BlockData() {
            mBlockSize = INIT_SIZE;
            mBufferData = new DataOutput();
        }

        public void setBlockSize(int aBlockSize) {
            mBlockSize = aBlockSize;
        }

        public int getBlockSize() {
            return mBlockSize;
        }

        public boolean isStarting() {
            return (mBlockSize < 0);
        }

        public void put(byte[] aPartialData) throws Throwable {
            mBufferData.write(aPartialData);
        }

        public void put(byte[] aPartialData, int aOff, int aFrag) throws Exception {
            mBufferData.write(aPartialData, aOff, aFrag);
        }

        public void put(int aPartialData) throws Throwable {
            mBufferData.writeInt(aPartialData);
        }

        public void reset() {
            mBlockSize = INIT_SIZE;
            mBufferData.reset();
        }

        public void close() {
            try {
                if (mBufferData != null) {
                    mBufferData.close();
                }
            } catch (Exception e) {
            }
        }

        public boolean canDecode() throws Exception {
            return (mBlockSize == mBufferData.size());
        }

        public byte[] getInternalData() throws Exception {
            return mBufferData.getBytes();
        }

        public int size() throws Exception {
            return mBufferData.size();
        }

        public boolean isIsCompress() {
            return isCompress;
        }

        public void setIsCompress(boolean isCompress) {
            this.isCompress = isCompress;
        }

        public Vector<PrimitiveMessage> getAllMessages() {
            Vector<PrimitiveMessage> vecMs = new Vector<PrimitiveMessage>();
            int readLen = 0;
            try {
                byte[] data;
                if (isCompress) {
                    data = ZlibCompressionUtil.gzipDecompress(getInternalData());
                } else {
                    data = getInternalData();
                }
                PrimitiveMessage msg = null;
                while (readLen < data.length) {
                    msg = new PrimitiveMessage();
                    short msgLen = (short) (((data[0 + readLen] & 0xFF) << 8) + (data[1 + readLen] & 0xFF));
                    short mid = (short) (((data[2 + readLen] & 0xFF) << 8) + (data[3 + readLen] & 0xFF));
                    byte[] resBody = new byte[msgLen - 4];
                    System.arraycopy(data, 4 + readLen, resBody, 0, resBody.length);
                    msg.setMessageID(mid);
                    Log.d(Config.Tag, "RESPONSE CODE: " + mid);
                    msg.setData(resBody);
                    readLen += msgLen;
                    vecMs.addElement(msg);

                }
                return vecMs;
            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }
        }
    }
}
