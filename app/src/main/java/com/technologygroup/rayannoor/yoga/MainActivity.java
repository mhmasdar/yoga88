package com.technologygroup.rayannoor.yoga;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.technologygroup.rayannoor.yoga.Coaches.CoachListActivity;
import com.technologygroup.rayannoor.yoga.Coaches.CoachProfileActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymDetailsActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymsListActivity;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;
import com.technologygroup.rayannoor.yoga.Teaches.teachsActivity;
import com.technologygroup.rayannoor.yoga.YogaIntroduce.YogaIntroduceActivity;
import com.technologygroup.rayannoor.yoga.adapters.SlidingImage_Adapter;
import com.technologygroup.rayannoor.yoga.referees.RefereeListActivity;

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
    private SharedPreferences prefs;
    public static Dialog dialog;
    public static Spinner StateSpinner;
    public static Spinner CitySpinner;
    private static int currentPage = 0;

    private int userType, idUser;
    private DrawerLayout drawerLayout;
    private LinearLayout lytCommingSoon;
    private LinearLayout hamegani;
    private ViewPagerCustomDuration pager;
    private LinearLayout lytReferee;
    private ArcNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        prefs = getSharedPreferences("MyPrefs", 0);

        userType = prefs.getInt("userType", -1);
        idUser = prefs.getInt("idUser", -1);

        if (idUser > 0) {
            txtUserName.setText(prefs.getString("Name", "") + " " + prefs.getString("lName", ""));
        }


        if (userType == 1) {
            txtLogin.setText("پنل باشگاه");
        } else if (userType == 2) {
            txtLogin.setText("پنل مربی");
        } else if (userType == 3) {
            txtLogin.setText("حساب کاربری");
        } else {
            txtLogin.setText("ورود/ثبت نام");
        }

        //set image darker
        drawerHeaderImage.setColorFilter(Color.rgb(150, 150, 150), PorterDuff.Mode.MULTIPLY);
        Glide.with(this).load(R.drawable.drawer).into(drawerHeaderImage);


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
                Toast.makeText(getApplicationContext() , "این قسمت بزودی فعال خواهد شد" ,Toast.LENGTH_LONG).show();
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
                if (userType == -1) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                if (userType == 1 && idUser > 0) {
                    Intent intent = new Intent(MainActivity.this, GymDetailsActivity.class);
                    startActivity(intent);
                }
                if (userType == 2 && idUser > 0) {
                    Intent intent = new Intent(MainActivity.this, CoachProfileActivity.class);
                    startActivity(intent);
                }
                if (userType == 3 && idUser > 0) {
                    Intent intent = new Intent(MainActivity.this, UserprofileActivity.class);
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
                startActivity(intent);
            }
        });


        lytChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, selectSportActivity.class);
                startActivity(intent);
                drawer_layout.closeDrawer(GravityCompat.END);
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
                share.putExtra(Intent.EXTRA_TEXT, "ورزش کار، تنها سامانه یکپارچه هیات ورزش های همگانی استان آذربایجان شرقی" + "\n" + "ورزش کار را در بازار ببین" + "\n" +"");
                startActivity(Intent.createChooser(share, "به اشتراک گذاري از طريق..."));

            }
        });


        initSlider();
    }

    private void initView() {
        mainAppBar = (AppBarLayout) findViewById(R.id.mainAppBar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        headerview = navigationView.getHeaderView(0);
        drawerHeaderImage = (ImageView) headerview.findViewById(R.id.drawerHeaderImage);
        imgNewTeach = (ImageView) headerview.findViewById(R.id.imgNewTeach);
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

    private void initSlider() {


        pager.setAdapter(new SlidingImage_Adapter(MainActivity.this));

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
}
