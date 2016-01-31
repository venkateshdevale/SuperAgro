package com.weloftlabs.superagro.util;

import android.content.Context;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by idea on 31-01-2016.
 */
public class Pusher {

    String one = "OMG! SuperAgro has found significant suspicious changes in the neighbourhood.";
    String two = "Alert! No rain for another two months. Save on water";
    String three = "Best time to make it fertile.";
    String four = "SuperAgro says he finds growth is slow in your fields. Try fertilizer.";

    public static void sendPush(final Context context, String value1, String value2) {
        ParsePush push = new ParsePush();
        JSONObject data = new JSONObject();

        try {
            data.put("value1", value1);
            data.put("value2", value2);
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        push.setData(data);
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "Failed to send push notification to your family members", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "Push notification sent", Toast.LENGTH_LONG).show();

                    Toast.makeText(context, "Keep this screen on to track you in real time", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
