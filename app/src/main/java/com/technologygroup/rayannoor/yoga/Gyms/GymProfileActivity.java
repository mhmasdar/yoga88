package com.technologygroup.rayannoor.yoga.Gyms;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassLevels;
import com.technologygroup.rayannoor.yoga.CommentsActivity;
import com.technologygroup.rayannoor.yoga.LoginActivity;
import com.technologygroup.rayannoor.yoga.Models.GymModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class GymProfileActivity extends AppCompatActivity {

    private ImageView gymImage;
    private TextView txtCoachName;
    private ImageView imgTelegram;
    private ImageView imgInstagram;
    private ImageView imgEmail;
    private ImageView imgCall;
    private LinearLayout lytCoachRating;
    private RatingBar RatingBarCoach;
    private TextView txtCoachRate;
    private LikeButton btnLike;
    private TextView txtLikeCount;
    private ImageView imgLockHonours;
    private LinearLayout lytGymHonours;
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
    private ImageView imgNavigate;
    private TextView txtAddress;
    private TextView txtGymName;
    private LinearLayout lytGymRating;
    private RatingBar RatingBarGym;
    private TextView txtGymRate;
    private RelativeLayout btnBack;
    private ImageView imgLockGyms;
    private LinearLayout lytGyms;
    private LinearLayout lytGymAddress, lytParent;
    Boolean calledFromPanel;
    Dialog dialog;

    // dialog rating
    Dialog dialogRating;
    RatingBar rating_dialog;
    CircularProgressButton btnOk;
    ImageView imgClose;

    GymModel gymModel;
    private boolean CanLike = true, CanRate = true;
    private boolean isLiked = false;
    private int idUser;
    private SharedPreferences prefs;
    WebServiceCallRateAdd webServiceCallRateAdd;
    WebServiceCallLike like;
    WebServiceCallLike webServiceCallLike;
    private ImageView imgSorush;
    private ImageView imgLockAbout;
    private LinearLayout lytAbout;
    private ImageView imgLockClip;
    private LinearLayout lytClip;
    private int idsend;
    private SharedPreferences likes;
    String reqtoprefer;
    String reqtopreferRate;
    boolean liked;
    float Rated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_profile);
        initView();
        gymModel = new GymModel();
        calledFromPanel = getIntent().getBooleanExtra("calledFromPanel", false);
        idsend = getIntent().getIntExtra("idgym", -1);
        Toast.makeText(this, ""+idsend, Toast.LENGTH_SHORT).show();
        WebServiceCoachInfo webServiceCoachInfo = new WebServiceCoachInfo();
        webServiceCoachInfo.execute();

        getWorkTime();
        prefs = getSharedPreferences("User", 0);
        idUser = prefs.getInt("idUser", -1);
        reqtoprefer=""+idUser+":"+idsend;
        reqtopreferRate=""+idUser+"::"+idsend;
        likes = getSharedPreferences("Likes", 0);
        liked=likes.getBoolean(reqtoprefer,false);
        btnLike.setLiked(liked);
        Rated=likes.getFloat(reqtopreferRate,0);
        //set image dark
        gymImage.setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);


        btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                if (gymModel.IsVerified) {

                    if (CanLike) {

                        if (idUser > 0) {

                            CanLike = false;

                            gymModel.like++;
                            txtLikeCount.setText(gymModel.like + "");
                            webServiceCallLike = new WebServiceCallLike(true);
                            webServiceCallLike.execute();

                        } else {

                            btnLike.setLiked(false);

                            Snackbar snackbar = Snackbar.make(lytParent, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                            snackbar.setAction("ثبت نام", new registerAction());

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

                if (gymModel.IsVerified) {
                    if (CanLike) {

                        if (idUser > 0) {

                            CanLike = false;

                            gymModel.like--;
                            txtLikeCount.setText(gymModel.like + "");
                            like = new WebServiceCallLike(false);
                            like.execute();

                        } else {

                            Snackbar snackbar = Snackbar.make(lytParent, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                            snackbar.setAction("ثبت نام", new registerAction());

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

        lytGymRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gymModel.IsVerified) {
                    showRatingDialog();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        gymImage = (ImageView) findViewById(R.id.gymImage);

        //ic_lock = (ImageView) findViewById(R.id.ic_lock);
        txtCoachName = (TextView) findViewById(R.id.txtCoachName);
        imgTelegram = (ImageView) findViewById(R.id.imgTelegram);
        imgInstagram = (ImageView) findViewById(R.id.imgInstagram);
        imgEmail = (ImageView) findViewById(R.id.imgEmail);
        imgCall = (ImageView) findViewById(R.id.imgCall);
        lytCoachRating = (LinearLayout) findViewById(R.id.lytCoachRating);
        RatingBarCoach = (RatingBar) findViewById(R.id.RatingBarCoach);
        txtCoachRate = (TextView) findViewById(R.id.txtCoachRate);
        btnLike = (LikeButton) findViewById(R.id.btnLike);
        txtLikeCount = (TextView) findViewById(R.id.txtLikeCount);
        imgLockHonours = (ImageView) findViewById(R.id.imgLockHonours);
        lytGymHonours = (LinearLayout) findViewById(R.id.lytGymHonours);
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
        imgNavigate = (ImageView) findViewById(R.id.imgNavigate);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtGymName = (TextView) findViewById(R.id.txtGymName);
        lytGymRating = (LinearLayout) findViewById(R.id.lytGymRating);
        RatingBarGym = (RatingBar) findViewById(R.id.RatingBarGym);
        txtGymRate = (TextView) findViewById(R.id.txtGymRate);
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        imgLockGyms = (ImageView) findViewById(R.id.imgLockGyms);
        lytGyms = (LinearLayout) findViewById(R.id.lytGyms);
        lytGymAddress = (LinearLayout) findViewById(R.id.lytGymAddress);
        lytParent = findViewById(R.id.lytParent);
        imgSorush = (ImageView) findViewById(R.id.imgSorush);
        imgLockAbout = (ImageView) findViewById(R.id.imgLockAbout);
        lytAbout = (LinearLayout) findViewById(R.id.lytAbout);
        imgLockClip = (ImageView) findViewById(R.id.imgLockClip);
        lytClip = (LinearLayout) findViewById(R.id.lytClip);
    }

    public void getWorkTime() {

        gymModel = new GymModel();
        gymModel.Name = getIntent().getStringExtra("Name");
        gymModel.fname = getIntent().getStringExtra("fName");
        gymModel.Email = getIntent().getStringExtra("Email");
        gymModel.Instagram = getIntent().getStringExtra("Instagram");
        gymModel.lName = getIntent().getStringExtra("lName");
        gymModel.Telegram = getIntent().getStringExtra("Telegram");
        //gymModel.Img = getIntent().getStringExtra("Img");
        gymModel.id = getIntent().getIntExtra("id", -1);
        gymModel.idCity = getIntent().getIntExtra("idCity", -1);
//        getIntent().getIntExtra("idState", 1);
        gymModel.idCurrentPlan = getIntent().getIntExtra("idCurrentPlan", -1);
        gymModel.like = getIntent().getIntExtra("like", 0);
        //gymModel.lastUpdate = getIntent().getStringExtra("lastUpdate");
        gymModel.Tell = getIntent().getStringExtra("Tell");
        gymModel.Rate = getIntent().getDoubleExtra("Rate", 0);
        gymModel.City = getIntent().getStringExtra("City");
        gymModel.State = getIntent().getStringExtra("State");
        gymModel.Des = getIntent().getStringExtra("Des");
        gymModel.workTime = getIntent().getStringExtra("workTime");
        gymModel.Address = getIntent().getStringExtra("Address");
        gymModel.Lat = getIntent().getDoubleExtra("Lat", 0);
        gymModel.Lon = getIntent().getDoubleExtra("Lon", 0);


    }

    private void setViews() {

        if (gymModel.IsVerified)
            if (gymModel.ImgName != null)
                if (!gymModel.ImgName.equals("") && !gymModel.ImgName.equals("null"))
                    Glide.with(GymProfileActivity.this).load(App.imgAddr + gymModel.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(gymImage);
        txtGymName.setText(gymModel.Name);
        txtLikeCount.setText(gymModel.like + "");
        txtAddress.setText(gymModel.Address);
//        txtCoachCity.setText(coachModel.State + "\n" + coachModel.City);
        String strRate = String.valueOf(gymModel.Rate);
        txtGymRate.setText(strRate);
        RatingBarGym.setRating((float) gymModel.Rate);
        if (gymModel.IsVerified) {
//            ic_lock.setVisibility(View.GONE);
//            imgLockGyms.setVisibility(View.GONE);
            lytCoaches.setAlpha(1);
            imgLockCoaches.setVisibility(View.GONE);
            lytCourses.setAlpha(1);
            imgLockCourse.setVisibility(View.GONE);
            lytPhotos.setAlpha(1);
            imgLockPhotos.setVisibility(View.GONE);
            lytGymHonours.setAlpha(1);
            imgLockHonours.setVisibility(View.GONE);
            lytWorkTime.setAlpha(1);
            imgLockWorkTime.setVisibility(View.GONE);
            lytNotifs.setAlpha(1);
            imgLockNotifs.setVisibility(View.GONE);
            lytAbout.setAlpha(1);
            imgLockAbout.setVisibility(View.GONE);
            lytClip.setAlpha(1);
            imgLockClip.setVisibility(View.GONE);
            btnLike.setEnabled(true);
//            lytCoachRating.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (gymModel.IsVerified) {
//                        showRatingDialog();
//                    }
//                }
//            });
        }

    }

    public class registerAction implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent i = new Intent(GymProfileActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    private void showRatingDialog() {
        dialogRating = new Dialog(GymProfileActivity.this);
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
                        webServiceCallRateAdd = new WebServiceCallRateAdd();
                        webServiceCallRateAdd.execute();
                    }
                    else
                    {
                        Toast.makeText(GymProfileActivity.this, "امتیاز شما قبلا ثبت شده است", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    dialogRating.dismiss();
                    Snackbar snackbar = Snackbar.make(lytParent, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                    snackbar.setAction("ثبت نام", new registerAction());
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
            dialog = new Dialog(GymProfileActivity.this);
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
            result = webService.postLike(App.isInternetOn(), idsend);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SharedPreferences.Editor editor = prefs.edit();
            dialog.dismiss();

            if (isLiked) {

                if (result != null) {

                    if (result.equals("Ok")) {

                        editor.putBoolean(reqtoprefer, true);
                        editor.apply();

                    } else {
                        Toast.makeText(GymProfileActivity.this, "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(false);
                        gymModel.like--;
                        txtLikeCount.setText(gymModel.like + "");
                    }

                } else {
                    Toast.makeText(GymProfileActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(false);
                    gymModel.like--;
                    txtLikeCount.setText(gymModel.like + "");
                }

            } else {
                if (result != null) {

                    if (Integer.parseInt(result) == 1) {

                        editor.putBoolean("isLiked_idCoachOrGym:" + idsend, false);
                        editor.apply();

                    } else {
                        Toast.makeText(GymProfileActivity.this, "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(true);
                        gymModel.like++;
                        txtLikeCount.setText(gymModel.like + "");
                    }

                } else {
                    Toast.makeText(GymProfileActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                    gymModel.like++;
                    txtLikeCount.setText(gymModel.like + "");
                }

            }

            CanLike = true;

        }

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
            result = webService.postRate(App.isInternetOn(), idsend, "Gym", (float) rate);
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
                    WebServiceCoachInfo webServiceCoachInfo=new WebServiceCoachInfo();
                    webServiceCoachInfo.execute();

                } else {

                    btnOk.revertAnimation();
                    Toast.makeText(GymProfileActivity.this, "ثبت امتیاز نا موفق", Toast.LENGTH_LONG).show();
                }

            } else {

                btnOk.revertAnimation();
                Toast.makeText(GymProfileActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }
    private class WebServiceCoachInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            gymModel = new GymModel();



            dialog = new Dialog(GymProfileActivity.this);
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

            gymModel = webService.getGymInfo(App.isInternetOn(), idsend);
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (gymModel != null) {

//                if (gymModel.Img != null)
//                    if (!gymModel.Img.equals("") && !gymModel.Img.equals("null"))
//                        Glide.with(GymProfileActivity.this).load(App.imgAddr + gymModel.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgGym);
                txtGymName.setText(gymModel.Name);
                ClassLevels classLevels = new ClassLevels();
                //txtCoachLevel.setText(classLevels.getCoachLevelName(gymModel.idCurrentPlan));
                String strRate = String.valueOf(gymModel.Rate);
                if (strRate.length() > 3)
                    strRate = strRate.substring(0, 3);
//                txtCoachRate.setText(strRate);
                txtLikeCount.setText(gymModel.like + "");
               // rating.setRating((float) gymModel.Rate);

                if (gymModel.IsVerified) {

                    lytGymHonours.setAlpha(1);
                    imgLockHonours.setVisibility(View.GONE);
                    lytPhotos.setAlpha(1);
                    imgLockPhotos.setVisibility(View.GONE);
                    lytCoaches.setAlpha(1);
                    imgLockCoaches.setVisibility(View.GONE);
                    lytCourses.setAlpha(1);
                    imgLockCourse.setVisibility(View.GONE);
//                   lytComments.setAlpha(1);
//                    imgLockComments.setVisibility(View.GONE);
                    lytAbout.setAlpha(1);
                    imgLockAbout.setVisibility(View.GONE);
                    lytNotifs.setAlpha(1);
                    imgLockNotifs.setVisibility(View.GONE);
                    setbuttons();
                    setViews();

                }

            } else {

                Toast.makeText(GymProfileActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                finish();

            }


        }

    }
    @Override
    public void onStop() {
        super.onStop();

        if (webServiceCallRateAdd != null)
            if (webServiceCallRateAdd.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCallRateAdd.cancel(true);
        if (like != null)
            if (like.getStatus() == AsyncTask.Status.RUNNING)
                like.cancel(true);
        if (webServiceCallLike != null)
            if (webServiceCallLike.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCallLike.cancel(true);
    }
    public void setbuttons()
    {
        lytGymHonours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymProfileActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 0);
                    intent.putExtra("idGym", idsend);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("about", gymModel.About);
                    startActivity(intent);
                }
            }
        });


        lytPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymProfileActivity.this, GymServiceActivity.class);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 3);
                    intent.putExtra("idGym", idsend);
                    startActivity(intent);
                }
            }
        });

        lytCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymProfileActivity.this, GymServiceActivity.class);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 4);
                    intent.putExtra("idGym", idsend);
                    startActivity(intent);
                }
            }
        });

        lytCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymProfileActivity.this, GymServiceActivity.class);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 5);

                    intent.putExtra("idGym", idsend);
                    startActivity(intent);
                }
            }
        });

        lytWorkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymProfileActivity.this, GymServiceActivity.class);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 6);

                    intent.putExtra("idGym", idsend);
                    startActivity(intent);
                }
            }
        });

        lytNotifs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymProfileActivity.this, GymServiceActivity.class);
                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 7);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("idGym", idsend);
                    startActivity(intent);
                }
            }
        });

        lytAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gymModel.IsVerified) {
                    Intent intent = new Intent(GymProfileActivity.this, GymServiceActivity.class);

                    intent.putExtra("calledFromPanel", false);
                    intent.putExtra("SelectedTabIndex", 1);
                    intent.putExtra("work", gymModel.workTime);
                    intent.putExtra("idGym", idsend);
                    startActivity(intent);
                }
            }
        });
        imgTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gymModel.IsVerified) {
                    if (gymModel.Telegram != null) {
                        if (!gymModel.Telegram.equals("") && !gymModel.Telegram.equals("null")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + gymModel.Telegram));
                            startActivity(intent);
                        } else {
                            Toast.makeText(GymProfileActivity.this, "آی دی تلگرام موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(GymProfileActivity.this, "آی دی تلگرام موجود نیست", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gymModel.IsVerified) {
                    if (gymModel.Tell != null) {
                        if (!gymModel.Tell.equals("") && !gymModel.Tell.equals("null")) {
                            Intent intentCall = new Intent(Intent.ACTION_DIAL);
                            intentCall.setData(Uri.fromParts("tel", "0" + gymModel.Tell, null));
                            startActivity(intentCall);
                        } else {
                            Toast.makeText(GymProfileActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(GymProfileActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gymModel.IsVerified) {
                    if (gymModel.Email != null) {
                        if (!gymModel.Email.equals("") && !gymModel.Email.equals("null")) {

                            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "نرم افزار یوگا");
//                        intent.putExtra(Intent.EXTRA_TEXT, txtEmailBody.getText().toString());
                            intent.setData(Uri.parse("mailto:" + gymModel.Email)); // or just "mailto:" for blank
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                            try {
                                startActivity(Intent.createChooser(intent, "ارسال ایمیل از طریق"));
                            } catch (ActivityNotFoundException ex) {
                                Toast.makeText(getApplicationContext(), "در دستگاه شما هیچ برنامه ای برای ارسال ایمیل وجود ندارد", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(GymProfileActivity.this, "ایمیل موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(GymProfileActivity.this, "ایمیل موجود نیست", Toast.LENGTH_LONG).show();
                }

            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gymModel.IsVerified) {
                    if (gymModel.Instagram != null) {
                        if (!gymModel.Instagram.equals("") && !gymModel.Instagram.equals("null")) {

                            Uri uri = Uri.parse("http://instagram.com/_u/" + gymModel.Instagram);
                            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                            likeIng.setPackage("com.instagram.android");

                            try {
                                startActivity(likeIng);
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://instagram.com/" + gymModel.Instagram)));
                            }

                        } else {
                            Toast.makeText(GymProfileActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(GymProfileActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                }

            }
        });

        imgNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "http://maps.google.com/maps?daddr=" + gymModel.Lat + "," + gymModel.Lon;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
                startActivity(intent);
            }
        });
    }

}
