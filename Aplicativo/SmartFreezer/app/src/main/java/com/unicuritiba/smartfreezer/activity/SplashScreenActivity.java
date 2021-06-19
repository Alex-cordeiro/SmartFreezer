package com.unicuritiba.smartfreezer.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.unicuritiba.smartfreezer.R;

public class SplashScreenActivity extends AppCompatActivity {
    private MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //mainActivity.AtivaBluetooth();

                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        }, 5000);
    }
}