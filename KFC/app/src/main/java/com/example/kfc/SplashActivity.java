package com.example.kfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private Handler handle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent inten = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(inten);
                finish();
            }
        },3000);
    }
}