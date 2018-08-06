package com.technologygroup.rayannoor.yoga.Teaches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;

public class TeachDetailsActivity extends AppCompatActivity {


    private RelativeLayout btnBack;
    private TextView txtTitle;
    private LinearLayout lyt1;
    private TextView txt1;
    private ImageView img1;
    private LinearLayout lyt2;
    private TextView txt2;
    private ImageView img2;
    private LinearLayout lyt3;
    private TextView txt3;
    private ImageView img3;
    private LinearLayout lyt4;
    private TextView txt4;
    private ImageView img4;
    private LinearLayout lyt5;
    private TextView txt5;
    private ImageView img5;
    private LinearLayout lyt6;
    private TextView txt6;
    private ImageView img6;
    private LinearLayout lyt7;
    private TextView txt7;
    private ImageView img7;
    private LinearLayout lyt8;
    private TextView txt8;
    private ImageView img8;
    private LinearLayout lyt9;
    private TextView txt9;
    private ImageView img9;
    private LinearLayout lyt10;
    private TextView txt10;
    private ImageView img10;
    private LinearLayout lytShare;
    private ImageView teachDetailsSharing;
    private LinearLayout lytLast;
    private LinearLayout lytNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_details);

        initView();
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        lyt1 = (LinearLayout) findViewById(R.id.lyt1);
        txt1 = (TextView) findViewById(R.id.txt1);
        img1 = (ImageView) findViewById(R.id.img1);
        lyt2 = (LinearLayout) findViewById(R.id.lyt2);
        txt2 = (TextView) findViewById(R.id.txt2);
        img2 = (ImageView) findViewById(R.id.img2);
        lyt3 = (LinearLayout) findViewById(R.id.lyt3);
        txt3 = (TextView) findViewById(R.id.txt3);
        img3 = (ImageView) findViewById(R.id.img3);
        lyt4 = (LinearLayout) findViewById(R.id.lyt4);
        txt4 = (TextView) findViewById(R.id.txt4);
        img4 = (ImageView) findViewById(R.id.img4);
        lyt5 = (LinearLayout) findViewById(R.id.lyt5);
        txt5 = (TextView) findViewById(R.id.txt5);
        img5 = (ImageView) findViewById(R.id.img5);
        lyt6 = (LinearLayout) findViewById(R.id.lyt6);
        txt6 = (TextView) findViewById(R.id.txt6);
        img6 = (ImageView) findViewById(R.id.img6);
        lyt7 = (LinearLayout) findViewById(R.id.lyt7);
        txt7 = (TextView) findViewById(R.id.txt7);
        img7 = (ImageView) findViewById(R.id.img7);
        lyt8 = (LinearLayout) findViewById(R.id.lyt8);
        txt8 = (TextView) findViewById(R.id.txt8);
        img8 = (ImageView) findViewById(R.id.img8);
        lyt9 = (LinearLayout) findViewById(R.id.lyt9);
        txt9 = (TextView) findViewById(R.id.txt9);
        img9 = (ImageView) findViewById(R.id.img9);
        lyt10 = (LinearLayout) findViewById(R.id.lyt10);
        txt10 = (TextView) findViewById(R.id.txt10);
        img10 = (ImageView) findViewById(R.id.img10);
        lytShare = (LinearLayout) findViewById(R.id.lytShare);
        teachDetailsSharing = (ImageView) findViewById(R.id.teach_details_sharing);
        lytLast = (LinearLayout) findViewById(R.id.lytLast);
        lytNext = (LinearLayout) findViewById(R.id.lytNext);
    }
}
