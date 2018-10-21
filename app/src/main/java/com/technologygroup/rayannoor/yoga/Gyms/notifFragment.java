package com.technologygroup.rayannoor.yoga.Gyms;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.GymNotifAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class notifFragment extends Fragment {


    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floactAction;

    private LinearLayout lytMain;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    boolean calledFromPanel;
    private List<ZanguleModel> list;
    private int idGym;
    private Dialog dialog;
    private EditText edtTitle;
    private EditText edtBody;
    CircularProgressButton btnOk;
    ImageView imgClose;

    public notifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notif, container, false);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);
        idGym = getArguments().getInt("idGym", -1);
        list=new ArrayList<>();
        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        if (!calledFromPanel) {
            floactAction.setVisibility(View.GONE);
        }
        if(idGym>0) {
            WebServiceList webServiceCoachInfo = new WebServiceList();
            webServiceCoachInfo.execute();
        }
        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        return view;
    }


    private void setUpRecyclerView() {
        GymNotifAdapter adapter = new GymNotifAdapter(getActivity(),list,idGym);
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

            list = webService.getZanguleGym(App.isInternetOn(),idGym);
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
                    setUpRecyclerView();

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
    private class WebServiceAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String title;
        String body;
        int result;
        WebServiceAdd(String title,String body)
        {
            this.body=body;
            this.title=title;
            result=0;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnOk.startAnimation();
            webService = new WebService();
            list = new ArrayList<>();
            Recycler.showShimmerAdapter();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.AddElanat(App.isInternetOn(),idGym,title,body);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Recycler.clearAnimation();

            if (result > 0) {
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_ok);
                btnOk.doneLoadingAnimation(R.color.green, icon); // finish loading

                // بستن دیالوگ حتما با تاخیر انجام شود
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 1000);
                WebServiceList webServiceList=new WebServiceList();
                webServiceList.execute();
            }

        }

    }
    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_notif);
        edtBody=dialog.findViewById(R.id.edtBody);
        edtTitle=dialog.findViewById(R.id.edtTitle);
        imgClose=dialog.findViewById(R.id.imgClose);
        btnOk=dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceAdd webServiceADD=new WebServiceAdd(edtTitle.getText().toString(),edtBody.getText().toString());
                webServiceADD.execute();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
