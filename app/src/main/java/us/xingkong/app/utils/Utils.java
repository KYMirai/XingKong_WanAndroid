package us.xingkong.app.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Utils {
    public static void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }
}