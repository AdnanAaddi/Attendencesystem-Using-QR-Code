package com.example.amna.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

public class LoaderDialog {
    private ProgressDialog progressDialog;

    public void showDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        //your logic here for back button pressed event
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void showDialog(Context context, String Message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Message);
        progressDialog.show();
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !event.isCanceled()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        //your logic here for back button pressed event
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void hideDialog() {
        progressDialog.dismiss();
    }
}
