package com.technologygroup.rayannoor.yoga.referees;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.technologygroup.rayannoor.yoga.Coaches.CoachServicesActivity;
import com.technologygroup.rayannoor.yoga.Coaches.educationFragment;
import com.technologygroup.rayannoor.yoga.Models.CoachEduModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.CoachEducationAdapter;
import com.technologygroup.rayannoor.yoga.adapters.RefereeEducationAdapter;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class refEducationFragment extends Fragment {

    private LinearLayout lytMain, lytDisconnect, lytEmpty;
    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floatAction;
    private Dialog dialog;
    private EditText edtTitle, edtUniversity, edtDate;
    private TextView txtNoImage;
    private ImageView imgCertificate, imgSelectPicture, imgClose;
    private CircularProgressButton btnOk;

    public refEducationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ref_education, container, false);
        initView(view);

        setUpRecyclerView();


        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }


    public void initView(View view){
        lytEmpty = view.findViewById(R.id.lytEmpty);
        lytMain = view.findViewById(R.id.lytMain);
        lytDisconnect = view.findViewById(R.id.lytDisconnect);
        Recycler = view.findViewById(R.id.Recycler);
        floatAction = view.findViewById(R.id.floatAction);
    }

    private void setUpRecyclerView() {
        RefereeEducationAdapter adapter = new RefereeEducationAdapter(getActivity());
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_education);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        edtTitle = dialog.findViewById(R.id.edtTitle);
        edtUniversity = dialog.findViewById(R.id.edtUniversity);
        edtDate = dialog.findViewById(R.id.edtDate);
        txtNoImage = dialog.findViewById(R.id.txtNoImage);
        imgCertificate = dialog.findViewById(R.id.imgCertificate);
        imgSelectPicture = dialog.findViewById(R.id.imgSelectPicture);
        btnOk = dialog.findViewById(R.id.btnOk);
        imgClose = dialog.findViewById(R.id.imgClose);


    }
}
