package com.technologygroup.rayannoor.yoga.Coaches;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Models.CoachEduModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.FilePath;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.CoachEducationAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class educationFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener {


    LinearLayout lytMain, lytDisconnect, lytEmpty;
    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floatAction;
    private Dialog dialog;

    private SharedPreferences prefs;
    private int idCoach;
    List<CoachEduModel> list;
    public boolean flagPermission = false;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath, selectedImgName = "";


    String resultAdd;

    // dialog add content
    EditText edtTitle, edtUniversity, edtDate;
    TextView txtNoImage;
    ImageView imgCertificate, imgSelectPicture, imgClose;
    CircularProgressButton btnOk;


    //relates to date and time picker
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";
    String date;

    CoachEducationAdapter adapter;

    private boolean calledFromPanel = false;
    WebServiceAdd callBackFileDetails;
    WebServiceList webServiceCoachInfo;


    public educationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idCoach = getArguments().getInt("idCoach", -1);

        initView(view);

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

            webServiceCoachInfo = new WebServiceList();
            webServiceCoachInfo.execute();
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

                    webServiceCoachInfo = new WebServiceList();
                    webServiceCoachInfo.execute();
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

    private void setUpRecyclerView(List<CoachEduModel> list) {
        adapter = new CoachEducationAdapter(getActivity(), list, idCoach, calledFromPanel);
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


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CoachServicesActivity activity = (CoachServicesActivity) getContext();

                PersianCalendar now = new PersianCalendar();

                DatePickerDialog dpd = DatePickerDialog.newInstance(educationFragment.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
                dpd.show(activity.getFragmentManager(), DATEPICKER);
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
            }
        });

        imgSelectPicture.setOnClickListener(imgSelectPicture_click);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtTitle.getText().toString().equals("") && !edtUniversity.getText().toString().equals("")&& !edtDate.getText().toString().equals("")) {

                    if (!selectedImgName.equals("")) {
                        callBackFileDetails = new WebServiceAdd();
                        callBackFileDetails.execute();

                    } else {
                        Toast.makeText(getContext(), "لطفا تصویر مدرک را انتخاب کنید", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getContext(), "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }

            }
        });

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


        edtDate.setText(date);
//        startDateInt = date.replace("/", "");


    }

    View.OnClickListener imgSelectPicture_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (flagPermission) {

                if (App.isInternetOn()) {

                    if (idCoach > 0) {

                        showFileChooser();

                    }
                } else {
                    Toast.makeText(getContext(), "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
                }
            }

        }
    };

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "انتخاب فایل"), PICK_FILE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (data == null) {
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();

                imgCertificate.setVisibility(View.VISIBLE);
                txtNoImage.setVisibility(View.GONE);

                if (selectedFileUri != null)
                    if (!selectedFileUri.equals("") && !selectedFileUri.equals("null"))
                        Glide.with(getContext()).loadFromMediaStore(selectedFileUri).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCertificate);

                selectedFilePath = FilePath.getPath(this.getActivity(), selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    String extension = selectedFilePath.substring(selectedFilePath.lastIndexOf(".") + 1, selectedFilePath.length());
                    ClassDate classDate = new ClassDate();
                    selectedImgName = classDate.getDateTime() + "_" + "c_" + idCoach + "." + extension;

                }
            } else {
                Toast.makeText(getContext(), "خطا در انتخاب فایل", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
            flagPermission = true;
        } else
            flagPermission = false;
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
            model.Title=edtTitle.getText().toString();
//            model.Name = edtTitle.getText().toString();
//            model.gettingPlace = edtUniversity.getText().toString();
            model.Date = edtDate.getText().toString();
//            model.Img = selectedImgName;
        }

        @Override
        protected Void doInBackground(Object... params) {

            resultAdd = webService.AddCoachEdu(App.isInternetOn(), idCoach,model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            int y=0;
            try {
                JSONObject add=new JSONObject(resultAdd);
                y=add.getInt("ID");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (resultAdd != null) {

                if (y > 0) {
                    CallBackFile callBackFile = new CallBackFile();
                    callBackFile.execute();

                    model.id = y;

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
                    setUpRecyclerView(list);

                } else if (y == 0) {

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

    @Override
    public void onStop() {
        super.onStop();

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);

        if (callBackFileDetails != null)
            if (callBackFileDetails.getStatus() == AsyncTask.Status.RUNNING)
                callBackFileDetails.cancel(true);
    }

}
