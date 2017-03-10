package com.fihou.babicare.Network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.fihou.babicare.Data.Save;
import com.fihou.babicare.Service.StartService;
import com.fihou.babicare.config.Config;


//import com.google.analytics.tracking.android.GoogleAnalytics;
//import com.google.analytics.tracking.android.MapBuilder;
//import com.google.analytics.tracking.android.Tracker;

public class NetworkStateReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		if(Config.RECONNECT_ENABLE && Save.isForceApp){
			if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
				if(networkInfo!=null){
					if(networkInfo.getState() == NetworkInfo.State.CONNECTED){
						autoReconnect(context, true);
					}else if(networkInfo.getState() == NetworkInfo.State.DISCONNECTED){
						autoReconnect(context, false);
					}
				}else{
					autoReconnect(context, !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false));
				}
			}
		}
	}
	
	public static void autoReconnect(Context context, boolean isConnectedToInternet){
		if(!Save.isForceApp) return;
		if(!isConnectedToInternet){
			Save.isReconnect = false;
			Save.last_times = System.currentTimeMillis();
		}else if(isConnectedToInternet && !Save.isReconnect){
			if(Save.last_times > 0 && (System.currentTimeMillis() - Save.last_times) >= Config.timeOutReconnect){
				
				//trường hợp User bấm phím HOME mà cờ Save.isForceApp=TRUE + user bị mất kết nối
				//->thời gian chờ báo kết nối lại với server >= @link {Config.maxTimeOutReconnect} 
				//đến lúc user có kết nối trở lại sẽ cho exitAPP mà không thông báo gì tới user đó.
				if((System.currentTimeMillis() - Save.last_times) >= Config.maxTimeOutReconnect){
					Save.isForceApp = false;
				}
				Save.notify("Kết nối bị ngắt do hết thời gian của phiên", false, true);
			}else{
				Save.isReconnect = true;
				new StartService().start();
				if(Config.GOOGLE_ANALYTICS_ENABLE){
//					Tracker tracker = GoogleAnalytics.getInstance(context).getTracker(Constant.APP_GAID);
//					tracker.send(MapBuilder.createEvent("reConnect", DeviceInfo.getDeviceID(context, "123456478"),
//							"&version= "+Utility.getVersionName(context, Constant.CURRENT_VERSION), null).build());
				}
			}
		}
	}
}