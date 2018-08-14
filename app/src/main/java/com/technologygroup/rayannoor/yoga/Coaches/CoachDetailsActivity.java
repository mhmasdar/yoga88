package com.technologygroup.rayannoor.yoga.Coaches;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.CommentsActivity;
import com.technologygroup.rayannoor.yoga.LoginActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CoachDetailsActivity extends AppCompatActivity {


    private RoundedImageView imgCoach;
    private TextView txtCoachName;
    private TextView txtCoachCity;
    private ImageView imgTelegram;
    private ImageView imgInstagram;
    private ImageView imgEmail;
    private ImageView imgCall;
    private LinearLayout lytCoachRating;
    private RatingBar RatingBarCoach;
    private TextView txtCoachRate;
    private LikeButton btnLike;
    private TextView txtLikeCount;
    private ImageView imgLockEducation;
    private LinearLayout lytEducation;
    private ImageView imgLockResume;
    private ImageView lytBack;
    private LinearLayout lytResume;
    private ImageView imgLockGyms;
    private LinearLayout lytGyms;
    private ImageView imgLockCertificates;
    private LinearLayout lytCertificates, lytParent;
    private FloatingActionButton floatAction;
    private Dialog dialog;

    // dialog rating
    Dialog dialogRating;
    RatingBar rating_dialog;
    CircularProgressButton btnOk;
    ImageView imgClose;


    CoachModel coachModel;
    private boolean CanLike = true, CanRate = true;
    private boolean isLiked = false;
    private int idUser;
    private SharedPreferences prefs;
    WebServiceCallRateAdd webServiceCallRateAdd;
    WebServiceCallLike like;
    WebServiceCallLike webServiceCallLike;
    private ImageView imgLockBio;
    private LinearLayout lytBio;
    private ImageView imgLockCourse;
    private LinearLayout lytCourse;
    private ImageView imgSorush;
    Boolean calledFromPanel;
    int idsend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_profile);
        coachModel = new CoachModel();
        initView();
        idsend = getIntent().getIntExtra("idUser", -1);
        calledFromPanel = getIntent().getBooleanExtra("calledFromPanel", false);
        
        
       /// prefs = getSharedPreferences("User", 0);
        //idUser = prefs.getInt("idUser", -1);
        getInfo();
    }
    private void others()
    {
        floatAction.hide();
        setViews();
        lytResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachDetailsActivity.this, CoachServicesActivity.class);
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
                    Intent intent = new Intent(CoachDetailsActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", calledFromPanel);
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
                    Intent intent = new Intent(CoachDetailsActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", calledFromPanel);
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
                    Intent intent = new Intent(CoachDetailsActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", calledFromPanel);
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
                    Intent intent = new Intent(CoachDetailsActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", calledFromPanel);
                    intent.putExtra("SelectedTabIndex", 4);
                    intent.putExtra("idCoach", coachModel.id);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });

        lytGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coachModel.IsVerified) {
                    Intent intent = new Intent(CoachDetailsActivity.this, CoachServicesActivity.class);
                    intent.putExtra("calledFromPanel", calledFromPanel);
                    intent.putExtra("SelectedTabIndex", 5);
                    intent.putExtra("idCoach", coachModel.id);
                    intent.putExtra("idBio", coachModel.Bio);
                    startActivity(intent);
                }
            }
        });


        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoachDetailsActivity.this, CommentsActivity.class);
                intent.putExtra("IdCoachOrGym", coachModel.id);
                intent.putExtra("IsGym", false);
                startActivity(intent);
            }
        });


        imgTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    if (coachModel.Telegram != null) {
                        if (!coachModel.Telegram.equals("") && !coachModel.Telegram.equals("null")) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + coachModel.Telegram));
                            startActivity(intent);
                        } else {
                            Toast.makeText(CoachDetailsActivity.this, "آی دی تلگرام موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(CoachDetailsActivity.this, "آی دی تلگرام موجود نیست", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    if (coachModel.Mobile != null) {
                        if (!coachModel.Mobile.equals("") && !coachModel.Mobile.equals("null")) {
                            Intent intentCall = new Intent(Intent.ACTION_DIAL);
                            intentCall.setData(Uri.fromParts("tel", "0" + coachModel.Mobile, null));
                            startActivity(intentCall);
                        } else {
                            Toast.makeText(CoachDetailsActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(CoachDetailsActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    if (coachModel.Email != null) {
                        if (!coachModel.Email.equals("") && !coachModel.Email.equals("null")) {

                            Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "نرم افزار یوگا");
//                        intent.putExtra(Intent.EXTRA_TEXT, txtEmailBody.getText().toString());
                            intent.setData(Uri.parse("mailto:" + coachModel.Email)); // or just "mailto:" for blank
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                            try {
                                startActivity(Intent.createChooser(intent, "ارسال ایمیل از طریق"));
                            } catch (ActivityNotFoundException ex) {
                                Toast.makeText(getApplicationContext(), "در دستگاه شما هیچ برنامه ای برای ارسال ایمیل وجود ندارد", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(CoachDetailsActivity.this, "ایمیل موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(CoachDetailsActivity.this, "ایمیل موجود نیست", Toast.LENGTH_LONG).show();
                }

            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    if (coachModel.Instagram != null) {
                        if (!coachModel.Instagram.equals("") && !coachModel.Instagram.equals("null")) {

                            Uri uri = Uri.parse("http://instagram.com/_u/" + coachModel.Instagram);
                            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                            likeIng.setPackage("com.instagram.android");

                            try {
                                startActivity(likeIng);
                            } catch (ActivityNotFoundException e) {
                                startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("http://instagram.com/" + coachModel.Instagram)));
                            }

                        } else {
                            Toast.makeText(CoachDetailsActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                        }
                    } else
                        Toast.makeText(CoachDetailsActivity.this, "شماره تلفن موجود نیست", Toast.LENGTH_LONG).show();
                }

            }
        });


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


                if (coachModel.IsVerified) {

                    if (CanLike) {

                        if (idUser > 0) {

                            CanLike = false;

                            coachModel.like--;
                            txtLikeCount.setText(coachModel.like + "");
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

        lytCoachRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coachModel.IsVerified) {
                    showRatingDialog();
                }
            }
        });

        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {

        imgCoach = (RoundedImageView) findViewById(R.id.imgCoach);
        txtCoachName = (TextView) findViewById(R.id.txtCoachName);
        txtCoachCity = (TextView) findViewById(R.id.txtCoachCity);
        imgTelegram = (ImageView) findViewById(R.id.imgTelegram);
        imgInstagram = (ImageView) findViewById(R.id.imgInstagram);
        lytBack = (ImageView) findViewById(R.id.lytBack);
        imgEmail = (ImageView) findViewById(R.id.imgEmail);
        imgCall = (ImageView) findViewById(R.id.imgCall);
        lytCoachRating = (LinearLayout) findViewById(R.id.lytCoachRating);
        RatingBarCoach = (RatingBar) findViewById(R.id.RatingBarCoach);
        txtCoachRate = (TextView) findViewById(R.id.txtCoachRate);
        btnLike = (LikeButton) findViewById(R.id.btnLike);
        txtLikeCount = (TextView) findViewById(R.id.txtLikeCount);
        imgLockEducation = (ImageView) findViewById(R.id.imgLockEducation);
        lytEducation = (LinearLayout) findViewById(R.id.lytEducation);
        imgLockResume = (ImageView) findViewById(R.id.imgLockResume);
        lytResume = (LinearLayout) findViewById(R.id.lytResume);
        imgLockGyms = (ImageView) findViewById(R.id.imgLockGyms);
        lytGyms = (LinearLayout) findViewById(R.id.lytGyms);
        imgLockCertificates = (ImageView) findViewById(R.id.imgLockCertificates);
        lytCertificates = (LinearLayout) findViewById(R.id.lytCertificates);
        floatAction = (FloatingActionButton) findViewById(R.id.floatAction);
        lytParent = findViewById(R.id.lytParent);
        imgLockBio = (ImageView) findViewById(R.id.imgLockBio);
        lytBio = (LinearLayout) findViewById(R.id.lytBio);
        imgLockCourse = (ImageView) findViewById(R.id.imgLockCourse);
        lytCourse = (LinearLayout) findViewById(R.id.lytCourse);
        imgSorush = (ImageView) findViewById(R.id.imgSorush);
    }

    public void getInfo() {

        coachModel = new CoachModel();

        idsend = getIntent().getIntExtra("idUser", -1);
        WebServiceCallgetDetail callCity = new WebServiceCallgetDetail();
        callCity.execute();



    }

    private void setViews() {

//        if (coachModel.Img != null)
//            if (!coachModel.Img.equals("") && !coachModel.Img.equals("null"))
//                Glide.with(CoachDetailsActivity.this).load(App.imgAddr + coachModel.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCoach);


        txtCoachName.setText(coachModel.fName + " " + coachModel.lName);
        txtLikeCount.setText(coachModel.like + "");
        txtCoachCity.setText(coachModel.State + "\n" + coachModel.City);
        String strRate = String.valueOf(coachModel.Rate);
        if (strRate.length() > 3)
            strRate = strRate.substring(0, 3);
        txtCoachRate.setText(strRate);
        RatingBarCoach.setRating((float) coachModel.Rate);


        if (coachModel.IsVerified) {

            lytEducation.setAlpha(1);
            imgLockEducation.setVisibility(View.GONE);
            lytResume.setAlpha(1);
            imgLockResume.setVisibility(View.GONE);
            lytGyms.setAlpha(1);
            imgLockGyms.setVisibility(View.GONE);
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


    public class registerAction implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent i = new Intent(CoachDetailsActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    private void showRatingDialog() {
        dialogRating = new Dialog(CoachDetailsActivity.this);
        dialogRating.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogRating.setContentView(R.layout.dialog_rating);
        rating_dialog = (RatingBar) dialogRating.findViewById(R.id.rating_dialog);
        btnOk = dialogRating.findViewById(R.id.btnOk);
        imgClose = dialogRating.findViewById(R.id.imgClose);
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

                    webServiceCallRateAdd = new WebServiceCallRateAdd();
                    webServiceCallRateAdd.execute();

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
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postLike(App.isInternetOn(), coachModel.id, idUser, "Coach");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            SharedPreferences.Editor editor = prefs.edit();

            if (isLiked) {

                if (result != null) {

                    if (Integer.parseInt(result) == 1 || Integer.parseInt(result) == -3) {

                        editor.putBoolean("isLiked_idCoachOrGym:" + coachModel.id, true);
                        editor.apply();

                    } else {
                        Toast.makeText(CoachDetailsActivity.this, "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(false);
                        coachModel.like--;
                        txtLikeCount.setText(coachModel.like + "");
                    }

                } else {
                    Toast.makeText(CoachDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(false);
                    coachModel.like--;
                    txtLikeCount.setText(coachModel.like + "");
                }

            } else {
                if (result != null) {

                    if (Integer.parseInt(result) == 1) {

                        editor.putBoolean("isLiked_idCoachOrGym:" + coachModel.id, false);
                        editor.apply();

                    } else {
                        Toast.makeText(CoachDetailsActivity.this, "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(true);
                        coachModel.like++;
                        txtLikeCount.setText(coachModel.like + "");
                    }

                } else {
                    Toast.makeText(CoachDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                    btnLike.setLiked(true);
                    coachModel.like++;
                    txtLikeCount.setText(coachModel.like + "");
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
            result = webService.postRate(App.isInternetOn(), coachModel.id, idUser, "coach", (float) rate);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (Integer.parseInt(result) > 0) {


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


                } else {

                    btnOk.revertAnimation();
                    Toast.makeText(CoachDetailsActivity.this, "ثبت امتیاز نا موفق", Toast.LENGTH_LONG).show();
                }

            } else {

                btnOk.revertAnimation();
                Toast.makeText(CoachDetailsActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
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
            dialog = new Dialog(CoachDetailsActivity.this);
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
            coachModel = webService.getCoachDetail(App.isInternetOn(), idsend);

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
    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
    }

}

