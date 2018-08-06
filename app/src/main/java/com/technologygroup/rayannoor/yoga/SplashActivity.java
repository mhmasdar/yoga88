package com.technologygroup.rayannoor.yoga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.technologygroup.rayannoor.yoga.IntroPage.IntroFragment;
import com.technologygroup.rayannoor.yoga.IntroPage.introActivity;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 3000;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setUpTimer();
    }


    public void setUpTimer() {


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                prefs = getApplicationContext().getSharedPreferences("MyPrefs", 0);
                boolean isFirstRun = prefs.getBoolean("isFirstRun", true);

                if (isFirstRun)
                {
                    Intent i = new Intent(SplashActivity.this, introActivity.class);
                    startActivity(i);
                    finish();
                }

                else
                {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
