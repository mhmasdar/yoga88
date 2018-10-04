package com.technologygroup.rayannoor.yoga.Coaches;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.WebService;

public class CoachProfileActivity extends AppCompatActivity {

    private RoundedImageView imgCoach;
    private TextView txtCoachName;
    private ImageView imgEditCoachDetails;
    private ImageView imgBack;
    private TextView txtCoachRate;
    private TextView txtLikeCount;
    private ImageView imgLockEducation;
    private LinearLayout lytEducation;
    private ImageView imgLockResume;
    private LinearLayout lytResume;
    private ImageView imgLockGyms;
    private LinearLayout lytGyms;
    private ImageView imgLockCertificates;
    private LinearLayout lytCertificates;
    private RelativeLayout lytCoachProfileUpgrade;
    private TextView txtCoachLevel;
    private ImageView imgLockTeachs;
    private LinearLayout lytTeachs;
    private RatingBar rating;


    private SharedPreferences prefs;
    private int idCoach;
    private CoachModel coachModel;
    WebServiceCoachInfo webServiceCoachInfo;
    private ImageView imgLockBio;
    private LinearLayout lytBio;
    private ImageView imgLockCourse;
    private LinearLayout lytCourse;
    private LinearLayout lytCoachesOnly;
    private ImageView imgLockTeach;
    private LinearLayout lytTeach;
    private ImageView imgLockJob;
    private LinearLayout lytJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_edit_profile);

        initView();

        idCoach = getIntent().getIntExtra("idUser", -1);

        if (idCoach > 0) {

            webServiceCoachInfo = new WebServiceCoachInfo();
            webServiceCoachInfo.execute();
        } else {
            Toast.makeText(this, "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
            finish();
        }

        imgEditCoachDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CoachProfileActivity.this, CoachEditDetialsActivity.class);

                intent.putExtra("CoachId", idCoach);
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
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void others()
    {


        lytCoachProfileUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(CoachProfileActivity.this, CoachPlanActivity.class);
//                intent.putExtra("idCoachOrGym", idCoach);
//                intent.putExtra("idPlan", 1);
//                startActivity(intent);

                Toast.makeText(getApplicationContext(), "این بخش به زودی فعال خواهد شد..." , Toast.LENGTH_LONG).show();
            }
        });


