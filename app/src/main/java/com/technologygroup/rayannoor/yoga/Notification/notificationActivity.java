package com.technologygroup.rayannoor.yoga.Notification;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.NotifPager;

public class notificationActivity extends AppCompatActivity {


    private ImageView btnBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Typeface typeface;
    private String userType;

    private SharedPreferences CountPrefs;
    private SharedPreferences.Editor editor;
    private String totalNotifsCount, newNotifsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
        typeface = Typeface.createFromAsset(getAssets(), "font.ttf");

        CountPrefs = getSharedPreferences("Counts", 0);
        editor = CountPrefs.edit();

        totalNotifsCount = getIntent().getStringExtra("totalNotifsCount");
        newNotifsCount = getIntent().getStringExtra("newNotifsCount");
        if (!totalNotifsCount.equals("0"))
        {
            editor.putString("notifsCount", totalNotifsCount);
            editor.apply();
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        userType = getIntent().getStringExtra("userType");


        tabLayout.addTab(tabLayout.newTab().setText("اخبار"));

        if (Integer.valueOf(newNotifsCount) > 0)
            tabLayout.addTab(tabLayout.newTab().setText("اعلانات (" + newNotifsCount + ")"));
        else
            tabLayout.addTab(tabLayout.newTab().setText("اعلانات "));

        if (!userType.equals("no"))
            tabLayout.addTab(tabLayout.newTab().setText("کاریابی"));

        NotifPager adapter = new NotifPager(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
//        viewPager.setPageTransformer(false, new FadePageTransformer());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        changeTabsFont();
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }


    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {

                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }

    }
}
