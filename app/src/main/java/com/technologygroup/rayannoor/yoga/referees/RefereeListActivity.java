package com.technologygroup.rayannoor.yoga.referees;

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
import com.technologygroup.rayannoor.yoga.Internet;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.RefereeListAdapter;

import java.util.List;

public class RefereeListActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private Button btnTryAgain;
    private LinearLayout lytEmpty;
    WebServiceCall call;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referee_list);
        initView();
        call = new WebServiceCall();
        call.execute();

    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        Recycler = (ShimmerRecyclerView) findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
    }

    private void setUpRecyclerView(List<CoachModel> c) {
        RefereeListAdapter adapter = new RefereeListAdapter(RefereeListActivity.this,c);
        Recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(RefereeListActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }
    private class WebServiceCall extends AsyncTask<Object, Void, Void> {
        WebService webService;
        Internet internet;
        SharedPreferences sharedPreferences;
        String cid,fid,res;
        List<CoachModel> reffres;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            internet=new Internet();
            webService=new WebService();
            sharedPreferences= getSharedPreferences("User", 0);
            cid=sharedPreferences.getString("cid","0");
            fid=sharedPreferences.getString("fid","0");
        }

        @Override
        protected Void doInBackground(Object... params) {

            reffres=webService.getReffres(App.isInternetOn(),fid,cid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Recycler.hideShimmerAdapter();

            if (reffres != null) {

                if (reffres.size() > 0) {
                    Recycler = findViewById(R.id.Recycler);
                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    Recycler.setLayoutManager(mLinearLayoutManagerVertical);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);
                    setUpRecyclerView(reffres);
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
    public void finishme(View v)
    {
        finish();
    }
}
