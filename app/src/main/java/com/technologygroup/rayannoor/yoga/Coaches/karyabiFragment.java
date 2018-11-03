package com.technologygroup.rayannoor.yoga.Coaches;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.FilePath;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.CoachKaryabAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

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
    private EditText edtBody;
    private TextView txtNoImage;
    private ImageView imgCertificate;
    private ImageView imgSelectPicture;
    private CircularProgressButton btnOk;
    private int idCoach;
    private boolean calledFromPanel = false;
    private List<ZanguleModel> list;
    public boolean flagPermission = false;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath, selectedImgName = "";
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
        calledFromPanel = getArguments().getBoolean("calledFromPanel", false);
        idCoach = getArguments().getInt("idCoach", -1);
        if(!calledFromPanel)
        {
            floactAction.setVisibility(View.GONE);
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

        WebServiceList webServiceList=new WebServiceList();
        webServiceList.execute();


        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void setUpRecyclerView() {
        CoachKaryabAdapter adapter = new CoachKaryabAdapter(getActivity(),list);
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
        edtBody = (EditText) dialog.findViewById(R.id.edtBody);
        txtNoImage = (TextView) dialog.findViewById(R.id.txtNoImage);
        imgSelectPicture = (ImageView) dialog.findViewById(R.id.imgSelectPicture);
        imgCertificate = (ImageView) dialog.findViewById(R.id.imgCertificate);
        imgSelectPicture.setOnClickListener(imgSelectPicture_click);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceAdd webServiceADD=new WebServiceAdd(edtTitle.getText().toString(),edtBody.getText().toString());
                webServiceADD.execute();
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
    private class WebServiceAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String title;
        String body;
        int result;
        WebServiceAdd(String title,String body)
        {
            this.body=body;
            this.title=title;
            result=0;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnOk.startAnimation();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.AddKaryabi(App.isInternetOn(),idCoach,title,body);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Recycler.clearAnimation();

            if (result > 0) {
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
                sendFileDetails fileDetails = new sendFileDetails(result);
                fileDetails.execute();
            }

        }

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

            list = webService.getCoachKaryabi(App.isInternetOn(), idCoach);
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
    private class sendFileDetails extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String fileResult;

        int ObjectID;

        sendFileDetails(int ObjectID)
        {

            this.ObjectID = ObjectID;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            fileResult = webService.sendFileDetails(App.isInternetOn(), selectedImgName, 8, ObjectID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (fileResult != null && fileResult.equals("ok")) //file uploaded successfully
            {
                CallBackFile callBackFile = new CallBackFile();
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
                WebServiceList webServiceList=new WebServiceList();
                webServiceList.execute();

            }

            else
            {
                btnOk.revertAnimation();
                Toast.makeText(getContext(), "خطا در ارسال اطلاعات...لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
