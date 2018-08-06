package com.technologygroup.rayannoor.yoga.Notification;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.FadePageTransformer;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.GymServicesPager;
import com.technologygroup.rayannoor.yoga.adapters.NotifPager;

public class notificationActivity extends AppCompatActivity {


    private ImageView btnBack;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
        typeface = Typeface.createFromAsset(getAssets(), "font.ttf");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("اخبار"));
        tabLayout.addTab(tabLayout.newTab().setText("اعلانات"));
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


//    private void setUpRecyclerView() {
//        NotifsAdapter adapter = new NotifsAdapter(notificationActivity.this);
//        RecyclerCoach.setAdapter(adapter);
//
//        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(notificationActivity.this);
//        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
//        RecyclerCoach.setLayoutManager(mLinearLayoutManagerVertical);
//    }

}
