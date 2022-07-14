package com.app.AlistApp.utils;

import android.transition.Fade;
import android.view.View;
import android.view.Window;

import androidx.core.view.ViewCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {


    public static void showSnackbar(View view, String message){
        Snackbar snackbar=Snackbar.make(view,message,Snackbar.LENGTH_LONG);
        ViewCompat.setLayoutDirection(snackbar.getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
        snackbar.show();
    }




}
