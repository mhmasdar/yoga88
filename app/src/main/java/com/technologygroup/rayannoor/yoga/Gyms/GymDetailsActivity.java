package com.technologygroup.rayannoor.yoga.Gyms;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.technologygroup.rayannoor.yoga.Classes.ClassLevels;
import com.technologygroup.rayannoor.yoga.Coaches.CoachPlanActivity;
import com.technologygroup.rayannoor.yoga.CommentsActivity;
import com.technologygroup.rayannoor.yoga.Models.GymModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

public class GymDetailsActivity extends AppCompatActivity {


    private SharedPreferences prefs;
    private int idGym;
    private GymModel gymModel;
    WebServiceCoachInfo webServiceCoachInfo;
    private ImageView imgGym;
    private ImageView imgEditGymDetails;
    private ImageView imgBack;
    private TextView txtGymName;
    private TextView txtCoachLevel;
    private RatingBar rating;
    private TextView txtCoachRate;
    private TextView txtLikeCount;
    private ImageView imgLockHonours;
    private LinearLayout lytGymHonours;
    private ImageView imgLockAbout;
    private LinearLayout lytAbout;
    private ImageView imgLockClip;
    private LinearLayout lytClip;
    private ImageView imgLockPhotos;
    private LinearLayout lytPhotos;
    private ImageView imgLockCoaches;

    private LinearLayout lytCoaches;
    private ImageView imgLockCourse;
    private LinearLayout lytCourses;
    private ImageView imgLockWorkTime;
    private LinearLayout lytWorkTime;
    private ImageView imgLockNotifs;
    private LinearLayout lytNotifs;
    private RelativeLayout lytGymProfileUpgrade;
    int idsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_details);
        idsend = getIntent().getIntExtra("idgym", -1);
        Toast.makeText(this, ""+idsend, Toast.LENGTH_SHORT).show();
        initView();
        getInfo();
        //set image darker
        imgGym.setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

        public void getInfo() {

        gymModel = new GymModel();
        webServiceCoachInfo = new WebServiceCoachInfo();
        webServiceCoachInfo.execute();

    }
    public void setbuttons()
    {
        imgEditGymDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GymDetailsActivity.this, GymEditProfileActivity.class);
                intent.putExtra("CoachId", idsend);
                intent.putExtra("CoachName", gymModel.Name);
                intent.putExtra("GymAddress", gymModel.Address);
                intent.putExtra("CoachImg", gymModel.ImgName);
                //intent.putExtra("CoachNatCode", gymModel.natCode);
                intent.putExtra("CoachEmail", gymModel.Email);
                intent.putExtra("CoachMobile", gymModel.Mobile);
                intent.putExtra("CoachIdTelegram", gymModel.Telegram);
                intent.putExtra("CoachIdInstagram", gymModel.Instagram);
                startActivity(intent);
            }
        });

        lytGymProfileUpgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(GymDetailsActivity.this, CoachPlanActivity.class);
//                intent.putExtra("idCoachOrGym", idsend);
//                intent.putExtra("idPlan", 2);
//                intent.putExtra("work", gymModel.workTime);
//                intent.putExtra("about", gymModel.About);
//                startActivity(intent);

                Toast.makeText(getApplicationContext(), "این بخش به زودی فعال خواهد شد..." , Toast.LENGTH_LONG).show();

            }
        });


        lytGymHonours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 0);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 3);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 4);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 5);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 1);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        lytNotifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 7);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
        lytClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 2);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
        lytWorkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymDetailsActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", true);
                    intent.putExtra("SelectedTabIndex", 6);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                } else {
                    Toast.makeText(GymDetailsActivity.this, "برای دسترسی به این بخش باید پروفایل خود را فعال کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initView() {
        imgGym = (ImageView) findViewById(R.id.imgGym);
        imgEditGymDetails = (ImageView) findViewById(R.id.imgEditGymDetails);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtGymName = (TextView) findViewById(R.id.txtGymName);
        txtCoachLevel = (TextView) findViewById(R.id.txtCoachLevel);
        rating = (RatingBar) findViewById(R.id.rating);
        txtCoachRate = (TextView) findViewById(R.id.txtCoachRate);
        txtLikeCount = (TextView) findViewById(R.id.txtLikeCount);
        imgLockHonours = (ImageView) findViewById(R.id.imgLockHonours);
        lytGymHonours = (LinearLayout) findViewById(R.id.lytGymHonours);
        imgLockAbout = (ImageView) findViewById(R.id.imgLockAbout);
        lytAbout = (LinearLayout) findViewById(R.id.lytAbout);
        imgLockClip = (ImageView) findViewById(R.id.imgLockClip);
        lytClip = (LinearLayout) findViewById(R.id.lytClip);
        imgLockPhotos = (ImageView) findViewById(R.id.imgLockPhotos);
        lytPhotos = (LinearLayout) findViewById(R.id.lytPhotos);
        imgLockCoaches = (ImageView) findViewById(R.id.imgLockCoaches);
        lytCoaches = (LinearLayout) findViewById(R.id.lytCoaches);
        imgLockCourse = (ImageView) findViewById(R.id.imgLockCourse);
        lytCourses = (LinearLayout) findViewById(R.id.lytCourses);
        imgLockWorkTime = (ImageView) findViewById(R.id.imgLockWorkTime);
        lytWorkTime = (LinearLayout) findViewById(R.id.lytWorkTime);
        imgLockNotifs = (ImageView) findViewById(R.id.imgLockNotifs);
        lytNotifs = (LinearLayout) findViewById(R.id.lytNotifs);
        lytGymProfileUpgrade = (RelativeLayout) findViewById(R.id.lytGymProfileUpgrade);
    }


    private class WebServiceCoachInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            gymModel = new GymModel();
            dialog = new Dialog(GymDetailsActivity.this);
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

            gymModel = webService.getGymInfo(App.isInternetOn(), idsend);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (gymModel != null) {

                if (gymModel.ImgName != null)
                    if (!gymModel.ImgName.equals("") && !gymModel.ImgName.equals("null"))
                        Glide.with(GymDetailsActivity.this).load(App.imgAddr + gymModel.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgGym);
                txtGymName.setText(gymModel.Name);
                ClassLevels classLevels = new ClassLevels();
                txtCoachLevel.setText(classLevels.getCoachLevelName(gymModel.idCurrentPlan));
                String strRate = String.valueOf(gymModel.Rate);
                if (strRate.length() > 3)
                    strRate = strRate.substring(0, 3);
                txtCoachRate.setText(strRate);
                txtLikeCount.setText(gymModel.like + "");
                rating.setRating((float) gymModel.Rate);
                if (gymModel.IsVerified) {

                    lytGymHonours.setAlpha(1);
                    imgLockHonours.setVisibility(View.GONE);
                    lytPhotos.setAlpha(1);
                    imgLockPhotos.setVisibility(View.GONE);
                    lytCoaches.setAlpha(1);
                    imgLockCoaches.setVisibility(View.GONE);
                    lytCourses.setAlpha(1);
                    imgLockCourse.setVisibility(View.GONE);
                    lytAbout.setAlpha(1);
                    imgLockAbout.setVisibility(View.GONE);
                    lytNotifs.setAlpha(1);
                    lytClip.setAlpha(1);
                    lytWorkTime.setAlpha(1);
                    imgLockNotifs.setVisibility(View.GONE);
                    imgLockWorkTime.setVisibility(View.GONE);
                    imgLockClip.setVisibility(View.GONE);
                    setbuttons();

                }

            } else {

                Toast.makeText(GymDetailsActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();
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
