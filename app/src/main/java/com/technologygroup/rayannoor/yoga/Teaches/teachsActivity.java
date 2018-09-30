package com.technologygroup.rayannoor.yoga.Teaches;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.R;

public class teachsActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView imgNewTeach;

    private String teachsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);
        initView();

        Glide.with(this).load(R.drawable.yoga1).diskCacheStrategy(DiskCacheStrategy.NONE).into(img1);
        Glide.with(this).load(R.drawable.yoga3).diskCacheStrategy(DiskCacheStrategy.NONE).into(img2);
        Glide.with(this).load(R.drawable.yoga2).diskCacheStrategy(DiskCacheStrategy.NONE).into(img3);
//        Glide.with(this).load("google.com/hesam").diskCacheStrategy(DiskCacheStrategy.NONE).into(img3);
        //set image dark
        img1.setColorFilter(Color.rgb(190, 190, 190), PorterDuff.Mode.MULTIPLY);
        img2.setColorFilter(Color.rgb(190, 190, 190), PorterDuff.Mode.MULTIPLY);
        img3.setColorFilter(Color.rgb(190, 190, 190), PorterDuff.Mode.MULTIPLY);


        teachsCount = getIntent().getStringExtra("teachsCount");
        if (Integer.valueOf(teachsCount) > 0)
            imgNewTeach.setVisibility(View.VISIBLE);
        else
            imgNewTeach.setVisibility(View.INVISIBLE);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(teachsActivity.this, teachsListActivity.class);
                intent.putExtra("tab_number", 0);
                startActivity(intent);
            }
        });


        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(teachsActivity.this, CoachTeachsActivity.class);
                intent.putExtra("teachsCount" , teachsCount);
                startActivity(intent);
            }
        });


        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(getApplicationContext() , "این بخش به زودی فعال خواهد شد" , Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        imgNewTeach = (ImageView) findViewById(R.id.imgNewTeach);
    }

}
