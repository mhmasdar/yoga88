package com.technologygroup.rayannoor.yoga.NavigationMenu;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ChartModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.ChartAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class hameganiFragment3 extends Fragment {


    private LinearLayout lytMain;
    private ShimmerRecyclerView RecyclerCoach;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private getChart chart;
    private List<ChartModel> result;
    private Button btnTryAgain;

    public hameganiFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hamegani_fragment3, container, false);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        RecyclerCoach = (ShimmerRecyclerView) view.findViewById(R.id.RecyclerCoach);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);


        chart = new getChart();
        chart.execute();

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chart = new getChart();
                chart.execute();
            }
        });



        return view;
    }


    private void setUpRecyclerView() {
        ChartAdapter adapter = new ChartAdapter(getActivity(), result);
        RecyclerCoach.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getActivity());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerCoach.setLayoutManager(mLinearLayoutManagerVertical);
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

            result = webService.getChart(App.isInternetOn(), 0, true);

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

        if (chart != null)
            if (chart.getStatus() == AsyncTask.Status.RUNNING)
                chart.cancel(true);
    }

}
