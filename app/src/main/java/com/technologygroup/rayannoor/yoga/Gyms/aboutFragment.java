package com.technologygroup.rayannoor.yoga.Gyms;


import android.app.Dialog;
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

import com.technologygroup.rayannoor.yoga.R;

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

    public aboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gym_about, container, false);
        txtAbout = (TextView) view.findViewById(R.id.txtAbout);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }


    private void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_about);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtAbout = (EditText) dialog.findViewById(R.id.edtAbout);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
