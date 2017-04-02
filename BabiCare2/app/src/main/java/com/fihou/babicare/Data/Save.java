package com.fihou.babicare.Data;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.fihou.babicare.Activitys.LogInActivity;
import com.fihou.babicare.Network.Connection;
import com.fihou.babicare.Network.MessageKey;
import com.fihou.babicare.Network.MessagesID;
import com.fihou.babicare.Network.NetworkStateReceiver;
import com.fihou.babicare.config.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Administrator on 10/03/2017.
 */

public class Save {
    public static JSONObject lastMessage;
    public static ActivityInterface currentView = null;
    public static String titlerro = "";
    public static String msgErro = "";
    public static ArrayList<TypeUser> listtype = new ArrayList<>();
    private static Connection connect = null;
    public static long ping_times = 0;
    public static boolean isReconnect = false;
    public static String sessionId = "";
    public static String networkTypeName;
    public static int networkType;
    public static boolean BUG_LAG_ENABLE = false;
    public static int signalLevelInternet;
    public static long last_times = 0;
    public static boolean inGame = false;
    public static boolean isForceApp = false;
    public static UserInfos userInfos;
    public static Chuongtrinh chuongtrinh;
    public static ArrayList<ChitietChuongtrinh> listchitietchuongtrinh;




    public static Connection getConnect() {
        return connect;
    }

    public static void setConnect(Connection connect) {
        Save.connect = connect;
    }

    public static boolean isConnectingToInternet(Context... context) {
        if (currentView == null) {
            return true;
        }
        Context mContext = null;
        if (context.length > 0) {
            mContext = context[0];
        } else {
            mContext = currentView.getActivity();
        }
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        networkTypeName = info[i].getTypeName();
                        networkType = info[i].getType();
                        return true;
                    }
        }
        return false;
    }

    public static String serverMsg = "";

    public static void notify(final String msg, final boolean adminMsg, final boolean... serverDisconnect) {
        if (currentView == null) return;
        currentView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adminMsg) {
                    if (!inGame) {
                        serverMsg = msg;
                        return;
                    }
                    MiDialog.alert("Thông báo", msg, currentView.getActivity());
                } else {
                    if (Save.isForceApp) {
                        Toast toast = Toast.makeText(currentView.getActivity(), msg, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                    }

                    //nhận được thông báo disconnect từ server
                    if (serverDisconnect.length > 0 && serverDisconnect[0]) {
                        closeConnection();
                        if (Save.isForceApp) {
                            Intent intent = new Intent(currentView.getActivity(), LogInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            currentView.getActivity().startActivity(intent);
                            currentView.getActivity().finish();
                        }
                    }
                }
            }
        });
    }

    public static void closeConnection() {
        if (connect != null) {
            connect.close();
            connect = null;
        }
    }

    public synchronized static void readTimeOut() {
        if (currentView == null) return;
        currentView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentView.readTimeOut();
            }
        });
    }

    public static void manualReconnect() {
        if (currentView == null) return;
        Save.last_times = System.currentTimeMillis();
        Save.isReconnect = false;
        currentView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NetworkStateReceiver.autoReconnect(currentView.getActivity(), isConnectingToInternet());
                    }
                }, 300L);
            }
        });
    }

    public synchronized static void broadcastActivityListener(final Vector<PrimitiveMessage> messages, final boolean... disconnected) {
        if (currentView == null) return;
        currentView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    currentView.alertFinish(messages);
                    if ((disconnected.length > 0 && disconnected[0]) || inGame && (connect == null || !connect.isConnected())) {
                        if (Config.RECONNECT_ENABLE) {
                            closeConnection();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sendJSONObject(JSONObject aMsg, boolean... isParallel) {
        //Lưu lại message cuối cùng mà user gửi đi. Không lưu lại các tin như:
        //PING, LOAD_CONFIG, LOGIN, REGISTER_ACCOUNT
        //Trong trường hợp gửi lỗi do một lý do nào đó -> reconnect sẽ tự động gửi message này đi
        try {
            if (aMsg.has(MessageKey.mid)) {
                if (lastMessage != null) {
                    lastMessage = null;
                }
                int mid = aMsg.getInt(MessageKey.mid);
                if (mid != MessagesID.PING && mid != MessagesID.LOAD_CONFIG
                        && mid != MessagesID.LOGIN && mid != MessagesID.REGISTER_ACCOUNT && mid != MessagesID.GET_APP_CONFIG) {
                    lastMessage = aMsg;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Thực thi lệnh gửi nếu có thể
        if (connect != null) {
            if (isConnectingToInternet() && connect.isConnected()) {
                connect.addRequestMessage(aMsg, isParallel);

            } else {
                connect.close();
            }
        }
    }
}
