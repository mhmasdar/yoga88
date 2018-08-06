package com.technologygroup.rayannoor.yoga.Coaches;


import android.app.Dialog;
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
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.CoachKaryabAdapter;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class karyabiFragment extends Fragment {


    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private Button btnTryAgain;
    private LinearLayout lytEmpty;
    private FloatingActionButton floactAction;
    private Dialog dialog;
    private TextView txtWindowTitle;
    private ImageView imgClose;
    private EditText edtTitle;
    private EditText edtUniversity;
    private TextView txtNoImage;
    private ImageView imgCertificate;
    private ImageView imgSelectPicture;
    private CircularProgressButton btnOk;

    public karyabiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karyabi, container, false);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);

        setUpRecyclerView();


        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void setUpRecyclerView() {
        CoachKaryabAdapter adapter = new CoachKaryabAdapter(getActivity());
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_coach_karyab);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
        txtNoImage = (TextView) dialog.findViewById(R.id.txtNoImage);
        imgSelectPicture = (ImageView) dialog.findViewById(R.id.imgSelectPicture);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
