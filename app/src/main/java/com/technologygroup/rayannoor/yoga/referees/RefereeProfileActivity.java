package com.technologygroup.rayannoor.yoga.referees;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;

public class RefereeProfileActivity extends AppCompatActivity {

    private ImageView imgEditRefereeDetails;
    private ImageView imgBack;
    private RoundedImageView imgReferee;
    private TextView txtRefereeName;
    private TextView txtRefereeLevel;
    private RatingBar rating;
    private TextView txtRefereeRate;
    private TextView txtLikeCount;
    private ImageView imgLockResume;
    private LinearLayout lytResume;
    private ImageView imgLockBio;
    private LinearLayout lytBio;
    private ImageView imgLockCertificates;
    private LinearLayout lytCertificates;
    private ImageView imgLockEducation;
    private LinearLayout lytEducation;
    private ImageView imgLockCourse;
    private LinearLayout lytCourse;
    private ImageView imgLockComments;
    private LinearLayout lytComments;
    private RelativeLayout lytRefereeProfileUpgrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_profile);
        initView();
    }

    private void initView() {
        imgEditRefereeDetails = (ImageView) findViewById(R.id.imgEditRefereeDetails);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgReferee = (RoundedImageView) findViewById(R.id.imgReferee);
        txtRefereeName = (TextView) findViewById(R.id.txtRefereeName);
        txtRefereeLevel = (TextView) findViewById(R.id.txtRefereeLevel);
        rating = (RatingBar) findViewById(R.id.rating);
        txtRefereeRate = (TextView) findViewById(R.id.txtRefereeRate);
        txtLikeCount = (TextView) findViewById(R.id.txtLikeCount);
        imgLockResume = (ImageView) findViewById(R.id.imgLockResume);
        lytResume = (LinearLayout) findViewById(R.id.lytResume);
        imgLockBio = (ImageView) findViewById(R.id.imgLockBio);
        lytBio = (LinearLayout) findViewById(R.id.lytBio);
        imgLockCertificates = (ImageView) findViewById(R.id.imgLockCertificates);
        lytCertificates = (LinearLayout) findViewById(R.id.lytCertificates);
        imgLockEducation = (ImageView) findViewById(R.id.imgLockEducation);
        lytEducation = (LinearLayout) findViewById(R.id.lytEducation);
        imgLockCourse = (ImageView) findViewById(R.id.imgLockCourse);
        lytCourse = (LinearLayout) findViewById(R.id.lytCourse);
        imgLockComments = (ImageView) findViewById(R.id.imgLockComments);
        lytComments = (LinearLayout) findViewById(R.id.lytComments);
        lytRefereeProfileUpgrade = (RelativeLayout) findViewById(R.id.lytRefereeProfileUpgrade);
    }
}
