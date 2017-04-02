package com.fihou.babicare.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;

import com.fihou.babicare.Activitys.MainActivity;
import com.fihou.babicare.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by TT99-PC01 on 2/3/2017.
 */

public class myFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if(remoteMessage.getFrom().equals("/topics/" + myFirebaseInstanceIDService.infoTopicName)){
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("content-text"),remoteMessage.getData().get("id"));
        }

//        showNotification(remoteMessage.getNotification().getBody());
    }

    private void showNotification(String title,String mess, String id) {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Set large icon
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float multiplier=metrics.density/3f;//fix icon cho đẹp
        Bitmap icon = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.babicare_logo);
        icon=Bitmap.createScaledBitmap(icon, (int)(icon.getWidth()*multiplier), (int)(icon.getHeight()*multiplier), false);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(mess)
                .setSound(defaultsound)
                .setLargeIcon(icon)
                .setOngoing(true)
                .setSmallIcon(R.drawable.babicare_icon)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(id), builder.build());

    }
}
