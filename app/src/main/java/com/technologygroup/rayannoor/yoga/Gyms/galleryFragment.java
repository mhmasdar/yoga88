package com.technologygroup.rayannoor.yoga.Gyms;


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
import android.support.v7.widget.GridLayoutManager;
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
import com.technologygroup.rayannoor.yoga.Models.GalleryModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.FilePath;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.GymHonourAdapter;
import com.technologygroup.rayannoor.yoga.adapters.GymImageAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class galleryFragment extends Fragment {


    private ShimmerRecyclerView Recycler;
    private FloatingActionButton floactAction;
    private LinearLayout lytMain;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    Dialog dialog;
    private boolean calledFromPanel = false;
    private int idGym;
    List<GalleryModel> list;
    GymHonourAdapter adapter;
    WebServiceList webServiceCoachInfo;
    private ImageView imgClose;
    private EditText edtTitle;
    private TextView txtNoImage;
    private ImageView img;
    private ImageView imgSelectPicture;
    private CircularProgressButton btnOk;
    public boolean flagPermission = false;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath, selectedImgName = "";

    public galleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }
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


        floactAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;

    }

    private void setUpRecyclerView(List<GalleryModel> list) {

        GymImageAdapter adapter = new GymImageAdapter(getActivity(), list, idGym, calledFromPanel);
        Recycler.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        Recycler.setLayoutManager(gridLayoutManager);
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

            list = webService.getGymGallery(App.isInternetOn(), idGym);

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

    @Override
    public void onStop() {
        super.onStop();

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);
    }


    private void showDialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_image);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
        txtNoImage = (TextView) dialog.findViewById(R.id.txtNoImage);
        img = (ImageView) dialog.findViewById(R.id.img);
        imgSelectPicture = (ImageView) dialog.findViewById(R.id.imgSelectPicture);
        imgSelectPicture.setOnClickListener(imgSelectPicture_click);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceAdd webServiceAdd=new WebServiceAdd();
                webServiceAdd.execute();
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
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

                img.setVisibility(View.VISIBLE);
                txtNoImage.setVisibility(View.GONE);

                if (selectedFileUri != null)
                    if (!selectedFileUri.equals("") && !selectedFileUri.equals("null"))
                        Glide.with(getContext()).loadFromMediaStore(selectedFileUri).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);

                selectedFilePath = FilePath.getPath(this.getActivity(), selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    String extension = selectedFilePath.substring(selectedFilePath.lastIndexOf(".") + 1, selectedFilePath.length());
                    ClassDate classDate = new ClassDate();
                    selectedImgName = classDate.getDateTime() + "_" + "c_" + idGym + "." + extension;

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
    private class WebServiceAdd extends AsyncTask<Object, Void, Void> {
        int resultAdd=0;
        private WebService webService;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            btnOk.startAnimation();
        }

        @Override
        protected Void doInBackground(Object... params) {

            resultAdd = webService.AddGallery(App.isInternetOn(), idGym,edtTitle.getText().toString(),"");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (resultAdd >0) {
                    sendFileDetails fileDetails = new sendFileDetails(resultAdd);
                    fileDetails.execute();

                }

                else
            {
                btnOk.revertAnimation();
                Toast.makeText(getContext() , "خطا در ارسال اطلاعات...لطفا دوباره سعی کنید" , Toast.LENGTH_LONG).show();
            }
            }

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

            fileResult = webService.sendFileDetails(App.isInternetOn(), selectedImgName, 4, ObjectID);
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
            }

            else
            {
                btnOk.revertAnimation();
                Toast.makeText(getContext(), "خطا در ارسال اطلاعات...لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



