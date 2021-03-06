package com.technologygroup.rayannoor.yoga.referees;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Coaches.CoachEditDetialsActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import org.json.JSONException;
import org.json.JSONObject;

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
    private RelativeLayout lytRefereeProfileUpgrade;
    CoachModel coachModel;
    int idsend;
    private Dialog dialog;
    Boolean calledFromPanel;

    getInfo getinfo;
    WebServiceCallgetDetail callCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_profile);
        initView();
        getInfo();


        lytRefereeProfileUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "این بخش به زودی فعال خواهد شد..." , Toast.LENGTH_LONG).show();

            }
        });
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
        lytRefereeProfileUpgrade = (RelativeLayout) findViewById(R.id.lytRefereeProfileUpgrade);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });
    }
    public void getInfo() {

        coachModel = new CoachModel();

        idsend = getIntent().getIntExtra("idReffre", -1);
        calledFromPanel = getIntent().getBooleanExtra("calledFromPanel", false);

        callCity = new WebServiceCallgetDetail();
        callCity.execute();



    }
    private void setViews() {

        if (coachModel.ImgName != null)
            if (!coachModel.ImgName.equals("") && !coachModel.ImgName.equals("null"))
                Glide.with(RefereeProfileActivity.this).load(App.imgAddr + coachModel.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgReferee);


        txtRefereeName.setText(coachModel.fName + " " + coachModel.lName);
        txtLikeCount.setText(coachModel.like + "");
    //    txtRefereeCity.setText(coachModel.State + "\n" + coachModel.City);
        String strRate = String.valueOf(coachModel.Rate);
        if (strRate.length() > 3)
            strRate = strRate.substring(0, 3);
        txtRefereeRate.setText(strRate);
        rating.setRating((float) coachModel.Rate);


        if (coachModel.IsVerified) {

            lytEducation.setAlpha(1);
            imgLockEducation.setVisibility(View.GONE);
            lytResume.setAlpha(1);
            imgLockResume.setVisibility(View.GONE);
            lytCertificates.setAlpha(1);
            imgLockCertificates.setVisibility(View.GONE);
            lytBio.setAlpha(1);
            imgLockBio.setVisibility(View.GONE);
            lytCourse.setAlpha(1);
            imgLockCourse.setVisibility(View.GONE);
        }


    }
    private void others() {
        
        setViews();
        imgEditRefereeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RefereeProfileActivity.this, CoachEditDetialsActivity.class);
                intent.putExtra("CoachId", idsend);
                intent.putExtra("CoachFName", coachModel.fName);
                intent.putExtra("CoachLName", coachModel.lName);
               intent.putExtra("CoachImg", coachModel.ImgName);
                intent.putExtra("CoachNatCode", coachModel.natCode);
                intent.putExtra("CoachEmail", coachModel.Email);
                intent.putExtra("CoachMobile", coachModel.Mobile);
                intent.putExtra("CoachIdTelegram", coachModel.Telegram);
                intent.putExtra("CoachIdInstagram", coachModel.Instagram);
                startActivity(intent);
            }
        });
        lytResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeProfileActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", calledFromPanel);
                    intent.putExtra("SelectedTabIndex", 0);
                    intent.putExtra("idCoach", idsend);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });
        lytBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeProfileActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 1);
                    intent.putExtra("idCoach", idsend);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });


        lytCertificates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeProfileActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 2);
                    intent.putExtra("idCoach", idsend);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });

        lytEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeProfileActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 3);
                    intent.putExtra("idCoach", idsend);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });

        lytCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeProfileActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 4);
                    intent.putExtra("idCoach", idsend);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });

    }
    private class WebServiceCallgetDetail extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        double rate;
        Dialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            webService = new WebService();
            dialog = new Dialog(RefereeProfileActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog.findViewById(R.id.logo);
            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            coachModel = webService.getReffreDetail(App.isInternetOn(), idsend);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            others();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        getinfo=new getInfo();
        getinfo.execute();

    }
    private class getInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String Result;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            Result = webService.getPanelInfo(App.isInternetOn(), idsend);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Result != null) {
                try {
                    JSONObject panelj = new JSONObject(Result);
                    JSONObject imagej = panelj.getJSONObject("ProfileImage");
                    String imageName = imagej.getString("Name");
                    if (imageName != null)
                        if (!imageName.equals("") && !imageName.equals("null")) {
                            Glide.with(RefereeProfileActivity.this).load(App.imgAddr + imageName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgReferee);
                            SharedPreferences prefs = getSharedPreferences("User", 0);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("image", imageName);
                            editor.apply();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();

        if (getinfo != null)
            if (getinfo.getStatus() == AsyncTask.Status.RUNNING)
                getinfo.cancel(true);
        if (callCity != null)
            if (callCity.getStatus() == AsyncTask.Status.RUNNING)
                callCity.cancel(true);
    }

}
