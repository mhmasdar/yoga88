package com.technologygroup.rayannoor.yoga.Coaches;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.CoachTeachesAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class teachesFragment extends Fragment {


    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private LinearLayout lytNotBuy;
    private FloatingActionButton floatAction;

    private boolean calledFromPanel = false;
    private int idCoach;
    CoachTeachesAdapter adapter;
    private SharedPreferences prefs;
    private List<TeachesModel> list;
    WebServiceList webService;

    public teachesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_teaches, container, false);

        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idCoach = getArguments().getInt("idCoach", -1);

        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        lytNotBuy = (LinearLayout) view.findViewById(R.id.lytNotBuy);
        floatAction = (FloatingActionButton) view.findViewById(R.id.floatAction);

        if (!calledFromPanel) {
            floatAction.setVisibility(View.GONE);
        }

        if (idCoach > 0) {

            webService = new WebServiceList();
            webService.execute();
        } else {
            Toast.makeText(getContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
        }

        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), addTeachActivity.class);
                intent.putExtra("idRow", idCoach);
                startActivity(intent);
            }
        });

        Button btnTryAgain = view.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCoach > 0) {

                    webService = new WebServiceList();
                    webService.execute();
                } else {
                    Toast.makeText(getContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }
        });

        return view;

    }

    private void setUpRecyclerView(List<TeachesModel> list) {
        adapter = new CoachTeachesAdapter(getActivity(), list, idCoach, calledFromPanel);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
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

            list = webService.getTeachesOfown(App.isInternetOn(), 1,"ersali");

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
