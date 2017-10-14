package com.example.android.mytodo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

/**
 * Created by shruti on 05-10-2017.
 */



public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("Todo 1 Title");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("Todo 1 description");

        Intent intent1 = new Intent(context,MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,2,intent1,0);

        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);


//        if(Build.VERSION.SDK_INT >= 26){
//
//            NotificationChannel channel = new NotificationChannel("channel_1","Urgent", NotificationManager.IMPORTANCE_HIGH);
//            builder.setChannelId("channel_1");
//
//
//        }



        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1,notification);

    }
}
