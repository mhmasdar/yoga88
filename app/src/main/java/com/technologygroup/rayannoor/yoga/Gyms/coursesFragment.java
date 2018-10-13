package com.technologygroup.rayannoor.yoga.Gyms;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CourseModel;
import com.technologygroup.rayannoor.yoga.Models.GymCoachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.GymCourseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class coursesFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener {


    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floactAction;
    private Dialog dialog;
    private LinearLayout lytMain;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;

    private boolean calledFromPanel = false;
    private int idGym;
    private int pos;
    private Spinner selectCoach;
    EditText t;
    List<CourseModel> list;
    List<GymCoachesModel> listcoaches;
    ArrayList<String> coaches;
    List<Integer> coachesId;
    GymCourseAdapter adapter;
    WebServiceList webServiceCoachInfo;

    EditText edtDateStart;
    EditText edtDateEnd;
    EditText edtTitle;
    EditText edtTime;
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";
    String date;
    CircularProgressButton btnOk;
    public coursesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_courses, container, false);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idGym = getArguments().getInt("idGym", -1);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

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

    private void setUpRecyclerView(List<CourseModel> list) {
        adapter = new GymCourseAdapter(getActivity(), list, idGym, calledFromPanel);
        Recycler.setAdapter(adapter);
        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }


    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_course);
        edtDateStart=dialog.findViewById(R.id.edtStartDate);
        edtDateEnd=dialog.findViewById(R.id.edtEndDate);
        edtTitle=dialog.findViewById(R.id.edtTitle);
        edtTime=dialog.findViewById(R.id.edtTime);
        btnOk=dialog.findViewById(R.id.btnOk);
        ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(coursesFragment.this, android.R.layout.simple_spinner_item,coaches);
        edtDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=edtDateEnd;
                GymServiceActivity activity = (GymServiceActivity) getContext();
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(coursesFragment.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
                dpd.show(activity.getFragmentManager(), DATEPICKER);
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");

                    }
                });

            }

        });
        edtDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t=edtDateEnd;
                GymServiceActivity activity = (GymServiceActivity) getContext();
                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(coursesFragment.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
                dpd.show(activity.getFragmentManager(), DATEPICKER);

                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        boolean flagMonth = false, flagDay = false;

        if (dayOfMonth / 10 < 1)
            flagDay = true;
        if ((monthOfYear + 1) / 10 < 1)
            flagMonth = true;

        date = year + "";
        if (flagMonth)
            date += "/0" + (monthOfYear + 1);
        else
            date += "/" + (monthOfYear + 1);
        if (flagDay)
            date += "/0" + dayOfMonth;
        else
            date += "/" + dayOfMonth;


        t.setText(date);
//        startDateInt = date.replace("/", "");


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

            list = webService.getGymCourses(App.isInternetOn(), idGym);

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

    private class WebServiceListCoach extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            listcoaches = new ArrayList<>();
            Recycler.showShimmerAdapter();
        }

        @Override
        protected Void doInBackground(Object... params) {

            listcoaches = webService.getGymCoaches(App.isInternetOn(), idGym);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Recycler.hideShimmerAdapter();

            if (listcoaches != null) {
                for (int i = 0; i < listcoaches.size(); i++) {
                    coaches = new ArrayList<>();
                    coachesId = new ArrayList<>();
                    coachesId.clear();
                    coaches.clear();
                    coaches.add(listcoaches.get(i).fName + " " + listcoaches.get(i).lName);
                    coachesId.add(listcoaches.get(i).idUser);
                }

            }
        }

    }
    private class WebServiceADD extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        Integer result;
        int pos;
        String title;
        String days;
        String StartDate;
        String EndDate;

        public WebServiceADD(String t,String d,String S,String E) {
            title=t;
            days=d;
            StartDate=S;
            EndDate=E;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnOk.startAnimation();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.AddGymTerm(App.isInternetOn(),idGym,coachesId.get(pos),title,days,StartDate,EndDate);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {
                if (result>0) {


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

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);
    }

}
