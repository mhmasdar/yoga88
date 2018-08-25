package com.technologygroup.rayannoor.yoga.NavigationMenu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.CoachServicesPager;
import com.technologygroup.rayannoor.yoga.adapters.hameganiPager;

public class hameganiActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private ImageView imgBack;
    private TabLayout tabLayout;
    private ViewPager Pager;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamegani);
        initView();
        typeface = Typeface.createFromAsset(getAssets(), "font.ttf");


        tabLayout.addTab(tabLayout.newTab().setText("درباره"));
        tabLayout.addTab(tabLayout.newTab().setText("راههای ارتباطی"));
        tabLayout.addTab(tabLayout.newTab().setText("چارت"));
        tabLayout.addTab(tabLayout.newTab().setText("اهداف"));


        hameganiPager adapter = new hameganiPager(getSupportFragmentManager());
        Pager.setAdapter(adapter);
        Pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Pager.setCurrentItem(tab.getPosition());
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
        imgBack = (ImageView) findViewById(R.id.imgBack);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        Pager = (ViewPager) findViewById(R.id.Pager);
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
