package com.technologygroup.rayannoor.yoga.Gyms;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.GymCoachSearchAdapter;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

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
    private int stateNumber;
    private int cityNumber;
    private int fieldNumber;
    private SharedPreferences prefs;
    int idGym;
    private List<CoachModel> coachModel;
    private List<CoachModel> filtercoachModel;
    FetchDataCoachesList fetchDataCoachesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coach);
        initView();
        idGym = getIntent().getIntExtra("idGym", -1);
        prefs = App.context.getSharedPreferences("User", 0);
        stateNumber = prefs.getInt("idState", 0);
        cityNumber = prefs.getInt("idCity", 0);
        fieldNumber = prefs.getInt("idField", 0);
        coachModel = new ArrayList<>();
        filtercoachModel = new ArrayList<>();
        FetchDataCoachesList fetchDataCoachesList=new FetchDataCoachesList();
        fetchDataCoachesList.execute();

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


        lytBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        GymCoachSearchAdapter adapter = new GymCoachSearchAdapter(AddCoachActivity.this,coachModel,idGym);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddCoachActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public void filter(View view) {
        String filterword;
        filterword=edtSearch.getText().toString();
        for(int i=0;i<coachModel.size();i++)
        {
            if((coachModel.get(i).fName+" "+coachModel.get(i).lName).contains(filterword))
            {
                filtercoachModel.add(coachModel.get(i));
            }
        }
        GymCoachSearchAdapter adapter = new GymCoachSearchAdapter(AddCoachActivity.this,filtercoachModel,idGym);
        Recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(AddCoachActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    public class FetchDataCoachesList extends AsyncTask<Object, Void, Void> {
        private WebService webService;

        @Override
        protected void onPreExecute() {
            webService = new WebService();
            super.onPreExecute();
            Recycler.showShimmerAdapter();  //start recycler's animation
        }

        @Override
        protected Void doInBackground(Object... params) {
            coachModel = webService.getCoachesByField(App.isInternetOn(), fieldNumber,cityNumber);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Recycler.clearAnimation(); // stop recycler's animation

            if (coachModel != null) {

                if (coachModel.size() > 0) {

                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    Recycler.setLayoutManager(mLinearLayoutManagerVertical);

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
