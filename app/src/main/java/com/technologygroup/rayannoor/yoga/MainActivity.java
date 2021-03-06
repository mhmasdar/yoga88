package com.technologygroup.rayannoor.yoga;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Coaches.*;
import com.technologygroup.rayannoor.yoga.Coaches.CoachProfileActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymDetailsActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymsListActivity;
import com.technologygroup.rayannoor.yoga.Models.MainPageModel;
import com.technologygroup.rayannoor.yoga.NavigationMenu.AboutUsActivity;
import com.technologygroup.rayannoor.yoga.NavigationMenu.ChartActivity;
import com.technologygroup.rayannoor.yoga.NavigationMenu.SuggestionActivity;
import com.technologygroup.rayannoor.yoga.NavigationMenu.aboutAppActivity;
import com.technologygroup.rayannoor.yoga.NavigationMenu.commonQuestionActivity;
import com.technologygroup.rayannoor.yoga.NavigationMenu.hameganiActivity;
import com.technologygroup.rayannoor.yoga.NavigationMenu.rulesActivity;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.Teaches.teachsActivity;
import com.technologygroup.rayannoor.yoga.YogaIntroduce.YogaIntroduceActivity;
import com.technologygroup.rayannoor.yoga.adapters.SlidingImage_Adapter;
import com.technologygroup.rayannoor.yoga.referees.RefereeListActivity;
import com.technologygroup.rayannoor.yoga.referees.RefereeProfileActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout mainAppBar;
    private Toolbar toolbar;
    private CoordinatorLayout relativeMsg;
    private TextView txtNewMessageCount;
    private TextView txtTitle;
    private RelativeLayout lytMenu;
    private LinearLayout lytTeachs;
    private LinearLayout lytYogaIntroduce;
    private LinearLayout lytGyms;
    private LinearLayout lytCoaches;
    private NavigationView navigationView;
    private DrawerLayout drawer_layout;
    private ImageView drawerHeaderImage;
    private TextView txtUserName, txtLogin;
    private RoundedImageView imgUser;
    private LinearLayout lytLogin;
    private LinearLayout lytChart;
    private LinearLayout lytAboutApp;
    private LinearLayout lytAboutUs;
    private LinearLayout lytRate;
    private LinearLayout lytChange;
    private LinearLayout lytQuestion;
    private LinearLayout lytRules;
    private LinearLayout lytSuggestion;
    private LinearLayout lytShare;
    private LinearLayout lytBoard;
    private ImageView imgNewTeach;
    private View headerview;
    JSONObject roles;
    private SharedPreferences prefs, CountPrefs;
    public static Dialog dialog;
    public static Spinner StateSpinner;
    public static Spinner CitySpinner;
    private static int currentPage = 0;

    private int idUser;
    private String userType;
    private DrawerLayout drawerLayout;
    private LinearLayout lytCommingSoon;
    private LinearLayout hamegani;
    private ViewPagerCustomDuration pager;
    private LinearLayout lytReferee;
    private ArcNavigationView navView;
    private JSONArray usertypes;
    private String Role, SliderImage;
    private int idField, idState;
    private getSlider slider;
    private getCounts counts;
    private getCommitteeName committeeName;
    private String storedNotifsCount, storedTeachsCount, newNotifsCount, newTeachsCount, totalNotifsCount, totlaTeachsCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        prefs = getSharedPreferences("User", 0);
        userType = prefs.getString("userType", "no");
        idUser = prefs.getInt("idUser", -1);
        idField = prefs.getInt("idField", 0);
        idState = prefs.getInt("idState", 0);

        CountPrefs = getSharedPreferences("Counts", 0);
        storedNotifsCount = CountPrefs.getString("notifsCount", "0");
        storedTeachsCount = CountPrefs.getString("teachsCount", "0");


        slider = new getSlider();
        slider.execute();

        try {
            usertypes = new JSONArray(userType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (idUser > 0) {
            txtUserName.setText(prefs.getString("Name", "") + " " + prefs.getString("lName", ""));
            String image = prefs.getString("ProfileImageName", "null");
            if (!image.equals("") && !image.equals("null"))
                Glide.with(MainActivity.this).load(App.imgAddr + image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgUser);
        }
        try {
            if (usertypes.length() == 1) {
                try {
                    Role = usertypes.getJSONObject(0).getJSONObject("Role").getString("Name");
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                if (Role.equals("Gym")) {
                    txtLogin.setText("پنل باشگاه");
                } else if (Role.equals("Coach")) {
                    txtLogin.setText("پنل مربی");
                } else if (Role.equals("Referee")) {
                    txtLogin.setText("پنل داور");
                } else if (Role.equals("Gym")) {
                    txtLogin.setText("پنل باشگاه");
                } else if (Role.equals("User")) {
                    txtLogin.setText("حساب کاربری");
                } else {
                    txtLogin.setText("ورود/ثبت نام");
                }
            } else {

                Role = "others";
                roles = new JSONObject();
                txtLogin.setText("ورود به پنل");
                for (int j = 0; j < usertypes.length(); j++) {
                    try {
                        roles.put("Role" + j, usertypes.getJSONObject(j).getJSONObject("Role").getString("Name"));
                        roles.put("ID" + j, usertypes.getJSONObject(j).getString("ID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        break;
                    }
                }

            }
        } catch (NullPointerException e) {
            txtLogin.setText("ورود/ثبت نام");
        }


        //set image darker
        drawerHeaderImage.setColorFilter(Color.rgb(150, 150, 150), PorterDuff.Mode.MULTIPLY);
        Glide.with(this).load(R.drawable.pattern).into(drawerHeaderImage);


        hamegani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, hameganiActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });

        lytBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "این قسمت بزودی فعال خواهد شد", Toast.LENGTH_LONG).show();
            }
        });


        lytCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CoachListActivity.class);
                startActivity(intent);
            }
        });

        lytGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GymsListActivity.class);
                startActivity(intent);
            }
        });


        lytTeachs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, teachsActivity.class);
                intent.putExtra("teachsCount", newTeachsCount);
                startActivity(intent);
            }
        });

        lytReferee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefereeListActivity.class);
                startActivity(intent);
            }
        });


        lytYogaIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, YogaIntroduceActivity.class);
                intent.putExtra("tab_number", 0);
                startActivity(intent);
            }
        });


        lytMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer_layout.isDrawerOpen(Gravity.RIGHT)) {
                    drawer_layout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawer_layout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        lytRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lytLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    if (Role.equals("Gym") && idUser > 0) {
                        Intent intent = new Intent(MainActivity.this, GymDetailsActivity.class);
                        int id = 0;
                        try {
                            id = usertypes.getJSONObject(0).getInt("ID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intent.putExtra("idgym", id);
                        intent.putExtra("calledFromPanel", true);
                        startActivity(intent);
                    } else if (Role.equals("Coach") && idUser > 0) {
                        Intent intent = new Intent(MainActivity.this, CoachProfileActivity.class);
                        int id = 0;
                        try {
                            id = usertypes.getJSONObject(0).getInt("ID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intent.putExtra("idUser", id);
                        intent.putExtra("calledFromPanel", true);
                        startActivity(intent);
                    } else if (Role.equals("Referee") && idUser > 0) {
                        Intent intent = new Intent(MainActivity.this, RefereeProfileActivity.class);
                        int id = 0;
                        try {
                            id = usertypes.getJSONObject(0).getInt("ID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        intent.putExtra("idReffre", id);
                        intent.putExtra("calledFromPanel", true);
                        startActivity(intent);
                    } else if (Role.equals("User") && idUser > 0) {

                        Intent intent = new Intent(MainActivity.this, UserprofileActivity.class);
                        intent.putExtra("idUser", idUser);
                        intent.putExtra("calledFromPanel", true);
                        startActivity(intent);
                    } else if (Role.equals("others") && idUser > 0) {
                        Intent intent = new Intent(MainActivity.this, SelectRoleActivity.class);
                        intent.putExtra("id", idUser);
                        intent.putExtra("roles", roles.toString());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (NullPointerException e) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });


        lytChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });


        lytAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });

        relativeMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, notificationActivity.class);
                intent.putExtra("userType", userType);
                intent.putExtra("totalNotifsCount", totalNotifsCount);
                intent.putExtra("newNotifsCount", newNotifsCount);
                startActivity(intent);
            }
        });


        lytChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, selectSportActivity.class);
                startActivity(intent);
                finish();
            }
        });


        lytAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, aboutAppActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });

        lytQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, commonQuestionActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });


        lytSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuggestionActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });


        lytRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, rulesActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
            }
        });


        lytShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "ورزش کار، تنها سامانه یکپارچه هیات ورزش های همگانی استان آذربایجان شرقی" + "\n" + "ورزش کار را در بازار ببین" + "\n" + "");
                startActivity(Intent.createChooser(share, "به اشتراک گذاري از طريق..."));

            }
        });

    }

    private void initView() {
        mainAppBar = (AppBarLayout) findViewById(R.id.mainAppBar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        headerview = navigationView.getHeaderView(0);
        drawerHeaderImage = (ImageView) headerview.findViewById(R.id.drawerHeaderImage);
        imgNewTeach = (ImageView) findViewById(R.id.imgNewTeach);
        imgUser = (RoundedImageView) headerview.findViewById(R.id.imgUser);
        txtUserName = (TextView) headerview.findViewById(R.id.txtUserName);
        lytLogin = (LinearLayout) headerview.findViewById(R.id.lytLogin);
        hamegani = (LinearLayout) headerview.findViewById(R.id.hamegani);
        lytQuestion = (LinearLayout) headerview.findViewById(R.id.lytQuestion);
        lytChart = (LinearLayout) headerview.findViewById(R.id.lytChart);
        lytAboutApp = (LinearLayout) headerview.findViewById(R.id.lytAboutApp);
        lytAboutUs = (LinearLayout) headerview.findViewById(R.id.lytAboutUs);
        lytRate = (LinearLayout) headerview.findViewById(R.id.lytRate);
        lytRules = (LinearLayout) headerview.findViewById(R.id.lytRules);
        lytSuggestion = (LinearLayout) headerview.findViewById(R.id.lytSuggestion);
        lytShare = (LinearLayout) headerview.findViewById(R.id.lytShare);
        lytBoard = (LinearLayout) headerview.findViewById(R.id.lytBoard);
        lytChange = (LinearLayout) headerview.findViewById(R.id.lytChange);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        relativeMsg = (CoordinatorLayout) findViewById(R.id.relative_Msg);
        txtNewMessageCount = (TextView) findViewById(R.id.txt_newMessageCount);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        lytMenu = (RelativeLayout) findViewById(R.id.lytMenu);
        lytTeachs = (LinearLayout) findViewById(R.id.lytTeachs);
        lytYogaIntroduce = (LinearLayout) findViewById(R.id.lytYogaIntroduce);
        lytGyms = (LinearLayout) findViewById(R.id.lytGyms);
        lytCoaches = (LinearLayout) findViewById(R.id.lytCoaches);
        txtLogin = headerview.findViewById(R.id.txtLogin);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        lytCommingSoon = (LinearLayout) findViewById(R.id.lytCommingSoon);
        pager = (ViewPagerCustomDuration) findViewById(R.id.pager);
        lytReferee = (LinearLayout) findViewById(R.id.lytReferee);
        navView = (ArcNavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END);
        } else {
            finish();
        }
    }

    private void initSlider(String thirdImage) {


        pager.setAdapter(new SlidingImage_Adapter(MainActivity.this, thirdImage));

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 3) //slider is on the last image
                    currentPage = 0;

                pager.setCurrentItem(currentPage++, true);
            }
        };

        app.isScheduled = false;

        if (app.isScheduled == false) {
            app.swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 2000, 4000);
            app.isScheduled = true;
        }

    }

    private class getSlider extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            SliderImage = webService.getSliderImage(App.isInternetOn(), idField);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (SliderImage != null && !SliderImage.equals("null") && !SliderImage.equals("")) // server responding
                initSlider(SliderImage);
            else
                initSlider("");


            committeeName = new getCommitteeName();
            committeeName.execute();
        }
    }

    private class getCommitteeName extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            SliderImage = webService.getCommitteeName(App.isInternetOn(), idField);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (SliderImage != null && !SliderImage.equals("null") && !SliderImage.equals("")) // server responding
                txtTitle.setText(txtTitle.getText() + SliderImage);

            counts = new getCounts();
            counts.execute();
        }
    }

    private class getCounts extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private MainPageModel model;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            model = webService.getMainPageCounts(App.isInternetOn(), idState, idField);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (model != null) // server responding
            {
                totalNotifsCount = model.notifsCount;
                newNotifsCount = String.valueOf(Integer.valueOf(model.notifsCount) - Integer.valueOf(storedNotifsCount));
                if (!model.notifsCount.equals("") && !model.notifsCount.equals("0") && Integer.valueOf(newNotifsCount) > 0) {

                    txtNewMessageCount.setText(newNotifsCount);
                    txtNewMessageCount.setVisibility(View.VISIBLE);
                } else
                    txtNewMessageCount.setVisibility(View.INVISIBLE);

                newTeachsCount = String.valueOf(Integer.valueOf(model.teachsCount) - Integer.valueOf(storedTeachsCount));
                if (!model.teachsCount.equals("") && Integer.valueOf(newTeachsCount) > 0)
                    imgNewTeach.setVisibility(View.VISIBLE);
                else
                    imgNewTeach.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (slider != null)
            if (slider.getStatus() == AsyncTask.Status.RUNNING)
                slider.cancel(true);

        if (counts != null)
            if (counts.getStatus() == AsyncTask.Status.RUNNING)
                counts.cancel(true);

        if (committeeName != null)
            if (committeeName.getStatus() == AsyncTask.Status.RUNNING)
                committeeName.cancel(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String imageName;
        SharedPreferences prefs = getSharedPreferences("User", 0);
        imageName = prefs.getString("image", null);
        if (imageName != null)
            if (!imageName.equals("") && !imageName.equals("null")) {
                Glide.with(MainActivity.this).load(App.imgAddr + imageName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgUser);
            }
    }
}
