package com.technologygroup.rayannoor.yoga.referees;


import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachResumeModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.RefereeResumeAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class refResumeFragment extends Fragment {


    private LinearLayout lytMain, lytDisconnect, lytEmpty;
    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floatAction;
    private Dialog dialog;
    private TextView txtWindowTitle;
    private ImageView imgClose;
    private EditText edtTitle;
    private EditText edtStartDate;
    private EditText edtEndDate;
    private CheckBox checkStudents;
    private CircularProgressButton btnOk;
    private boolean calledFromPanel;
    int idCoach;
    refResumeFragment.WebServiceList webServiceCoachInfo;
    List<CoachResumeModel> list;
    public refResumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ref_resume, container, false);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idCoach = getArguments().getInt("idCoach", -1);
        lytEmpty = view.findViewById(R.id.lytEmpty);
        lytMain = view.findViewById(R.id.lytMain);
        lytDisconnect = view.findViewById(R.id.lytDisconnect);
        Recycler = view.findViewById(R.id.Recycler);
        floatAction = view.findViewById(R.id.floatAction);

        if (!calledFromPanel) {
            floatAction.setVisibility(View.GONE);
        }
        if (idCoach > 0) {

            webServiceCoachInfo = new refResumeFragment.WebServiceList();
            webServiceCoachInfo.execute();
            Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
        }

//        setUpRecyclerView();

        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void setUpRecyclerView() {
        RefereeResumeAdapter adapter = new RefereeResumeAdapter(getActivity(), list, idCoach, calledFromPanel);
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

            list = webService.getCoachResume(App.isInternetOn(), idCoach);

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

    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_resume);

        txtWindowTitle = (TextView) dialog.findViewById(R.id.txtWindowTitle);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
        edtStartDate = (EditText) dialog.findViewById(R.id.edtStartDate);
        edtEndDate = (EditText) dialog.findViewById(R.id.edtEndDate);
        checkStudents = (CheckBox) dialog.findViewById(R.id.checkContinue);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

}
