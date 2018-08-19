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

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class aboutFragment extends Fragment {


    private TextView txtAbout;
    private FloatingActionButton floactAction;
    private ImageView imgClose;
    private EditText edtAbout;
    private CircularProgressButton btnOk;
    String Biotemp;
    Dialog dialog;
    int idGym;
    boolean calledFromPanel;
    public aboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gym_about, container, false);
        txtAbout = (TextView) view.findViewById(R.id.txtAbout);
        String about = getArguments().getString("about", "");
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idGym = getArguments().getInt("idGym", -1);
        txtAbout.setText(about);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        if(!calledFromPanel)
        {
            floactAction.setVisibility(View.GONE);
        }
        return view;
    }


    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_about);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtAbout = (EditText) dialog.findViewById(R.id.edtAbout);
        edtAbout.setText(txtAbout.getText().toString());
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Biotemp=edtAbout.getText().toString();
                WebServiceBio webServiceBio=new WebServiceBio();
                webServiceBio.execute();
                dialog.dismiss();
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
    private class WebServiceBio extends AsyncTask<Object, Void, Void> {

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
            result = webService.editCoachBio(App.isInternetOn(), edtAbout.getText().toString().trim() ,idGym,"gym");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(result.equals("Ok"))
            {
                txtAbout.setText(Biotemp);
            }


        }

    }
}
