package com.fihou.babicare.Service;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
/**
 * Created by TT99-PC01 on 2/3/2017.
 */

public class myFirebaseInstanceIDService extends FirebaseInstanceIdService {
    final String tokenPreferenceKey = "fcm_token";

    final static String infoTopicName = "info";
    @Override
    public void onTokenRefresh() {

        String token= FirebaseInstanceId.getInstance().getToken();
        Log.i("Refresh Token",token );

        //////
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(tokenPreferenceKey, FirebaseInstanceId.getInstance().getToken()).apply();

        FirebaseMessaging.getInstance().subscribeToTopic(infoTopicName);
    }


}
