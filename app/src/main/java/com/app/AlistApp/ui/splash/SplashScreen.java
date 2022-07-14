package com.app.AlistApp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.AlistApp.R;
import com.app.AlistApp.ui.services.ServicesActivity;

import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getBaseContext(), ServicesActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);


    }
}