package com.technologygroup.rayannoor.yoga.Coaches;


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
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Models.CoachGymsModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.CoachGymsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class gymsFragment extends Fragment {


    private ShimmerRecyclerView Recycler;
    LinearLayout lytMain, lytDisconnect, lytEmpty;
    private SharedPreferences prefs;
    private int idCoach;
    List<CoachGymsModel> list;

    private boolean calledFromPanel = false;
    WebServiceList webServiceCoachInfo;

    public gymsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gyms, container, false);

        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idCoach = getArguments().getInt("idCoach", -1);


        Recycler = view.findViewById(R.id.Recycler);
        lytEmpty = view.findViewById(R.id.lytEmpty);
        lytMain = view.findViewById(R.id.lytMain);
        lytDisconnect = view.findViewById(R.id.lytDisconnect);


        if (idCoach > 0) {

            webServiceCoachInfo = new WebServiceList();
            webServiceCoachInfo.execute();

        } else {
            Toast.makeText(getContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
        }
        Button btnTryAgain = view.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCoach > 0) {

                    webServiceCoachInfo = new WebServiceList();
                    webServiceCoachInfo.execute();
                } else {
                    Toast.makeText(getContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }
        });
//        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView(List<CoachGymsModel> list){
        CoachGymsAdapter adapter = new CoachGymsAdapter(getActivity(), list, calledFromPanel);
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

//            list = webService.getCoachGyms(App.isInternetOn(), idCoach);

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

  //                  setUpRecyclerView(list);

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

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);
    }

}
