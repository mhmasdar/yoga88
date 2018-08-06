package com.technologygroup.rayannoor.yoga;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;

public class imageActivity extends Activity {

    private TouchImageView img;

    String imgName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imgName = getIntent().getStringExtra("ImgName");

        img = (TouchImageView) findViewById(R.id.img);

            if (!imgName.equals("") && !imgName.equals("null"))
                Glide.with(this).load(App.imgAddr + imgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);


    }

}
