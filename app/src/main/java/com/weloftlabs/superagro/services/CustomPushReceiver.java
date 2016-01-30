package com.weloftlabs.superagro.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.weloftlabs.superagro.R;
import com.weloftlabs.superagro.manager.SharedPreferenceManager;
import com.weloftlabs.superagro.utils.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Venkatesh Devale on 03-10-2015.
 */

public class CustomPushReceiver extends ParsePushBroadcastReceiver {
    private final String TAG = CustomPushReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private Intent parseIntent;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        Log.e("wel", "Push received: ");

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            Log.e(TAG, "Push received: " + json);
            parseIntent = intent;
            parsePushJson(context, json);
        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(final Context context, final JSONObject json) {
        try {
            final String value1 = json.getString("value1");
            final String value2 = json.getString("value2");

            showNotificationForJson(context, value1, value2);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    private void showNotificationForJson(Context context, String value1, String value2) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

//        Intent intent = new Intent(context, VictimUserDetailsActiviy.class);
//        intent.putExtra("userId", userId);

//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification notification;

        if (Build.VERSION.SDK_INT < 16) {
            notification = new Notification.Builder(context).setContentTitle(value1 + " is value one")
                    .setContentText("Tap to see the user status and location")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .getNotification();
        } else {
            notification = new Notification.Builder(context)
                    .setContentTitle(value1 + " is value one")
                    .setContentText("Tap to see the user status and location")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setSound(soundUri)

                    .build();

//            .setContentIntent(pendingIntent)
//                    .addAction(0, "Check user status", pendingIntent)
        }

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);


    }
}