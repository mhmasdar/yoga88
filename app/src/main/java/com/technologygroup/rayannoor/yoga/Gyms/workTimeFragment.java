package com.technologygroup.rayannoor.yoga.Gyms;


import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class workTimeFragment extends Fragment {

    private TextView txtWork;
    private FloatingActionButton floactAction;
    private ImageView imgClose;
    private EditText edtWorktime;
    boolean calledFromPanel;
    private CircularProgressButton btnOk;
    private int idGym;
    Dialog dialog;
    WebgetTime webgetTime;
    public workTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_time, container, false);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        txtWork = (TextView) view.findViewById(R.id.txtWork);
        idGym = getArguments().getInt("idGym", -1);
        String work = getArguments().getString("work", "");
        txtWork.setText(work);
        webgetTime=new WebgetTime();
        webgetTime.execute();
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);
        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        if (!calledFromPanel) {
            floactAction.setVisibility(View.GONE);
        }
        return view;
    }

    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_worktime);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtWorktime = (EditText) dialog.findViewById(R.id.edtWorktime);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idGym > 0) {

                    WebEditTime webEditTime = new WebEditTime(edtWorktime.getText().toString());
                    webEditTime.execute();
                } else {
                    Toast.makeText(getContext(), "باشگاه مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
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
    private class WebgetTime extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.getworktime(App.isInternetOn(),idGym );
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            txtWork.setText(result);
        }

    }
    private class WebEditTime extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String work;
        WebEditTime(String s)
        {
            work =s;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.editGymWorkTime(App.isInternetOn(),work,idGym);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if(result.equals("OK")||result.equals("Ok")) {
                txtWork.setText(work);

            }
        }

    }
    @Override
    public void onStop() {
        super.onStop();

        if (webgetTime != null)
            if (webgetTime.getStatus() == AsyncTask.Status.RUNNING)
                webgetTime.cancel(true);
    }

}
