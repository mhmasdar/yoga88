package com.technologygroup.rayannoor.yoga;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.technologygroup.rayannoor.yoga.Classes.App;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class imageActivity extends Activity {

    private TouchImageView img;
    private CircularProgressBar lytLoading;

    String imgName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imgName = getIntent().getStringExtra("ImgName");

        img = (TouchImageView) findViewById(R.id.img);
        lytLoading = (CircularProgressBar) findViewById(R.id.lytLoading);


            if (!imgName.equals("") && !imgName.equals("null"))
            {
                Glide.with(this).load(App.imgAddr + imgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bm, GlideAnimation<? super Bitmap> glideAnimation) {
                        img.setImageBitmap(bm);
                        lytLoading.setVisibility(View.GONE);
                        img.setVisibility(View.VISIBLE);
                    }
                });
            }

            else
            {
                lytLoading.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                Glide.with(this).load(R.drawable.ic_gym_list).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);
            }


    }

}
