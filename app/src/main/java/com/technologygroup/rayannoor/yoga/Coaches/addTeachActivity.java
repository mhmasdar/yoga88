package com.technologygroup.rayannoor.yoga.Coaches;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.FilePath;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class addTeachActivity extends AppCompatActivity {

    private TextView txtTitle;
    private RelativeLayout btnBack;
    private EditText edtTitle;
    private LinearLayout[] lytTeach = new LinearLayout[10];
    private TextView[] txtNoImage = new TextView[10];
    private ImageView[] imgTeach = new ImageView[10];
    private ImageView[] imgSelectPicture = new ImageView[10];
    private EditText[] edtBody = new EditText[10];
    private TextView lytAddLesson, lytDeleteLesson;
    private RelativeLayout lytSend;
    private TextView txtSend;

    private int visibleLyts = 0;
    public boolean flagPermission = false;
    private int idCoach;
    private static int PICK_FILE_REQUEST = 1;
    private List<String> selectedFilePath = new ArrayList<>();
    private List<String> selectedImgName = new ArrayList<>();
    private List<String> bodyList = new ArrayList<>();
    private String resultAdd, resultEdit;
    Dialog dialog;
    private boolean calledToAdd;
    private int id;
    private String Title, Images, Body;
    WebServiceEdit webServiceEdit;
    WebServiceAdd webServiceAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teach);
        initView();

        for (int j = 0; j < 10; j++) {

            selectedImgName.add(j, "");
            selectedFilePath.add(j, "");
            bodyList.add(j, "");

        }

        calledToAdd = getIntent().getBooleanExtra("calledToAdd", true);
        idCoach = getIntent().getIntExtra("idRow", -1);

        if (!calledToAdd) {

            txtSend.setText("ویرایش");

            id = getIntent().getIntExtra("id", -1);
            Title = getIntent().getStringExtra("Title");
            Body = getIntent().getStringExtra("Body");
            Images = getIntent().getStringExtra("Images");

            edtTitle.setText(Title);


            String[] tmp = new String[10];
            String[] tmp2 = new String[10];

            tmp = Body.split("~");
            tmp2 = Images.split("~");

            if (tmp.length > tmp2.length)
                visibleLyts = tmp.length;
            else
                visibleLyts = tmp2.length;

            for (int j = 0; j < visibleLyts; j++) {

                lytTeach[j].setVisibility(View.VISIBLE);

            }

            for (int j = 0; j < tmp.length; j++){
                bodyList.set(j, tmp[j]);
                edtBody[j].setText(bodyList.get(j));
            }
            for (int j = 0; j < tmp2.length; j++){

                selectedImgName.set(j, tmp2[j]);
                if (!selectedImgName.get(j).equals("")) {
                    Glide.with(this).load(App.imgAddr + selectedImgName.get(j)).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgTeach[j]);
                    txtNoImage[j].setVisibility(View.GONE);
                    imgTeach[j].setVisibility(View.VISIBLE);
                }

            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lytAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (visibleLyts < 9) {

                    visibleLyts++;
                    lytTeach[visibleLyts].setVisibility(View.VISIBLE);
                }

            }
        });

        lytDeleteLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (visibleLyts > 0) {

                    lytTeach[visibleLyts].setVisibility(View.GONE);
                    imgTeach[visibleLyts].setVisibility(View.GONE);
                    txtNoImage[visibleLyts].setVisibility(View.VISIBLE);
                    edtBody[visibleLyts].setText("");
                    selectedFilePath.set(visibleLyts, "");
                    selectedImgName.set(visibleLyts, "");
                    bodyList.set(visibleLyts, "");
                    visibleLyts--;
                }

            }
        });

        for (int j = 0; j < 10; j++) {
            setOnClick(imgSelectPicture[j], j);
        }

        lytSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                numberOfDes = 0;
