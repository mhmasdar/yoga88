package com.technologygroup.rayannoor.yoga.Notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;

public class NewsDetailsActivity extends AppCompatActivity {

    private ImageView imgNews;
    private TextView txtTitle;
    private TextView txtNewsBody;
    private LinearLayout lytShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        initView();
    }

    private void initView() {
        imgNews = (ImageView) findViewById(R.id.imgNews);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtNewsBody = (TextView) findViewById(R.id.txtNewsBody);
        lytShare = (LinearLayout) findViewById(R.id.lytShare);
    }
}
