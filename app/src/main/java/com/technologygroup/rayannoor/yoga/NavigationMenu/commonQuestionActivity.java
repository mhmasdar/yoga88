package com.technologygroup.rayannoor.yoga.NavigationMenu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.FAQmodel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.QuestionAdapter;

import java.util.List;

public class commonQuestionActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private LinearLayout lytMain;
    private ShimmerRecyclerView RecyclerCoach;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private Button btnTryAgain;
    getFAQ getFAQ;
    private List<FAQmodel> result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_question);
        initView();

        getFAQ = new getFAQ();
        getFAQ.execute();

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFAQ = new getFAQ();
                getFAQ.execute();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpRecyclerView() {
        QuestionAdapter adapter = new QuestionAdapter(commonQuestionActivity.this, result);
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
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
    }

    private class getFAQ extends AsyncTask<Object, Void, Void> {

        private WebService webService;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            RecyclerCoach.showShimmerAdapter();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.getFAQ(App.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerCoach.clearAnimation();

            if (result != null) // server responding
            {
                if (result.equals("")) {
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                    lytMain.setVisibility(View.GONE);
                } else {
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);

                    setUpRecyclerView();
                }
            } else {
                lytDisconnect.setVisibility(View.VISIBLE);
                lytEmpty.setVisibility(View.GONE);
                lytMain.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (getFAQ != null)
            if (getFAQ.getStatus() == AsyncTask.Status.RUNNING)
                getFAQ.cancel(true);
    }
}
