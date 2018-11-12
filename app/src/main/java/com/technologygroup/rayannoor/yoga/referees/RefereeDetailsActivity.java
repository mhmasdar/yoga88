package com.technologygroup.rayannoor.yoga.referees;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

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
    String Email;
    String Telegram;
    String instagram;
    String mobile;
    String sorosh;
    private boolean CanLike = true, CanRate = true;
    int idReffre;
    CoachModel coachModel;
    int idsend;
    Boolean calledFromPanel;
    private Dialog dialog;
    int idUser;
    SharedPreferences mypref;
    private SharedPreferences likes;
    String reqtoprefer;
    String reqtopreferRate;
    boolean liked;
    float Rated;
    boolean getdetailed=true;
    Dialog dialogRating;
    RatingBar rating_dialog;
    CircularProgressButton btnOk;
    ImageView imgClose;
    WebServiceCallLike webServiceCallLike;
    WebServiceCallgetDetail webServiceCallgetDetail;
    WebServiceCallِDisLike dislike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_details);

        coachModel=new CoachModel();
        getInfo();
        initView();
        mypref = getSharedPreferences("User", 0);
        idUser = mypref.getInt("idUser", -1);
        reqtoprefer=""+idUser+":"+idsend;
        reqtopreferRate=""+idUser+"::"+idsend;
        likes = getSharedPreferences("Likes", 0);
        liked=likes.getBoolean(reqtoprefer,false);
        btnLike.setLiked(liked);
        Rated=likes.getFloat(reqtopreferRate,0);
        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setDetail();

    }
    public void getInfo() {

        coachModel = new CoachModel();
        idsend = getIntent().getIntExtra("idReffre", -1);
        calledFromPanel = getIntent().getBooleanExtra("calledFromPanel", false);

        WebServiceCallgetDetail callCity = new WebServiceCallgetDetail();
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

        if (coachModel.ImgName != null)
            if (!coachModel.ImgName.equals("") && !coachModel.ImgName.equals("null"))
                Glide.with(RefereeDetailsActivity.this).load(App.imgAddr + coachModel.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgReferee);


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
            btnLike.setEnabled(true);
            lytRefereeRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (coachModel.IsVerified) {
                        showRatingDialog();
                    }
                }
            });
        }


    }
    private void others() {
        setViews();
        lytResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
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
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
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
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
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
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
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
                    Intent intent = new Intent(RefereeDetailsActivity.this, RefereeServicesActivity.class);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 4);
                    intent.putExtra("idCoach", idsend);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });
        dialog.dismiss();
        btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {


                if (coachModel.IsVerified) {

                    if (CanLike) {

                        if (idUser > 0) {

                            CanLike = false;
                            coachModel.like++;
                            txtLikeCount.setText(coachModel.like + "");
                            webServiceCallLike = new WebServiceCallLike(true);
                            webServiceCallLike.execute();

                        } else {

                            btnLike.setLiked(false);

                            Snackbar snackbar = Snackbar.make(lytParent, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                            //snackbar.setAction("ثبت نام", new CoachDetailsActivity.registerAction());

                            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                            TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                            textView.setLayoutParams(parms);
                            textView.setGravity(Gravity.LEFT);
                            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        }
                    }

                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {


                if (coachModel.IsVerified) {

                    if (CanLike) {

                        if (idUser > 0) {

                            CanLike = false;
                            coachModel.like--;
                            txtLikeCount.setText(coachModel.like + "");
                            dislike = new WebServiceCallِDisLike(false);
                            dislike.execute();

                        } else {

                            Snackbar snackbar = Snackbar.make(lytParent, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                            //snackbar.setAction("ثبت نام", new RefereeDetailsActivity().registerAction());

                            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                            TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                            textView.setLayoutParams(parms);
                            textView.setGravity(Gravity.LEFT);
                            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                            snackbar.show();
                        }
                    }
                }
            }
        });

    }
    private class WebServiceCallLike extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        boolean isLiked;

        public WebServiceCallLike(boolean isLiked) {
            this.isLiked = isLiked;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postLike(App.isInternetOn(), idsend);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SharedPreferences.Editor editor = likes.edit();

            if (isLiked) {

                if (result != null) {

                    if (result.equals("Ok")) {

                        editor.putBoolean(reqtoprefer, true);
                        editor.apply();

                    } else {
                        Toast.makeText(RefereeDetailsActivity.this, "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(false);
                        coachModel.like--;
                        txtLikeCount.setText(coachModel.like + "");
                        editor.putBoolean(reqtoprefer + idsend, false);
                        editor.apply();
                    }

                } else {
                    Toast.makeText(RefereeDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(false);
                    coachModel.like--;
                    txtLikeCount.setText(coachModel.like + "");
                    editor.putBoolean(reqtoprefer + idsend, false);
                    editor.apply();
                }

            } else {
                if (result != null) {

                    if (result.equals("Ok")) {
                        editor.putBoolean("isLiked_idCoachOrGym:" + idsend, false);
                        editor.apply();

                    } else {
                        Toast.makeText(RefereeDetailsActivity.this, "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(true);
                        coachModel.like++;
                        txtLikeCount.setText(coachModel.like + "");
                    }

                } else {
                    Toast.makeText(RefereeDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                    coachModel.like++;
                    txtLikeCount.setText(coachModel.like + "");
                }

            }

            CanLike = true;

        }

    }
    private class WebServiceCallِDisLike extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        boolean isLiked;

        public WebServiceCallِDisLike(boolean isLiked) {
            this.isLiked = isLiked;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postdisLike(App.isInternetOn(), idsend);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SharedPreferences.Editor editor = likes.edit();

            if (isLiked) {

                if (result != null) {

                    if (result.equals("Ok")) {

                        editor.putBoolean(reqtoprefer, false);
                        editor.apply();

                    } else {
                        Toast.makeText(RefereeDetailsActivity.this, "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(false);
                        coachModel.like++;
                        txtLikeCount.setText(coachModel.like + "");
                        editor.putBoolean(reqtoprefer + idsend, false);
                        editor.apply();
                    }

                } else {
                    Toast.makeText(RefereeDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(false);
                    coachModel.like++;
                    txtLikeCount.setText(coachModel.like + "");
                    editor.putBoolean(reqtoprefer + idsend, false);
                    editor.apply();
                }

            } else {
                if (result != null) {

                    if (result.equals("Ok")) {

                        editor.putBoolean("isLiked_idCoachOrGym:" + idsend, false);
                        editor.apply();

                    } else {
                        Toast.makeText(RefereeDetailsActivity.this, "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(true);
                        coachModel.like++;
                        txtLikeCount.setText(coachModel.like + "");
                    }

                } else {
                    Toast.makeText(RefereeDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                    coachModel.like++;
                    txtLikeCount.setText(coachModel.like + "");
                }

            }

            CanLike = true;

        }

    }
    private void showRatingDialog() {
        dialogRating = new Dialog(this);
        dialogRating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRating.setContentView(R.layout.dialog_rating);
        rating_dialog = (RatingBar) dialogRating.findViewById(R.id.rating_dialog);
        btnOk = dialogRating.findViewById(R.id.btnOk);
        imgClose = dialogRating.findViewById(R.id.imgClose);
        rating_dialog.setRating(Rated);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRating.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idUser > 0) {
                    if(Rated==0) {
                        WebServiceCallRateAdd webServiceCallRateAdd = new WebServiceCallRateAdd();
                        webServiceCallRateAdd.execute();
                    }
                    else
                    {
                        Toast.makeText(RefereeDetailsActivity.this, "امتیاز شما قبلا ثبت شده است", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    dialogRating.dismiss();
                    Snackbar snackbar = Snackbar.make(lytParent, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                    //snackbar.setAction("ثبت نام", new registerAction());
                    Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                    TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                    textView.setLayoutParams(parms);
                    textView.setGravity(Gravity.LEFT);
                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                }

            }
        });
        dialogRating.setCancelable(true);
        dialogRating.setCanceledOnTouchOutside(true);
        dialogRating.show();
    }
    private class WebServiceCallRateAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        double rate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnOk.startAnimation();
            webService = new WebService();
            rate = rating_dialog.getRating();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postRate(App.isInternetOn(), idsend, "referee", (float) rate);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (result.equals("Ok")) {

                    SharedPreferences.Editor editor = likes.edit();
                    editor.putFloat(reqtopreferRate,rating_dialog.getRating());
                    editor.apply();
                    Rated=rating_dialog.getRating();
                    // بعد از اتمام عملیات کدهای زیر اجرا شوند
                    Bitmap icon = BitmapFactory.decodeResource(getResources(),
                            R.drawable.ic_ok);
                    btnOk.doneLoadingAnimation(R.color.green, icon); // finish loading

                    // بستن دیالوگ حتما با تاخیر انجام شود
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogRating.dismiss();
                        }
                    }, 1000);
                    webServiceCallgetDetail=new WebServiceCallgetDetail();
                    webServiceCallgetDetail.execute();

                } else {

                    btnOk.revertAnimation();
                    Toast.makeText(RefereeDetailsActivity.this, "ثبت امتیاز نا موفق", Toast.LENGTH_LONG).show();
                }

            } else {

                btnOk.revertAnimation();
                Toast.makeText(RefereeDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

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
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
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
    public void onStop() {
        super.onStop();

        if (webServiceCallLike != null)
            if (webServiceCallLike.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCallLike.cancel(true);

        if (webServiceCallgetDetail != null)
            if (webServiceCallgetDetail.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCallgetDetail.cancel(true);
        if (dislike != null)
            if (dislike.getStatus() == AsyncTask.Status.RUNNING)
                dislike.cancel(true);
    }
}
