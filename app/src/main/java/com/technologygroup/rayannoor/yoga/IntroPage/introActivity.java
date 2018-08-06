package com.technologygroup.rayannoor.yoga.IntroPage;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.LoginActivity;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RegisterActivity;
import com.viewpagerindicator.CirclePageIndicator;

public class introActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private RelativeLayout lytImages;
    private ImageView img;
    private ImageView imgIntroPage;
    private LinearLayout lytIntroBottom;
    private TextView txtRegister;
    private TextView txtLogin;
    private CirclePageIndicator indicator;
    private float density;
    private static int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initView();

        //set image dark
        img.setColorFilter(Color.rgb(200, 200, 200), PorterDuff.Mode.MULTIPLY);


        density = getResources().getDisplayMetrics().density;


        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(introActivity.this).load(R.drawable.intro1).diskCacheStrategy(DiskCacheStrategy.NONE).animate(R.anim.fade_in).into(img);
            }
        }, 200);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(introActivity.this).load(R.drawable.intro2).diskCacheStrategy(DiskCacheStrategy.NONE).animate(R.anim.fade_in).into(img);
            }
        }, 1400);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(introActivity.this).load(R.drawable.intro3).diskCacheStrategy(DiskCacheStrategy.NONE).animate(R.anim.fade_in).into(img);
            }
        }, 2600);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Glide.with(introActivity.this).load(R.drawable.intro4).diskCacheStrategy(DiskCacheStrategy.NONE).animate(R.anim.fade_in).into(img);
            }
        }, 3800);

        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                //set image dark
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_to_top);
                Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                imgIntroPage.setColorFilter(Color.rgb(70, 70, 70), PorterDuff.Mode.MULTIPLY);
                Glide.with(introActivity.this).load(R.drawable.intro4).into(imgIntroPage);
                lytImages.setVisibility(View.GONE);
                lytIntroBottom.setVisibility(View.VISIBLE);
                lytIntroBottom.startAnimation(anim);

                viewpager.setVisibility(View.VISIBLE);
                viewpager.startAnimation(anim2);
                // Set an Adapter on the ViewPager
                viewpager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

                indicator.setViewPager(viewpager);
                indicator.setRadius(3 * density);

            }
        }, 5000);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(introActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(introActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        lytImages = (RelativeLayout) findViewById(R.id.lytImages);
        img = (ImageView) findViewById(R.id.img);
        imgIntroPage = (ImageView) findViewById(R.id.imgIntroPage);
        lytIntroBottom = (LinearLayout) findViewById(R.id.lytIntroBottom);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
    }
}
