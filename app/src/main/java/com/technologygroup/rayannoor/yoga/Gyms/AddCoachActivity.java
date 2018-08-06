package com.technologygroup.rayannoor.yoga.Gyms;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.adapters.GymCoachSearchAdapter;

import net.cachapa.expandablelayout.ExpandableLayout;

public class AddCoachActivity extends AppCompatActivity {

    private ExpandableLayout expandableLayout;
    private LinearLayout lytSearch;
    private EditText edtSearch;
    private RelativeLayout lytBack;
    private TextView lytCoachSelect;
    private TextView txtCoachName;
    private TextView txtCoachAddress;
    private RoundedImageView imgCoach;
    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coach);
        initView();
        setUpRecyclerView();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                expandableLayout.expand();

                Animation fade1 = AnimationUtils.loadAnimation(AddCoachActivity.this, R.anim.search_btn_back);

                lytBack.setVisibility(View.VISIBLE);
                lytBack.startAnimation(fade1);

            }
        }, 200);
    }

    private void initView() {
        expandableLayout = (ExpandableLayout) findViewById(R.id.expandable_layout);
        lytSearch = (LinearLayout) findViewById(R.id.lytSearch);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        lytBack = (RelativeLayout) findViewById(R.id.lytBack);
        lytCoachSelect = (TextView) findViewById(R.id.lytCoachSelect);
        txtCoachName = (TextView) findViewById(R.id.txtCoachName);
        txtCoachAddress = (TextView) findViewById(R.id.txtCoachAddress);
        imgCoach = (RoundedImageView) findViewById(R.id.imgCoach);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        Recycler = (ShimmerRecyclerView) findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
    }

    private void setUpRecyclerView() {
        GymCoachSearchAdapter adapter = new GymCoachSearchAdapter(AddCoachActivity.this);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddCoachActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }
}
