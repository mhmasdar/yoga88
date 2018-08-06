package com.technologygroup.rayannoor.yoga.referees;


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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Internet;
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.RefereeCertificateAdapter;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class refCertificateFragment extends Fragment {

    private LinearLayout lytMain, lytDisconnect, lytEmpty;
    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floatAction;
    private Dialog dialog;
    private TextView txtWindowTitle;
    private ImageView imgClose;
    private EditText edtTitle;
    private EditText edtDate;
    private TextView txtNoImage;
    private ImageView imgCertificate;
    private ImageView imgSelectPicture;
    private CircularProgressButton btnOk;
    private boolean calledFromPanel = false;
    List<CoachHonorModel> reffres;
    int idCoach;

    public refCertificateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ref_certificate, container, false);
        lytEmpty = view.findViewById(R.id.lytEmpty);
        lytMain = view.findViewById(R.id.lytMain);
        lytDisconnect = view.findViewById(R.id.lytDisconnect);
        Recycler = view.findViewById(R.id.Recycler);
        floatAction = view.findViewById(R.id.floatAction);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idCoach = getArguments().getInt("idCoach", -1);
        setUpRecyclerView();

        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void setUpRecyclerView() {
        RefereeCertificateAdapter adapter = new RefereeCertificateAdapter(getActivity(),reffres, idCoach, calledFromPanel);
        Recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }


    private class WebServiceCall extends AsyncTask<Object, Void, Void> {
        WebService webService;
        Internet internet;
        SharedPreferences sharedPreferences;
        String cid,fid,res;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            internet=new Internet();
            webService=new WebService();
            sharedPreferences= getActivity().getSharedPreferences("User", 0);
            cid=sharedPreferences.getString("cid","0");
            fid=sharedPreferences.getString("fid","0");
        }

        @Override
        protected Void doInBackground(Object... params) {

            reffres=webService.getCoachHonor(App.isInternetOn(),idCoach);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Recycler.hideShimmerAdapter();

            if (reffres != null) {

                if (reffres.size() > 0) {

                    LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    Recycler.setLayoutManager(mLinearLayoutManagerVertical);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);
                    setUpRecyclerView();/////
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


    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_certificate);

        txtWindowTitle = (TextView) dialog.findViewById(R.id.txtWindowTitle);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
        edtDate = (EditText) dialog.findViewById(R.id.edtDate);
        txtNoImage = (TextView) dialog.findViewById(R.id.txtNoImage);
        imgCertificate = (ImageView) dialog.findViewById(R.id.imgCertificate);
        imgSelectPicture = (ImageView) dialog.findViewById(R.id.imgSelectPicture);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


    }

}
