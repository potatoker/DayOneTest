package com.raymond.retrofittest.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.raymond.retrofittest.R;
import com.raymond.retrofittest.ui.MyNewDaysActivity;

/**
 * Created by raymond on 8/17/16.
 */
public class AlarmReceiver extends BroadcastReceiver{

    public final static String KEY_COMMAND = "command";
    public final static String COMMAND_TAKE_PHOTO = "take_photo";

    @Override
    public void onReceive(Context context, Intent intent) {
        generateNotification(context);

    }

    private static void generateNotification(Context context) {

        Intent notificationIntent = new Intent(context, MyNewDaysActivity.class);
        notificationIntent.putExtra(KEY_COMMAND, COMMAND_TAKE_PHOTO);

        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = context.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_add_photo_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.planet_green))

                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("take Photo now!");
        Notification n = builder.getNotification();
        n.defaults |= Notification.DEFAULT_ALL;
        nm.notify(0, n);

    }
}
