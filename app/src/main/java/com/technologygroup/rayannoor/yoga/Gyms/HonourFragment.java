package com.technologygroup.rayannoor.yoga.Gyms;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.GymHonourAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class HonourFragment extends Fragment {


    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floactAction;
    private LinearLayout lytMain;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private Dialog dialog;
    private ImageView imgClose;
    private EditText edtTitle;
    private EditText edtDate;
    private TextView txtNoImage;
    private ImageView imgHonour;
    private ImageView imgSelectPicture;
    private CircularProgressButton btnOk;

    private boolean calledFromPanel = false;
    private int idGym;
    List<CoachHonorModel> list;
    GymHonourAdapter adapter;
    WebServiceList webServiceCoachInfo;

    public HonourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_honour, container, false);

        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idGym = getArguments().getInt("idGym", -1);



        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);

//        setUpRecyclerView();

        if (!calledFromPanel) {
            floactAction.setVisibility(View.GONE);
        }

        if (idGym > 0) {

            webServiceCoachInfo = new WebServiceList();
            webServiceCoachInfo.execute();
        } else {
            Toast.makeText(getContext(), "باشگاه مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
        }

        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Button btnTryAgain = view.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idGym > 0) {

                    webServiceCoachInfo = new WebServiceList();
                    webServiceCoachInfo.execute();
                } else {
                    Toast.makeText(getContext(), "باشگاه مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
            }
        });

        return view;
    }

    private void setUpRecyclerView(List<CoachHonorModel> list) {
        adapter = new GymHonourAdapter(getActivity(), list, idGym, calledFromPanel);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }


    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_honour);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
        edtDate = (EditText) dialog.findViewById(R.id.edtDate);
        txtNoImage = (TextView) dialog.findViewById(R.id.txtNoImage);
        imgHonour = (ImageView) dialog.findViewById(R.id.imgHonour);
        imgSelectPicture = (ImageView) dialog.findViewById(R.id.imgSelectPicture);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
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

            list = webService.getGymHonor(App.isInternetOn(), idGym);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Recycler.hideShimmerAdapter();

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

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);
    }
}
