package com.weloftlabs.superagro.global;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by ymedia on 3/11/15.
 */
public class MyApp extends Application {


    private final String BM_APP_ROUTE = "http://superblue.mybluemix.net";
    private final String BM_GUID = "0a62c9d1-8eaa-4189-b13f-8a938bc0d823";

    @Override
    public void onCreate() {
        super.onCreate();

        initSDKs();

    }

    private void initSDKs() {
        FacebookSdk.sdkInitialize(getApplicationContext());

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "KzRkMP3NiqCFyf9Wl2xsU2cLOmdj95Fe0UlWyq6O", "phpNeS0sXFlJZepSFq5guQkXlafChHgeEEBQf690");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

}
