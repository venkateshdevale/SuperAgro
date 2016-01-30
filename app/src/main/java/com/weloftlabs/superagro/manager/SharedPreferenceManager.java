package com.weloftlabs.superagro.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.weloftlabs.superagro.global.Constants;


public class SharedPreferenceManager {
    private static SharedPreferenceManager sSharedPreferenceManager;
    private SharedPreferences mSharedPreference;

    private SharedPreferenceManager(Context context) {
        mSharedPreference = context.getSharedPreferences(Constants.SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceManager newInstance(Context context) {
        if (sSharedPreferenceManager == null) {
            sSharedPreferenceManager = new SharedPreferenceManager(context);
        }

        return sSharedPreferenceManager;
    }

    public void saveValue(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSavedValue(String key) {
        return mSharedPreference.getString(key, "");
    }

}
