package com.weloftlabs.superagro.ui.login;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.weloftlabs.superagro.manager.SharedPreferenceManager;
import com.weloftlabs.superagro.ui.MainActivity;
import com.weloftlabs.superagro.R;
import com.weloftlabs.superagro.utils.BlurBuilder;
import com.weloftlabs.superagro.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private LoginButton mLoginButton;
    private CallbackManager callbackManager;
    private Handler mHandler;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (AccessToken.getCurrentAccessToken() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        mLoginButton = (LoginButton) findViewById(R.id.login_button);
        mLoginButton.setReadPermissions("user_friends");

        LoginManager.getInstance();

        callbackManager = CallbackManager.Factory.create();


        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                Intent intent = new Intent(LoginActivity.this, UserDetailsActivity.class);
                startActivity(intent);

                SharedPreferenceManager.newInstance(LoginActivity.this).saveValue("userId", loginResult.getAccessToken().getUserId());

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Bitmap bg = BitmapFactory.decodeResource(getResources(), R.drawable.sign_up_bg);
                Bitmap blurredBitmap = BlurBuilder.blur(LoginActivity.this, bg);

                findViewById(R.id.login_bg).setBackgroundDrawable(new BitmapDrawable(getResources(), blurredBitmap));
                findViewById(R.id.logo).setVisibility(View.VISIBLE);
                findViewById(R.id.logo1).setVisibility(View.VISIBLE);

                mLoginButton.setVisibility(View.VISIBLE);
            }
        }, 1500);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
