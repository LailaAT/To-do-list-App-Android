package com.example.doitcheckit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        //Splash will be the display screen when first opening the app
        //then it will display the main after it by a few milliseconds
        final Intent i = new Intent(SplashActivity.this,MainActivity.class);
        //creating a handler for the delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                finish();
            }
        },1000); //after 1 sec the main activity will be shown
    }
}
