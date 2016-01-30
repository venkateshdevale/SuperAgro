package com.weloftlabs.superagro.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weloftlabs.superagro.R;
import com.weloftlabs.superagro.ui.MainActivity;


public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUserName;
    private EditText mUserContact;
    private EditText mFamilyContact;
    private Button mProceed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        initView();

    }

    private void initView() {
        mUserName = (EditText) findViewById(R.id.user_name);
        mUserContact = (EditText) findViewById(R.id.user_contact);
        mFamilyContact = (EditText) findViewById(R.id.family_contact);
        findViewById(R.id.proceed).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.proceed) {
            boolean shouldProceed = true;

            if (mUserName.getText().toString().trim().length() == 0) {
                mUserName.setError("This feild can't be empty");
                shouldProceed = false;
            }
            if (mUserContact.getText().toString().trim().length() == 0) {
                mUserContact.setError("This feild can't be empty");
                shouldProceed = false;
            }
            if (mFamilyContact.getText().toString().trim().length() == 0) {
                mFamilyContact.setError("This feild can't be empty");
                shouldProceed = false;
            }

            if (!shouldProceed) {
                return;

            } else {

                Toast.makeText(UserDetailsActivity.this, "Details Updated. Cheers!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UserDetailsActivity.this, MainActivity.class));
                finish();

            }

        }
    }

}
