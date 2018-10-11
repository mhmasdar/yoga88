package com.technologygroup.rayannoor.yoga.Teaches;

import android.content.SharedPreferences;
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
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.TeachCoachesAdapter;

import java.util.ArrayList;
import java.util.List;

public class CoachTeachsActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private List<TeachesModel> list;
    private int stateNumber;
    private int cityNumber;
    private int fieldNumber;
    private SharedPreferences prefs;

    WebServiceList webService;
    Button btnTryAgain;
    private String teachsCount;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_teachs);
        initView();
        prefs = getSharedPreferences("Counts", 0);
        editor = prefs.edit();

        teachsCount = getIntent().getStringExtra("teachsCount");
        editor.putString("teachsCount", teachsCount);
        editor.apply();

        stateNumber = prefs.getInt("idState", 0);
        cityNumber = prefs.getInt("idCity", 0);
        fieldNumber = prefs.getInt("idField", 0);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webService = new WebServiceList();
        webService.execute();
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webService = new WebServiceList();
                webService.execute();

            }
        });

    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        lytMain = findViewById(R.id.lytMain);
        Recycler = findViewById(R.id.Recycler);
        lytDisconnect = findViewById(R.id.lytDisconnect);
        lytEmpty = findViewById(R.id.lytEmpty);
        btnTryAgain = findViewById(R.id.btnTryAgain);

    }

    private void setUpRecyclerView(List<TeachesModel> list) {
        TeachCoachesAdapter adapter = new TeachCoachesAdapter(CoachTeachsActivity.this, list);
        Recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private class WebServiceList extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            list = new ArrayList<>();
            Recycler.showShimmerAdapter();
        }

        @Override
        protected Void doInBackground(Object... params) {

            list = webService.getTeaches(App.isInternetOn(), fieldNumber, stateNumber,"ersali");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Recycler.clearAnimation();

            if (list != null) {

                if (list.size() > 0) {

                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);

                    setUpRecyclerView(list);

                } else {

                    lytDisconnect.setVisibility(View.GONE);
                    lytMain.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);


                }

            } else {

                lytMain.setVisibility(View.GONE);
                lytEmpty.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);

            }

        }

    }


    @Override
    public void onStop() {
        super.onStop();

        if (webService != null)
            if (webService.getStatus() == AsyncTask.Status.RUNNING)
                webService.cancel(true);
    }
}
