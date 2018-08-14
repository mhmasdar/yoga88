package com.technologygroup.rayannoor.yoga.referees;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.WebService;

public class RefereeDetailsActivity extends AppCompatActivity {

    private LinearLayout lytParent;
    private ImageView lytBack;
    private RoundedImageView imgReferee;
    private TextView txtRefereeName;
    private TextView txtRefereeCity;
    private ImageView imgTelegram;
    private ImageView imgInstagram;
    private ImageView imgSorush;
    private ImageView imgEmail;
    private ImageView imgCall;
    private LinearLayout lytRefereeRating;
    private RatingBar RatingBarReferee;
    private TextView txtRefereeRate;
    private LikeButton btnLike;
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
    private FloatingActionButton floatAction;
    String Email;
    String Telegram;
    String instagram;
    String mobile;
    String sorosh;
    int idReffre;
    CoachModel coachModel;
    int idsend;
    Boolean calledFromPanel;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_details);
        coachModel=new CoachModel();
        getInfo();
        initView();
        setDetail();

    }
    public void getInfo() {

        coachModel = new CoachModel();

        idsend = getIntent().getIntExtra("idReffre", -1);
        calledFromPanel = getIntent().getBooleanExtra("calledFromPanel", false);
        Toast.makeText(this, ""+idsend, Toast.LENGTH_SHORT).show();
        RefereeDetailsActivity.WebServiceCallgetDetail callCity = new RefereeDetailsActivity.WebServiceCallgetDetail();
        callCity.execute();



    }
    private void initView() {
        lytParent = (LinearLayout) findViewById(R.id.lytParent);
        lytBack = (ImageView) findViewById(R.id.lytBack);
        imgReferee = (RoundedImageView) findViewById(R.id.imgReferee);
        txtRefereeName = (TextView) findViewById(R.id.txtRefereeName);
        txtRefereeCity = (TextView) findViewById(R.id.txtRefereeCity);
        imgTelegram = (ImageView) findViewById(R.id.imgTelegram);
        imgInstagram = (ImageView) findViewById(R.id.imgInstagram);
        imgSorush = (ImageView) findViewById(R.id.imgSorush);
        imgEmail = (ImageView) findViewById(R.id.imgEmail);
        imgCall = (ImageView) findViewById(R.id.imgCall);
        lytRefereeRating = (LinearLayout) findViewById(R.id.lytRefereeRating);
        RatingBarReferee = (RatingBar) findViewById(R.id.RatingBarReferee);
        txtRefereeRate = (TextView) findViewById(R.id.txtRefereeRate);
        btnLike = (LikeButton) findViewById(R.id.btnLike);
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
        imgLockResume = (ImageView) findViewById(R.id.imgLockResume);
        imgLockCertificates = (ImageView) findViewById(R.id.imgLockCertificates);
        imgLockCourse = (ImageView) findViewById(R.id.imgLockCourse);
        imgLockBio = (ImageView) findViewById(R.id.imgLockBio);
        floatAction = (FloatingActionButton) findViewById(R.id.floatAction);

    }
    public void setDetail()
    {
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        idReffre=b.getInt("idReffre");
        String name=  b.getString("name");
        Email=  b.getString("Email");
        Telegram=b.getString("Telegram");
        instagram=b.getString("instagram");
        mobile=b.getString("mobile");
        sorosh=b.getString("sorosh");
        String city=b.getString("city");
        String state=b.getString("state");
        int like=b.getInt("Like");
        float puan=b.getInt("puan");
        RatingBarReferee.setRating(puan);
        txtRefereeName.setText(name);
        txtLikeCount.setText(""+like);
        txtRefereeCity.setText(state + "/n" + city);
        txtRefereeRate.setText(""+puan);
    }
    public void call() throws InterruptedException {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:09367584300"));

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }
    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {Email};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setViews() {

//        if (coachModel.Img != null)
//            if (!coachModel.Img.equals("") && !coachModel.Img.equals("null"))
//                Glide.with(CoachDetailsActivity.this).load(App.imgAddr + coachModel.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCoach);


        txtRefereeName.setText(coachModel.fName + " " + coachModel.lName);
        txtLikeCount.setText(coachModel.like + "");
        txtRefereeCity.setText(coachModel.State + "\n" + coachModel.City);
        String strRate = String.valueOf(coachModel.Rate);
        if (strRate.length() > 3)
            strRate = strRate.substring(0, 3);
        txtRefereeRate.setText(strRate);
        RatingBarReferee.setRating((float) coachModel.Rate);


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
            floatAction.show();
            btnLike.setEnabled(true);
        }


    }
    private void others() {
        floatAction.hide();
        setViews();
        lytResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", calledFromPanel);
                    intent.putExtra("SelectedTabIndex", 0);
                    intent.putExtra("idCoach", coachModel.id);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });
        lytBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 1);
                    intent.putExtra("idCoach", coachModel.id);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });


        lytCertificates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 2);
                    intent.putExtra("idCoach", coachModel.id);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });

        lytEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 3);
                    intent.putExtra("idCoach", coachModel.id);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });

        lytCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 4);
                    intent.putExtra("idCoach", coachModel.id);
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            webService = new WebService();
            dialog = new Dialog(RefereeDetailsActivity.this);
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
        getInfo();
    }
}
