package com.technologygroup.rayannoor.yoga.Coaches;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.CoachListAdapter;

import java.util.List;

public class CoachListActivity extends AppCompatActivity {

    private TextView txtTitle;
    private RelativeLayout btnBack;
    LinearLayout lytMain, lytDisconnect, lytEmpty;
    private ShimmerRecyclerView RecyclerCoach;
    private SharedPreferences prefs;
    //    private int idCoach;
    private List<CoachModel> coachModel;
    private int stateNumber;
    private int cityNumber;
    private int fieldNumber;
    fetchDataCoachesList fetchDataCoachesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_list);
        initView();

        prefs = getSharedPreferences("User", 0);
        stateNumber = prefs.getInt("idState", 0);
        cityNumber = prefs.getInt("idCity", 0);
        fieldNumber = prefs.getInt("idField", 0);

        fetchDataCoachesList = new fetchDataCoachesList();
        fetchDataCoachesList.execute();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button btnTryAgain = findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    fetchDataCoachesList = new fetchDataCoachesList();
                    fetchDataCoachesList.execute();

            }
        });
    }

    private void setUpRecyclerView() {
        CoachListAdapter adapter = new CoachListAdapter(CoachListActivity.this, coachModel);
        RecyclerCoach.setAdapter(adapter);
    }

    private void initView() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        RecyclerCoach = findViewById(R.id.RecyclerCoach);
        lytEmpty = findViewById(R.id.lytEmpty);
        lytMain = findViewById(R.id.lytMain);
        lytDisconnect = findViewById(R.id.lytDisconnect);
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class fetchDataCoachesList extends AsyncTask<Object, Void, Void> {
        private WebService webService;

        @Override
        protected void onPreExecute() {
            webService = new WebService();
            super.onPreExecute();
            RecyclerCoach.showShimmerAdapter();  //start recycler's animation
        }

        @Override
        protected Void doInBackground(Object... params) {
         coachModel = webService.getCoachesByField(App.isInternetOn(), fieldNumber,cityNumber);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerCoach.clearAnimation(); // stop recycler's animation

            if (coachModel != null) {

                if (coachModel.size() > 0) {

                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    RecyclerCoach.setLayoutManager(mLinearLayoutManagerVertical);

                    setUpRecyclerView();

                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);

//                if (coachModel.Img != null)
//                    if (!coachModel.Img.equals("") && !coachModel.Img.equals("null"))
//                        //Glide.with(CoachProfileActivity.this).load(App.imgAddr + coachModel.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCoach);
////                txtCoachName.setText(coachModel.fName + " " + coachModel.lName);
////                ClassLevels classLevels = new ClassLevels();
////                txtCoachLevel.setText(classLevels.getCoachLevelName(coachModel.idCurrentPlan));
////                txtCoachRate.setText(coachModel.Rate + "");
////                txtLikeCount.setText(coachModel.like + "");
////                rating.setRating((float) coachModel.Rate);
//
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

        if (fetchDataCoachesList != null)
            if (fetchDataCoachesList.getStatus() == AsyncTask.Status.RUNNING)
                fetchDataCoachesList.cancel(true);
    }

}