//        lytComments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (coachModel.IsVerified) {
//                    Intent intent = new Intent(CoachProfileActivity.this, CommentsActivity.class);
//                    intent.putExtra("IdCoachOrGym", idCoach);
//                    intent.putExtra("IsGym", false);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


        lytEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 3);
                    intent.putExtra("idBio", coachModel.Bio);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 0);
                    intent.putExtra("idBio", coachModel.Bio);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
        lytBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 1);
                    intent.putExtra("idBio", coachModel.Bio);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 5);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });


        lytCertificates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 2);
                    intent.putExtra("idBio", coachModel.Bio);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytTeachs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("idBio", coachModel.Bio);
                    intent.putExtra("SelectedTabIndex", 6);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
        lytCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("idBio", coachModel.Bio);
                    intent.putExtra("SelectedTabIndex", 4);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
        lytJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachProfileActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("idBio", coachModel.Bio);
                    intent.putExtra("SelectedTabIndex", 7);
                    intent.putExtra("idCoach", idCoach);
                    startActivity(intent);
                } else {
                    Toast.makeText(CoachProfileActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void initView() {
        imgCoach = (RoundedImageView) findViewById(R.id.imgCoach);
        txtCoachName = (TextView) findViewById(R.id.txtCoachName);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgEditCoachDetails = (ImageView) findViewById(R.id.imgEditCoachDetails);
        txtCoachRate = (TextView) findViewById(R.id.txtCoachRate);
        txtLikeCount = (TextView) findViewById(R.id.txtLikeCount);
        imgLockEducation = (ImageView) findViewById(R.id.imgLockEducation);
        lytEducation = (LinearLayout) findViewById(R.id.lytEducation);
        imgLockResume = (ImageView) findViewById(R.id.imgLockResume);
        lytResume = (LinearLayout) findViewById(R.id.lytResume);
        imgLockGyms = (ImageView) findViewById(R.id.imgLockGyms);
        lytGyms = (LinearLayout) findViewById(R.id.lytGyms);
        imgLockCertificates = (ImageView) findViewById(R.id.imgLockCertificates);
        lytCertificates = (LinearLayout) findViewById(R.id.lytCertificates);
        lytCoachProfileUpgrade = (RelativeLayout) findViewById(R.id.lytCoachProfileUpgrade);
        txtCoachLevel = (TextView) findViewById(R.id.txtCoachLevel);
        imgLockTeachs = (ImageView) findViewById(R.id.imgLockTeach);
        lytTeachs = (LinearLayout) findViewById(R.id.lytTeach);
        rating = (RatingBar) findViewById(R.id.rating);
        imgLockBio = (ImageView) findViewById(R.id.imgLockBio);
        lytBio = (LinearLayout) findViewById(R.id.lytBio);
        imgLockCourse = (ImageView) findViewById(R.id.imgLockCourse);
        lytCourse = (LinearLayout) findViewById(R.id.lytCourse);
        lytCoachesOnly = (LinearLayout) findViewById(R.id.lytCoachesOnly);
        imgLockTeach = (ImageView) findViewById(R.id.imgLockTeach);
        lytTeach = (LinearLayout) findViewById(R.id.lytTeach);
        imgLockJob = (ImageView) findViewById(R.id.imgLockJob);
        lytJob = (LinearLayout) findViewById(R.id.lytJob);
    }

    private class WebServiceCoachInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            coachModel = new CoachModel();
            dialog = new Dialog(CoachProfileActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog.findViewById(R.id.logo);
            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Object... params) {

            coachModel = webService.getCoachDetail(App.isInternetOn(), idCoach);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (coachModel != null) {

                if (coachModel.ImgName != null)
                    if (!coachModel.ImgName.equals("") && !coachModel.ImgName.equals("null"))
                        Glide.with(CoachProfileActivity.this).load(App.imgAddr + coachModel.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCoach);
                txtCoachName.setText(coachModel.fName + " " + coachModel.lName);
//                ClassLevels classLevels = new ClassLevels();
//                txtCoachLevel.setText(classLevels.getCoachLevelName(idCoachCurrentPlan));
                String strRate = String.valueOf(coachModel.Rate);
                if (strRate.length() > 3)
                    strRate = strRate.substring(0, 3);
                txtCoachRate.setText(strRate);
                txtLikeCount.setText(coachModel.like + "");
                rating.setRating((float) coachModel.Rate);
                if (coachModel.IsVerified) {
                    lytEducation.setAlpha(1);
                    imgLockEducation.setVisibility(View.GONE);
                    lytBio.setAlpha(1);
                    imgLockBio.setVisibility(View.GONE);
                    lytResume.setAlpha(1);
                    imgLockResume.setVisibility(View.GONE);
                    lytGyms.setAlpha(1);
                    imgLockGyms.setVisibility(View.GONE);
                    lytCertificates.setAlpha(1);
                    imgLockCertificates.setVisibility(View.GONE);
//                    lytComments.setAlpha(1);
//                    imgLockCeomments.setVisibility(View.GONE);
                    lytTeachs.setAlpha(1);
                    imgLockTeachs.setVisibility(View.GONE);
                    lytCourse.setAlpha(1);
                    imgLockCourse.setVisibility(View.GONE);
                    lytJob.setAlpha(1);
                    imgLockJob.setVisibility(View.GONE);

                    others();
                }

            } else {

                Toast.makeText(CoachProfileActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                finish();

            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        WebServiceCoachInfo webServiceCoachInfo=new WebServiceCoachInfo();
        webServiceCoachInfo.execute();
    }
}
