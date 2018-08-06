package com.technologygroup.rayannoor.yoga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.adapters.ChartAdapter;
import com.technologygroup.rayannoor.yoga.adapters.QuestionAdapter;

public class commonQuestionActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private LinearLayout lytMain;
    private ShimmerRecyclerView RecyclerCoach;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_question);
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
        QuestionAdapter adapter = new QuestionAdapter(commonQuestionActivity.this);
        RecyclerCoach.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(commonQuestionActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerCoach.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        RecyclerCoach = (ShimmerRecyclerView) findViewById(R.id.RecyclerCoach);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
    }
}
