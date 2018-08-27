package com.technologygroup.rayannoor.yoga.YogaIntroduce;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Internet;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.IntroduceAdapter;

import java.util.List;

public class YogaIntroduceActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private LinearLayout lytMain;
    private List<TeachesModel> list;
    private WebServiceCall call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_sport);
        initView();
        call = new WebServiceCall();
        call.execute();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        Recycler = (ShimmerRecyclerView) findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
      //  lytMain = (LinearLayout) findViewById(R.id.lytMain);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
    }

    private void setUpRecyclerView(){

        IntroduceAdapter adapter = new IntroduceAdapter(this,list);
        Recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this);
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

            list=webService.getTeaches(App.isInternetOn(),1,1,"moarrefi");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Recycler.hideShimmerAdapter();

            if (list != null) {

                if (list.size() > 0) {
                    Recycler = findViewById(R.id.Recycler);
                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    Recycler.setLayoutManager(mLinearLayoutManagerVertical);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    Recycler.setVisibility(View.VISIBLE);
                    setUpRecyclerView();
                } else {
                    lytDisconnect.setVisibility(View.GONE);
                    Recycler.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }
            } else {

                Recycler.setVisibility(View.GONE);
                lytEmpty.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);

            }

        }

    }
}
