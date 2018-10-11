package com.technologygroup.rayannoor.yoga.NavigationMenu;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ChartModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.ChartAdapter;

import java.util.List;

public class ChartActivity extends AppCompatActivity {


    private ImageView btnBack;
    private LinearLayout lytMain;
    private ShimmerRecyclerView RecyclerCoach;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private getChart chart;
    private List<ChartModel> result;
    private Button btnTryAgain;
    private SharedPreferences prefs;
    private int idField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        initView();

        //get field id
        prefs = getSharedPreferences("User", 0);
        idField = prefs.getInt("idField", 0);

        chart = new getChart();
        chart.execute();

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart = new getChart();
                chart.execute();
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
        ChartAdapter adapter = new ChartAdapter(ChartActivity.this, result);
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
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
    }


    private class getChart extends AsyncTask<Object, Void, Void> {

        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            RecyclerCoach.showShimmerAdapter();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.getChart(App.isInternetOn(), idField, false);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerCoach.clearAnimation();

            if (result != null) // server responding
            {
                if (result.size() == 0) {
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

        if (chart != null)
            if (chart.getStatus() == AsyncTask.Status.RUNNING)
                chart.cancel(true);
    }
}
