package com.technologygroup.rayannoor.yoga.Coaches;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachCourseModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.CoachCourseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class courseFragment extends Fragment {


    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private Button btnTryAgain;
    private LinearLayout lytEmpty;
    private FloatingActionButton floactAction;
    private ImageView imgClose;
    private EditText edtCourse;
    private CircularProgressButton btnOk;
    List<CoachCourseModel> list;
    Dialog dialog;
    private int idCoach;
    boolean calledFromPanel;
    WebServiceList webServiceList;
    WebServiceADD callBack;


    public courseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        idCoach = getArguments().getInt("idCoach", -1);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        if(!calledFromPanel)
        {
            floactAction.setVisibility(View.GONE);
        }
        webServiceList=new WebServiceList();
        webServiceList.execute();
        return view;
    }

    private void setUpRecyclerView() {
        CoachCourseAdapter adapter = new CoachCourseAdapter(getActivity(),list,calledFromPanel);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }
    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_coach_course);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtCourse = (EditText) dialog.findViewById(R.id.edtCourse);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack = new WebServiceADD(edtCourse.getText().toString());
                 callBack.execute();
            }
        });
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

    private class WebServiceADD extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        String result;
        int pos;
        String title;

        public WebServiceADD(String t) {
            title=t;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnOk.startAnimation();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.AddCoachCourse(App.isInternetOn(),title,idCoach);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {
                if (result.equals("OK")) {


                    // بعد از اتمام عملیات کدهای زیر اجرا شوند
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

                    //Toast.makeText(context, "با موفقیت به روز رسانی شد", Toast.LENGTH_LONG).show();
                } else {
                    btnOk.revertAnimation();
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                }
            } else {
                btnOk.revertAnimation();
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
            WebServiceList webServiceList=new WebServiceList();
            webServiceList.execute();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (webServiceList != null)
            if (webServiceList.getStatus() == AsyncTask.Status.RUNNING)
                webServiceList.cancel(true);

        if (callBack != null)
            if (callBack.getStatus() == AsyncTask.Status.RUNNING)
                callBack.cancel(true);
    }

}
