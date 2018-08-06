package com.technologygroup.rayannoor.yoga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.adapters.ChartAdapter;

public class ChartActivity extends AppCompatActivity {


    private ImageView btnBack;
    private LinearLayout lytMain;
    private ShimmerRecyclerView RecyclerCoach;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initView();

        setUpRecyclerView();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void setUpRecyclerView() {
        ChartAdapter adapter = new ChartAdapter(ChartActivity.this);
        RecyclerCoach.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(ChartActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerCoach.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        RecyclerCoach = (ShimmerRecyclerView) findViewById(R.id.RecyclerCoach);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
    }
}
