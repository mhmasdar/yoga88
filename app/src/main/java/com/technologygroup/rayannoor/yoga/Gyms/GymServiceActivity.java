package com.technologygroup.rayannoor.yoga.Gyms;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.FadePageTransformer;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.GymServicesPager;

public class GymServiceActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private TabLayout tabLayout;
    private ViewPager GymServicesPager;
    private Typeface typeface;

    private int selectedTabIndex;
    private boolean calledFromPanel = false;
    private int idGym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_service);
        initView();

        typeface = Typeface.createFromAsset(getAssets(), "font.ttf");

        calledFromPanel = getIntent().getBooleanExtra("calledFromPanel", false);
        selectedTabIndex = getIntent().getIntExtra("SelectedTabIndex", 0);
        idGym = getIntent().getIntExtra("idGym", -1);


        tabLayout.addTab(tabLayout.newTab().setText("افتخارات"));
        tabLayout.addTab(tabLayout.newTab().setText("درباره"));
        tabLayout.addTab(tabLayout.newTab().setText("ویدئو"));
        tabLayout.addTab(tabLayout.newTab().setText("گالری"));
        tabLayout.addTab(tabLayout.newTab().setText("مربیان"));
        tabLayout.addTab(tabLayout.newTab().setText("دوره ها"));
        tabLayout.addTab(tabLayout.newTab().setText("ساعات کاری"));
        tabLayout.addTab(tabLayout.newTab().setText("اعلانات"));


        GymServicesPager adapter = new GymServicesPager(getSupportFragmentManager(), calledFromPanel, idGym);
        GymServicesPager.setAdapter(adapter);
        GymServicesPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        GymServicesPager.setCurrentItem(selectedTabIndex);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                GymServicesPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changeTabsFont();
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        GymServicesPager = (ViewPager) findViewById(R.id.GymServicesPager);
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
