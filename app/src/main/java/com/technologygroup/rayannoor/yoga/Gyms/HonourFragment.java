package com.technologygroup.rayannoor.yoga.Gyms;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.FilePath;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.GymHonourAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HonourFragment extends Fragment implements
        DatePickerDialog.OnDateSetListener{


    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floactAction;
    private LinearLayout lytMain;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private Dialog dialog;
    private ImageView imgClose;
    private EditText edtTitle;
    private EditText edtDate;
    private TextView txtNoImage;
    private ImageView imgHonour;
    private ImageView imgSelectPicture;
    private CircularProgressButton btnOk;
    public boolean flagPermission = false;
    private boolean calledFromPanel = false;
    private static final int PICK_FILE_REQUEST = 1;
    private int idGym;
    List<CoachHonorModel> list;
    GymHonourAdapter adapter;
    WebServiceList webServiceCoachInfo;
    private String selectedFilePath, selectedImgName = "";
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";

    public HonourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_honour, container, false);

        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idGym = getArguments().getInt("idGym", -1);



        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        floactAction = (FloatingActionButton) view.findViewById(R.id.floactAction);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);

//        setUpRecyclerView();

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }

        return view;
    }

    private void setUpRecyclerView(List<CoachHonorModel> list) {
        adapter = new GymHonourAdapter(getActivity(), list, idGym, calledFromPanel);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
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

                imgHonour.setVisibility(View.VISIBLE);
                txtNoImage.setVisibility(View.GONE);

                if (selectedFileUri != null)
                    if (!selectedFileUri.equals("") && !selectedFileUri.equals("null"))
                        Glide.with(getContext()).loadFromMediaStore(selectedFileUri).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgHonour);

                selectedFilePath = FilePath.getPath(this.getActivity(), selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    String extension = selectedFilePath.substring(selectedFilePath.lastIndexOf(".") + 1, selectedFilePath.length());
                    ClassDate classDate = new ClassDate();
                    selectedImgName = classDate.getDateTime() + "_" + "g_" + idGym + "." + extension;

                }
            } else {
                Toast.makeText(getContext(), "خطا در انتخاب فایل", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_honour);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
        edtDate = (EditText) dialog.findViewById(R.id.edtDate);
        txtNoImage = (TextView) dialog.findViewById(R.id.txtNoImage);
        imgHonour = (ImageView) dialog.findViewById(R.id.imgHonour);
        imgSelectPicture = (ImageView) dialog.findViewById(R.id.imgSelectPicture);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        imgSelectPicture.setOnClickListener(imgSelectPicture_click);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GymServiceActivity activity = (GymServiceActivity) getContext();

                PersianCalendar now = new PersianCalendar();

                DatePickerDialog dpd = DatePickerDialog.newInstance(HonourFragment.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
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

             WebServiceAdd web=new WebServiceAdd();
             web.execute();
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        boolean flagMonth = false, flagDay = false;
        String date;
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

            list = webService.getCoachHonor(App.isInternetOn(), idGym);

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
    private class WebServiceAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        CoachHonorModel model;
        String resultAdd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            btnOk.startAnimation();


            model = new CoachHonorModel();
            model.id = -1;
            model.id = idGym;
            model.Title=edtTitle.getText().toString();
//            model.Name = edtTitle.getText().toString();
//            model.gettingPlace = edtUniversity.getText().toString();
            model.Date = edtDate.getText().toString();
//            model.Img = selectedImgName;
        }

        @Override
        protected Void doInBackground(Object... params) {

            resultAdd = webService.AddCoachHonor(App.isInternetOn(),model, idGym);

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
                    model.id=y;
                    sendFileDetails fileDetails = new sendFileDetails(model, y);
                    fileDetails.execute();

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
    private class sendFileDetails extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String fileResult;
        CoachHonorModel model;
        int ObjectID;

        sendFileDetails(CoachHonorModel model, int ObjectID)
        {
            this.model = model;
            this.ObjectID = ObjectID;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            fileResult = webService.sendFileDetails(App.isInternetOn(), selectedImgName, 2, ObjectID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (fileResult != null && fileResult.equals("ok")) //file uploaded successfully
            {
                CallBackFile callBackFile = new CallBackFile(model);
                callBackFile.execute();
            }

            else
            {
                btnOk.revertAnimation();
                Toast.makeText(getContext(), "خطا در ارسال اطلاعات...لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class CallBackFile extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        int fileResult;
        String lastUpdate;
        CoachHonorModel model;

        CallBackFile(CoachHonorModel model)
        {
            this.model = model;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

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


            if (fileResult == 200) //file uploaded successfully
            {

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

                Toast.makeText(getContext(), "تصویر با موفقیت آپلود شد", Toast.LENGTH_SHORT).show();

            }

            else
            {
                btnOk.revertAnimation();
                Toast.makeText(getContext(), "خطا در ارسال اطلاعات...لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "انتخاب فایل"), PICK_FILE_REQUEST);
    }
    View.OnClickListener imgSelectPicture_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (flagPermission) {

                if (App.isInternetOn()) {

                    if (idGym > 0) {

                        showFileChooser();

                    }
                } else {
                    Toast.makeText(getContext(), "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
                }
            }

        }
    };
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

    @Override
    public void onStop() {
        super.onStop();

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);
    }
}
