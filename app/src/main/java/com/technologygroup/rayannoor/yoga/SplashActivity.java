package com.technologygroup.rayannoor.yoga;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.technologygroup.rayannoor.yoga.IntroPage.introActivity;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 3000;
    private SharedPreferences prefs;



    private static final int RightToLeft = 1;
    private static final int LeftToRight = 2;
    private static final int DURATION = 5000;

    private ValueAnimator mCurrentAnimator;
    private final Matrix mMatrix = new Matrix();
    private float mScaleFactor;
    private int mDirection = RightToLeft;
    private RectF mDisplayRect = new RectF();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getApplicationContext().getSharedPreferences("User", 0);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
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



//        img1.post(new Runnable() {
//            @Override
//            public void run() {
//                mScaleFactor = (float)  img1.getHeight() / (float) img1.getDrawable().getIntrinsicHeight();
//                mMatrix.postScale(mScaleFactor, mScaleFactor);
//                img1.setImageMatrix(mMatrix);
//                animate();
//            }
//        });



    }



//    private void animate() {
//        updateDisplayRect();
//        if(mDirection == RightToLeft) {
//            animate(mDisplayRect.left, mDisplayRect.left - (mDisplayRect.right - img1.getWidth()));
//        } else {
//            animate(mDisplayRect.left, 0.0f);
//        }
//    }
//
//    private void animate(float from, float to) {
//        mCurrentAnimator = ValueAnimator.ofFloat(from, to);
//        mCurrentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (Float) animation.getAnimatedValue();
//
//                mMatrix.reset();
//                mMatrix.postScale(mScaleFactor, mScaleFactor);
//                mMatrix.postTranslate(value, 0);
//
//                img1.setImageMatrix(mMatrix);
//
//            }
//        });
//        mCurrentAnimator.setDuration(DURATION);
//        mCurrentAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if(mDirection == RightToLeft)
//                    mDirection = LeftToRight;
//                else
//                    mDirection = RightToLeft;
//
//                animate();
//            }
//        });
//        mCurrentAnimator.start();
//    }
//
//    private void updateDisplayRect() {
//        mDisplayRect.set(0, 0, img1.getDrawable().getIntrinsicWidth(), img1.getDrawable().getIntrinsicHeight());
//        mMatrix.mapRect(mDisplayRect);
//    }
//
}
