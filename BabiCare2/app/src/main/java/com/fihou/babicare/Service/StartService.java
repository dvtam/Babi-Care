package com.fihou.babicare.Service;

import android.content.Context;
import android.util.Log;

import com.fihou.babicare.Data.BusinessMessage;
import com.fihou.babicare.Data.Save;
import com.fihou.babicare.Network.Connection;
import com.fihou.babicare.config.Config;

/**
 * Created by Administrator on 10/03/2017.
 */

public class StartService implements Runnable {
    public static final int PING_RECONNECT = -1;
    public static final int LOGIN = 0;
    public static final int REGIST = 1;
    public static final int LOAD_CONFIG = 2;

    Context context;
    private String user;
    private String pass;
    private String fullName;
    private String email;
    private String phone;

    private int mode = 0;
    private int loginType;
    private boolean facebookLogin;
    @Override
    public void run() {
        try {
            if(mode==PING_RECONNECT){
                Thread.sleep(300L);
            }
            if (Save.getConnect() == null) {
                Connection connect = new Connection(context, Config.host, Config.port);
                Save.setConnect(connect);
            }
            Save.getConnect().connect();
            if (Save.getConnect().isConnected()) {
                switch (mode) {
                    case PING_RECONNECT:
                        //gửi tín hiệu PING để báo cho server biết là client đã RECONNECTED trở lại;
                        Log.d(Config.Tag, "PING_RECONNECT");
                        int time = (int)(Save.ping_times - Save.last_times);
                        if(time > 0){
                            Save.sendJSONObject(BusinessMessage.pingReConnectServer(time));
                        }else{
                            Save.sendJSONObject(BusinessMessage.pingServer());
                        }
                        //Khi reconnect thành công
                        //->gửi lại message cuối cùng bị lỗi nếu có thể :D
                        if(Save.lastMessage!=null){
                            Save.sendJSONObject(Save.lastMessage, true);
                            Save.lastMessage = null;
                            Save.isReconnect = true;
                        }
                        break;
                    case LOGIN:
                        Log.d(Config.Tag, "LOGIN");
                        StringBuffer deviceInfo = new StringBuffer();
//                        deviceInfo.append("androidName=" + DeviceInfo.getAndroidName());
//                        deviceInfo.append("&manufacture=" + DeviceInfo.getManufacturer());
//                        deviceInfo.append("&model=" + DeviceInfo.getModel());
//                        deviceInfo.append("&carrierName=" + DeviceInfo.getCarrierName(context));
//                        deviceInfo.append("&phoneNumber=" + DeviceInfo.getPhone(context));
//                        deviceInfo.append("&networkType=" + DeviceInfo.getNetworkType(context));
//                        deviceInfo.append("&screenSize=" + DeviceInfo.getScreenSize(context));
//                        String referrer =  SharePrefrenceUtil.getAppRefer(this.context);
//                        Log.d("referrer:", referrer);
//                        Save.sendJSONObject(User.login(user, pass,
//                                Utility.getVersionName(context, Constant.CURRENT_VERSION),
//                                deviceInfo.toString(),
//                                facebookLogin, loginType, fullName, referrer));
                        break;
                    case REGIST:
//                        String referrer1 =  SharePrefrenceUtil.getAppRefer(this.context);
//                        Save.sendJSONObject(User.regist(user, pass, email, phone, Utility.getVersionName(context, Constant.CURRENT_VERSION), referrer1));
                        break;
                    case LOAD_CONFIG:
//                        Save.sendJSONObject(User.loadConfig(SharePrefrenceUtil.getConfigVer(context)));
                        break;
                }
            }
        } catch (Exception e) {
            Save.readTimeOut();
            e.printStackTrace();
        }
        //reset mode
        mode = 0;
    }
    public StartService() {
        mode = PING_RECONNECT;
        //đóng socket nếu có thể
        Save.closeConnection();
    }

    public void start() {
        new Thread(this).start();
    }
}
