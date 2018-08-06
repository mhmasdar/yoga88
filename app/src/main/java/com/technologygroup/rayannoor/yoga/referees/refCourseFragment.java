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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachCourseModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.RefereeCourseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class refCourseFragment extends Fragment {


    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private Button btnTryAgain;
    private LinearLayout lytEmpty;
    private FloatingActionButton floactAction;

    private ImageView imgClose;
    private EditText edtCourse;
    private CircularProgressButton btnOk;
    private int idCoach;
    boolean calledFromPanel;
    List<CoachCourseModel> list;
    public refCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ref_course, container, false);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

        idCoach = getArguments().getInt("idCoach", -1);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);

        WebServiceList webServiceList=new WebServiceList();
        webServiceList.execute();


        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;
    }

    private void setUpRecyclerView() {
        RefereeCourseAdapter adapter = new RefereeCourseAdapter(getActivity());
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_coach_course);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtCourse = (EditText) dialog.findViewById(R.id.edtCourse);
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

            list = webService.getCoachCourses(App.isInternetOn(), idCoach);
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

}
