package com.technologygroup.rayannoor.yoga.Coaches;


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

import org.json.JSONException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class bioFragment extends Fragment {


    private TextView txtBio;
    private FloatingActionButton floactAction;
    private ImageView imgClose;
    private EditText edtBio;
    private CircularProgressButton btnOk;
    private String Bio;
    private String Biotemp;
    private int idCoach;
    boolean calledFromPanel;
    WebgetBio webgetBio;
    WebServiceBio webServiceBio;
    public bioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bio, container, false);
         calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        txtBio = (TextView) view.findViewById(R.id.txtBio);
        idCoach = getArguments().getInt("idCoach", -1);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);
        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        if(!calledFromPanel)
        {
         floactAction.setVisibility(View.GONE);
        }
        webgetBio=new WebgetBio();
        webgetBio.execute();
        return view;
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_coach_bio);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);

        edtBio = (EditText) dialog.findViewById(R.id.edtBio);
        edtBio.setText(Bio);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Biotemp=edtBio.getText().toString();
                webServiceBio=new WebServiceBio();
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
            try {
                result = webService.editCoachBio(App.isInternetOn(), edtBio.getText().toString().trim() ,idCoach,"coach");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(result.equals("Ok"))
            {
            txtBio.setText(Biotemp);
            }


        }

    }

    private class WebgetBio extends AsyncTask<Object, Void, Void> {

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
            result = webService.getBio(App.isInternetOn(), idCoach);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            result=result.replace("\"","");
            result=result.replace("\\n","\n");
            txtBio.setText(result);
            Bio=result;
        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (webgetBio != null)
            if (webgetBio.getStatus() == AsyncTask.Status.RUNNING)
                webgetBio.cancel(true);

        if (webServiceBio != null)
            if (webServiceBio.getStatus() == AsyncTask.Status.RUNNING)
                webServiceBio.cancel(true);
    }



}
