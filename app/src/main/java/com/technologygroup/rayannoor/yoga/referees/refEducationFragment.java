package com.technologygroup.rayannoor.yoga.referees;


import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Models.CoachEduModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.RefereeEducationAdapter;

import java.util.ArrayList;
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
    private String selectedFilePath, selectedImgName = "";
    private CircularProgressButton btnOk;
    String resultAdd;
    private boolean calledFromPanel = false;
    public boolean flagPermission = false;
    int idCoach;
    List<CoachEduModel> list;

    public refEducationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ref_education, container, false);
        initView(view);
        list=new ArrayList<>();
        idCoach = getArguments().getInt("idCoach", -1);
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        if (!calledFromPanel) {
            floatAction.setVisibility(View.GONE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }

        if (idCoach > 0) {

            WebServiceList webServiceList= new WebServiceList();
            webServiceList.execute();
        } else {
            Toast.makeText(getContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
        }


        floatAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Button btnTryAgain = view.findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCoach > 0) {

                    WebServiceList webServiceList= new WebServiceList();
                    webServiceList.execute();
                } else {
                    Toast.makeText(getContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
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

            list = webService.getCoachEdu(App.isInternetOn(), idCoach);

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

    private class WebServiceAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        CoachEduModel model;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            btnOk.startAnimation();


            model = new CoachEduModel();
            model.id = -1;
            model.idCoach = idCoach;
//            model.Name = edtTitle.getText().toString();
//            model.gettingPlace = edtUniversity.getText().toString();
            model.Date = edtDate.getText().toString();
//            model.Img = selectedImgName;
        }

        @Override
        protected Void doInBackground(Object... params) {

            resultAdd = webService.AddCoachEdu(App.isInternetOn(), model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (resultAdd != null) {

                if (Integer.parseInt(resultAdd) > 0) {
                    CallBackFile callBackFile = new CallBackFile();
                    callBackFile.execute();

                    model.id = Integer.parseInt(resultAdd);

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


                    list.add(model);
                    setUpRecyclerView();

                } else if (Integer.parseInt(resultAdd) == 0) {

                    btnOk.revertAnimation();
                    Toast.makeText(getContext(), "ارسال اطلاعات ناموفق است", Toast.LENGTH_LONG).show();

                } else {

                    btnOk.revertAnimation();
                    Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

                }
            } else {

                btnOk.revertAnimation();
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

            }
        }
    }

    private class CallBackFile extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        int fileResult;
        String lastUpdate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

//            dialog2 = new Dialog(getContext());
//            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //      dialog2.setContentView(R.layout.dialog_waiting);
//            dialog2.setCancelable(true);
//            dialog2.setCanceledOnTouchOutside(true);
//            dialog2.show();

            ClassDate classDate = new ClassDate();
            lastUpdate = classDate.getDateTime();
        }

        @Override
        protected Void doInBackground(Object... params) {

            fileResult = webService.uploadFile(App.isInternetOn(), selectedFilePath, selectedImgName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (fileResult == 200) {
//                dialog2.dismiss();
                Toast.makeText(getContext(), "تصویر با موفقیت آپلود شد", Toast.LENGTH_SHORT).show();

            } else if (fileResult == 0) {
                Toast.makeText(getContext(), "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            } else {
                Toast.makeText(getContext(), "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            }
        }
    }

    private class CallBackFileDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String deleteResult;
        String lastUpdate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            int j = 0;
            if (resultAdd != null)
                if (Integer.parseInt(resultAdd) > 0)
                    j = 1;
//                    deleteResult = webService.deleteImgDetails(App.isInternetOn(), Integer.parseInt(resultAdd));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            dialog2.dismiss();

        }
    }
}
