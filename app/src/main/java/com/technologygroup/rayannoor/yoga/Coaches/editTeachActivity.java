package com.technologygroup.rayannoor.yoga.Coaches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;

public class editTeachActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private EditText edtTitle;
    private LinearLayout lytTeach1;
    private TextView txtNoImage1;
    private ImageView imgTeach1;
    private ImageView imgSelectPicture1;
    private EditText edtBody1;
    private LinearLayout lytTeach2;
    private TextView txtNoImage2;
    private ImageView imgTeach2;
    private ImageView imgSelectPicture2;
    private EditText edtBody2;
    private LinearLayout lytTeach3;
    private TextView txtNoImage3;
    private ImageView imgTeach3;
    private ImageView imgSelectPicture3;
    private EditText edtBody3;
    private LinearLayout lytTeach4;
    private TextView txtNoImage4;
    private ImageView imgTeach4;
    private ImageView imgSelectPictur4;
    private EditText edtBody4;
    private LinearLayout lytTeach5;
    private TextView txtNoImage5;
    private ImageView imgTeach5;
    private ImageView imgSelectPicture5;
    private EditText edtBody5;
    private LinearLayout lytTeach6;
    private TextView txtNoImage6;
    private ImageView imgTeach6;
    private ImageView imgSelectPicture6;
    private EditText edtBody6;
    private LinearLayout lytTeach7;
    private TextView txtNoImage7;
    private ImageView imgTeach7;
    private ImageView imgSelectPicture7;
    private EditText edtBody7;
    private LinearLayout lytTeach8;
    private TextView txtNoImage8;
    private ImageView imgTeach8;
    private ImageView imgSelectPicture8;
    private EditText edtBody8;
    private LinearLayout lytTeach9;
    private TextView txtNoImage9;
    private ImageView imgTeach9;
    private ImageView imgSelectPicture9;
    private EditText edtBody9;
    private LinearLayout lytTeach10;
    private TextView txtNoImage10;
    private ImageView imgTeach10;
    private ImageView imgSelectPicture10;
    private EditText edtBody10;
    private TextView lytDeleteLesson;
    private TextView lytAddLesson;
    private RelativeLayout lytSend;
    private TextView txtSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teach);
        initView();
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        lytTeach1 = (LinearLayout) findViewById(R.id.lytTeach1);
        txtNoImage1 = (TextView) findViewById(R.id.txtNoImage1);
        imgTeach1 = (ImageView) findViewById(R.id.imgTeach1);
        imgSelectPicture1 = (ImageView) findViewById(R.id.imgSelectPicture1);
        edtBody1 = (EditText) findViewById(R.id.edtBody1);
        lytTeach2 = (LinearLayout) findViewById(R.id.lytTeach2);
        txtNoImage2 = (TextView) findViewById(R.id.txtNoImage2);
        imgTeach2 = (ImageView) findViewById(R.id.imgTeach2);
        imgSelectPicture2 = (ImageView) findViewById(R.id.imgSelectPicture2);
        edtBody2 = (EditText) findViewById(R.id.edtBody2);
        lytTeach3 = (LinearLayout) findViewById(R.id.lytTeach3);
        txtNoImage3 = (TextView) findViewById(R.id.txtNoImage3);
        imgTeach3 = (ImageView) findViewById(R.id.imgTeach3);
        imgSelectPicture3 = (ImageView) findViewById(R.id.imgSelectPicture3);
        edtBody3 = (EditText) findViewById(R.id.edtBody3);
        lytTeach4 = (LinearLayout) findViewById(R.id.lytTeach4);
        txtNoImage4 = (TextView) findViewById(R.id.txtNoImage4);
        imgTeach4 = (ImageView) findViewById(R.id.imgTeach4);
        imgSelectPictur4 = (ImageView) findViewById(R.id.imgSelectPictur4);
        edtBody4 = (EditText) findViewById(R.id.edtBody4);
        lytTeach5 = (LinearLayout) findViewById(R.id.lytTeach5);
        txtNoImage5 = (TextView) findViewById(R.id.txtNoImage5);
        imgTeach5 = (ImageView) findViewById(R.id.imgTeach5);
        imgSelectPicture5 = (ImageView) findViewById(R.id.imgSelectPicture5);
        edtBody5 = (EditText) findViewById(R.id.edtBody5);
        lytTeach6 = (LinearLayout) findViewById(R.id.lytTeach6);
        txtNoImage6 = (TextView) findViewById(R.id.txtNoImage6);
        imgTeach6 = (ImageView) findViewById(R.id.imgTeach6);
        imgSelectPicture6 = (ImageView) findViewById(R.id.imgSelectPicture6);
        edtBody6 = (EditText) findViewById(R.id.edtBody6);
        lytTeach7 = (LinearLayout) findViewById(R.id.lytTeach7);
        txtNoImage7 = (TextView) findViewById(R.id.txtNoImage7);
        imgTeach7 = (ImageView) findViewById(R.id.imgTeach7);
        imgSelectPicture7 = (ImageView) findViewById(R.id.imgSelectPicture7);
        edtBody7 = (EditText) findViewById(R.id.edtBody7);
        lytTeach8 = (LinearLayout) findViewById(R.id.lytTeach8);
        txtNoImage8 = (TextView) findViewById(R.id.txtNoImage8);
        imgTeach8 = (ImageView) findViewById(R.id.imgTeach8);
        imgSelectPicture8 = (ImageView) findViewById(R.id.imgSelectPicture8);
        edtBody8 = (EditText) findViewById(R.id.edtBody8);
        lytTeach9 = (LinearLayout) findViewById(R.id.lytTeach9);
        txtNoImage9 = (TextView) findViewById(R.id.txtNoImage9);
        imgTeach9 = (ImageView) findViewById(R.id.imgTeach9);
        imgSelectPicture9 = (ImageView) findViewById(R.id.imgSelectPicture9);
        edtBody9 = (EditText) findViewById(R.id.edtBody9);
        lytTeach10 = (LinearLayout) findViewById(R.id.lytTeach10);
        txtNoImage10 = (TextView) findViewById(R.id.txtNoImage10);
        imgTeach10 = (ImageView) findViewById(R.id.imgTeach10);
        imgSelectPicture10 = (ImageView) findViewById(R.id.imgSelectPicture10);
        edtBody10 = (EditText) findViewById(R.id.edtBody10);
        lytDeleteLesson = (TextView) findViewById(R.id.lytDeleteLesson);
        lytAddLesson = (TextView) findViewById(R.id.lytAddLesson);
        lytSend = (RelativeLayout) findViewById(R.id.lytSend);
        txtSend = (TextView) findViewById(R.id.txtSend);
    }
}