//                numberOfImages = 0;
//                numberOfFilledLyts = 0;
//
//                for (int j = 1; j < visibleLyts + 1; j++) {
//
//                    if (!edtBody[j].getText().toString().equals("") || !selectedImgName[j].equals("")) {
//                        numberOfFilledLyts++;
//                    }
//                    if (!selectedImgName[j].equals("")) {
//                        numberOfImages++;
//                    }
//                }
//
//                if (!edtTitle.getText().toString().equals("")) {
//
//                    if (numberOfFilledLyts == 0) {
//                        Toast.makeText(addTeachActivity.this, "حرکتی اضافه نشده است", Toast.LENGTH_LONG).show();
//                    } else {
//                        WebServiceAdd webServiceAdd = new WebServiceAdd();
//                        webServiceAdd.execute();
//                    }
//                } else {
//                    Toast.makeText(addTeachActivity.this, "عنوان وارد نشده است", Toast.LENGTH_LONG).show();
//                }


                if (!edtTitle.getText().toString().equals("")) {

                    for (int j = 0; j < 10; j++) {
                        bodyList.set(j, edtBody[j].getText().toString());
                    }

                    if (!calledToAdd){

                        webServiceEdit = new WebServiceEdit();
                        webServiceEdit.execute();

                    } else {

                        webServiceAdd = new WebServiceAdd();
                        webServiceAdd.execute();

                    }

                } else {
                    Toast.makeText(addTeachActivity.this, "عنوان وارد نشده است", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void initView() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        lytTeach[0] = (LinearLayout) findViewById(R.id.lytTeach1);
        txtNoImage[0] = (TextView) findViewById(R.id.txtNoImage1);
        imgTeach[0] = (ImageView) findViewById(R.id.imgTeach1);
        imgSelectPicture[0] = (ImageView) findViewById(R.id.imgSelectPicture1);
        edtBody[0] = (EditText) findViewById(R.id.edtBody1);
        lytTeach[1] = (LinearLayout) findViewById(R.id.lytTeach2);
        txtNoImage[1] = (TextView) findViewById(R.id.txtNoImage2);
        imgTeach[1] = (ImageView) findViewById(R.id.imgTeach2);
        imgSelectPicture[1] = (ImageView) findViewById(R.id.imgSelectPicture2);
        edtBody[1] = (EditText) findViewById(R.id.edtBody2);
        lytTeach[2] = (LinearLayout) findViewById(R.id.lytTeach3);
        txtNoImage[2] = (TextView) findViewById(R.id.txtNoImage3);
        imgTeach[2] = (ImageView) findViewById(R.id.imgTeach3);
        imgSelectPicture[2] = (ImageView) findViewById(R.id.imgSelectPicture3);
        edtBody[2] = (EditText) findViewById(R.id.edtBody3);
        lytTeach[3] = (LinearLayout) findViewById(R.id.lytTeach4);
        txtNoImage[3] = (TextView) findViewById(R.id.txtNoImage4);
        imgTeach[3] = (ImageView) findViewById(R.id.imgTeach4);
        imgSelectPicture[3] = (ImageView) findViewById(R.id.imgSelectPictur4);
        edtBody[3] = (EditText) findViewById(R.id.edtBody4);
        lytTeach[4] = (LinearLayout) findViewById(R.id.lytTeach5);
        txtNoImage[4] = (TextView) findViewById(R.id.txtNoImage5);
        imgTeach[4] = (ImageView) findViewById(R.id.imgTeach5);
        imgSelectPicture[4] = (ImageView) findViewById(R.id.imgSelectPicture5);
        edtBody[4] = (EditText) findViewById(R.id.edtBody5);
        lytTeach[5] = (LinearLayout) findViewById(R.id.lytTeach6);
        txtNoImage[5] = (TextView) findViewById(R.id.txtNoImage6);
        imgTeach[5] = (ImageView) findViewById(R.id.imgTeach6);
        imgSelectPicture[5] = (ImageView) findViewById(R.id.imgSelectPicture6);
        edtBody[5] = (EditText) findViewById(R.id.edtBody6);
        lytTeach[6] = (LinearLayout) findViewById(R.id.lytTeach7);
        txtNoImage[6] = (TextView) findViewById(R.id.txtNoImage7);
        imgTeach[6] = (ImageView) findViewById(R.id.imgTeach7);
        imgSelectPicture[6] = (ImageView) findViewById(R.id.imgSelectPicture7);
        edtBody[6] = (EditText) findViewById(R.id.edtBody7);
        lytTeach[7] = (LinearLayout) findViewById(R.id.lytTeach8);
        txtNoImage[7] = (TextView) findViewById(R.id.txtNoImage8);
        imgTeach[7] = (ImageView) findViewById(R.id.imgTeach8);
        imgSelectPicture[7] = (ImageView) findViewById(R.id.imgSelectPicture8);
        edtBody[7] = (EditText) findViewById(R.id.edtBody8);
        lytTeach[8] = (LinearLayout) findViewById(R.id.lytTeach9);
        txtNoImage[8] = (TextView) findViewById(R.id.txtNoImage9);
        imgTeach[8] = (ImageView) findViewById(R.id.imgTeach9);
        imgSelectPicture[8] = (ImageView) findViewById(R.id.imgSelectPicture9);
        edtBody[8] = (EditText) findViewById(R.id.edtBody9);
        lytTeach[9] = (LinearLayout) findViewById(R.id.lytTeach10);
        txtNoImage[9] = (TextView) findViewById(R.id.txtNoImage10);
        imgTeach[9] = (ImageView) findViewById(R.id.imgTeach10);
        imgSelectPicture[9] = (ImageView) findViewById(R.id.imgSelectPicture10);
        edtBody[9] = (EditText) findViewById(R.id.edtBody10);
        lytAddLesson = (TextView) findViewById(R.id.lytAddLesson);
        lytDeleteLesson = (TextView) findViewById(R.id.lytDeleteLesson);
        lytSend = (RelativeLayout) findViewById(R.id.lytSend);
        txtSend = findViewById(R.id.txtSend);
    }


    private void setOnClick(final ImageView img, final int i) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagPermission) {

                    if (App.isInternetOn()) {

                        if (idCoach > 0) {

                            PICK_FILE_REQUEST = i;
                            showFileChooser(i);

                        }
                    } else {
                        Toast.makeText(addTeachActivity.this, "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    private void showFileChooser(int i) {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent, "انتخاب فایل"), i);
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

                imgTeach[requestCode].setVisibility(View.VISIBLE);
                txtNoImage[requestCode].setVisibility(View.GONE);

                if (selectedFileUri != null)
                    if (!selectedFileUri.equals("") && !selectedFileUri.equals("null"))
                        Glide.with(this).loadFromMediaStore(selectedFileUri).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgTeach[requestCode]);

                selectedFilePath.set(requestCode, FilePath.getPath(this, selectedFileUri));
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath.get(requestCode) != null && !selectedFilePath.get(requestCode).equals("")) {

                    String extension = selectedFilePath.get(requestCode).substring(selectedFilePath.get(requestCode).lastIndexOf(".") + 1, selectedFilePath.get(requestCode).length());
                    ClassDate classDate = new ClassDate();
                    selectedImgName.set(requestCode, classDate.getDateTime() + "_" + "t_" + idCoach + "." + extension);
                }
            } else {
                Toast.makeText(this, "خطا در انتخاب فایل", Toast.LENGTH_SHORT).show();
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

        private WebService webService;
        TeachesModel model;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(addTeachActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog.findViewById(R.id.logo);

            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();


            webService = new WebService();
            model = new TeachesModel();
            ClassDate classDate = new ClassDate();

            model.id = -1;
          //  model.idRow = idCoach;
            model.Title = edtTitle.getText().toString();
            model.Date = classDate.getDate();
          //  model.isGym = false;
            model.Body = "";
            model.Images = "";

            for (int j = 0; j < 10; j++) {

                if (!bodyList.get(j).equals("") || !selectedImgName.get(j).equals("")) {


                    model.Body += bodyList.get(j);
                    if (j != 9)
                        model.Body += "~";

                    model.Images += selectedImgName.get(j);
                    if (j != 9)
                        model.Images += "~";

                }

            }

        }

        @Override
        protected Void doInBackground(Object... params) {

            resultAdd = webService.AddTeaches(App.isInternetOn(), model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (resultAdd != null) {

                if (Integer.parseInt(resultAdd) > 0) {

                    CallBackFile callBackFile = new CallBackFile();
                    callBackFile.execute();

                    //model.id = Integer.parseInt(resultAdd);

                } else if (Integer.parseInt(resultAdd) == 0) {

                    Toast.makeText(addTeachActivity.this, "ارسال آموزش ناموفق است", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(addTeachActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(addTeachActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

            }
        }
    }

    private class WebServiceEdit extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        TeachesModel model;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new Dialog(addTeachActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog.findViewById(R.id.logo);

            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();


            webService = new WebService();
            model = new TeachesModel();
            ClassDate classDate = new ClassDate();

            model.id = id;
           // model.idRow = idCoach;
            model.Title = edtTitle.getText().toString();
            model.Date = classDate.getDate();
          //  model.isGym = false;
            model.Body = "";
            model.Images = "";

            for (int j = 0; j < 10; j++) {

                if (!bodyList.get(j).equals("") || !selectedImgName.get(j).equals("")) {


                    model.Body += bodyList.get(j);
                    if (j != 9)
                        model.Body += "~";

                    model.Images += selectedImgName.get(j);
                    if (j != 9)
                        model.Images += "~";

                }

            }

        }

        @Override
        protected Void doInBackground(Object... params) {

            resultEdit = webService.editTeaches(App.isInternetOn(), model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (resultEdit != null) {

                if (resultEdit.equals("true")) {

                    CallBackFile callBackFile = new CallBackFile();
                    callBackFile.execute();

                    //model.id = Integer.parseInt(resultAdd);

                } else if (resultEdit.equals("false")) {

                    Toast.makeText(addTeachActivity.this, "ویرایش آموزش ناموفق است", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(addTeachActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(addTeachActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

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
//            dialog2.setContentView(R.layout.dialog_waiting);
//            dialog2.setCancelable(true);
//            dialog2.setCanceledOnTouchOutside(true);
//            dialog2.show();

            ClassDate classDate = new ClassDate();
            lastUpdate = classDate.getDateTime();
        }

        @Override
        protected Void doInBackground(Object... params) {

            for (int j = 0; j < selectedImgName.size(); j++) {

                if (!selectedImgName.get(j).equals(""))
                    fileResult = webService.uploadFile(App.isInternetOn(), selectedFilePath.get(j), selectedImgName.get(j));

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();


            if (resultAdd != null) {

                if (Integer.parseInt(resultAdd) > 0) {

                    Toast.makeText(addTeachActivity.this, "آموزش با موفقیت ارسال شد", Toast.LENGTH_LONG).show();
                    finish();

                } else if (Integer.parseInt(resultAdd) == 0) {

                    Toast.makeText(addTeachActivity.this, "ارسال آموزش ناموفق است", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(addTeachActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

                }
            } else if (resultEdit != null){
                if (resultEdit.equals("true")) {

                    Toast.makeText(addTeachActivity.this, "آموزش با موفقیت ویرایش شد", Toast.LENGTH_LONG).show();
                    finish();

                } else if (resultEdit.equals("false")) {

                    Toast.makeText(addTeachActivity.this, "ویرایش آموزش ناموفق است", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(addTeachActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

                }
            }

            else {

                Toast.makeText(addTeachActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

            }


            if (fileResult == 200) {
//                Toast.makeText(addTeachActivity.this, "تصویر با موفقیت آپلود شد", Toast.LENGTH_SHORT).show();

            } else if (fileResult == 0) {
//                Toast.makeText(addTeachActivity.this, "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            } else {
//                Toast.makeText(addTeachActivity.this, "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (webServiceAdd != null)
            if (webServiceAdd.getStatus() == AsyncTask.Status.RUNNING)
                webServiceAdd.cancel(true);
        if (webServiceEdit != null)
            if (webServiceEdit.getStatus() == AsyncTask.Status.RUNNING)
                webServiceEdit.cancel(true);
    }
}
