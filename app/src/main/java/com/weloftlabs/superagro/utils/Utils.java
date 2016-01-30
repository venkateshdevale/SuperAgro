package com.weloftlabs.superagro.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


import com.weloftlabs.superagro.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {


    public static ProgressDialog showDialog(Context context, String message) {

        final ProgressDialog dialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message))
            dialog.setTitle(message);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;

    }

    public static void showAlert(Context context, String message) {

        new AlertDialog.Builder(context)
                .setTitle("LATCH")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })

                .show();


    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    /**
     *
     * @param inCal
     * @param inFormat
     * @return the formatted date
     */
    public static String getFormattedDate(Calendar inCal,SimpleDateFormat inFormat){
        return inFormat.format(inCal.getTime());
    }
}
