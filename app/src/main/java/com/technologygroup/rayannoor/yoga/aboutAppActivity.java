package com.technologygroup.rayannoor.yoga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class aboutAppActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private TextView txtAbout;
    private ImageView img1;
    private ImageView img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        initView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        txtAbout = (TextView) findViewById(R.id.txtAbout);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
    }
}
