package com.example.play;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new SessionManager(SplashScreenActivity.this).isLoggedIn()){
                    Intent intent = new Intent(getApplicationContext(), Admin.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreenActivity.this, IntroSlider.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1500);
    }
}