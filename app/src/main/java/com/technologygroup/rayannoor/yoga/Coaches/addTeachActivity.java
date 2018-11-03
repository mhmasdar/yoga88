package com.technologygroup.rayannoor.yoga.Coaches;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private String[] boolimage = new String[10];
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
    private String selectedFilePathTemp,selectedImgNameTemp;
    Dialog dialog;
    private boolean calledToAdd;
    private int id;
    private String Title, Images, Body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teach);
        initView();

        for (int j = 0; j < 10; j++) {

            selectedImgName.add(j, "");
            selectedFilePath.add(j, "");
            bodyList.add(j, "");
            boolimage[j]="0";

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




            for (int j = 0; j < visibleLyts; j++) {

                lytTeach[j].setVisibility(View.VISIBLE);

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



                if (!edtTitle.getText().toString().equals("")) {

                    for (int j = 0; j < 10; j++) {
                        bodyList.set(j, edtBody[j].getText().toString());
                    }

                    if (!calledToAdd){

//                        webServiceEdit = new WebServiceEdit();
//                        webServiceEdit.execute();

                    } else {

                        AddTrain addTrain = new AddTrain();
                        addTrain.execute();

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
                    selectedFilePathTemp=selectedFilePath.get(requestCode);
                    selectedImgNameTemp=selectedImgName.get(requestCode);
                    CallBackFile callBackFile=new CallBackFile();
                    callBackFile.execute();
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
    private class AddTrain extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        JSONObject jsonObject;
        JSONObject jsonObjectBody;
        JSONObject jsonObjectImage;
        JSONArray jsonArrayImage;
        JSONArray JSONArray;
        String lastUpdate;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            SharedPreferences prefs = getSharedPreferences("User", 0);
            int fieldNumber = prefs.getInt("idField", 0);
            jsonObject=new JSONObject();
            jsonObjectBody=new JSONObject();
            jsonObjectImage=new JSONObject();
            jsonArrayImage=new JSONArray();


            try {
                jsonObject.put("id","");
                jsonObject.put("Title",edtTitle.getText().toString());
                jsonObject.put("UserRoleID",idCoach);
                jsonObject.put("FieldID",fieldNumber);
                for(int i=0;i<visibleLyts+1;i++)
                {
                    JSONArray=new JSONArray();
                    jsonObjectImage.put("ID","");
                    jsonObjectImage.put("Name",selectedImgName.get(i));
                    jsonArrayImage.put(jsonObjectImage);
                    jsonObjectBody.put("Images",jsonArrayImage);
                    jsonObjectBody.put("Body",edtBody[i].getText().toString());
                    jsonObjectBody.put("ID","");
                    JSONArray.put(jsonObjectBody);

                }
                jsonObject.put("Bodies",JSONArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Dialog dialog2 = new Dialog(getApplicationContext());
//            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog2.setContentView(R.layout.dialog_wait);
//            dialog2.setCancelable(true);
//            dialog2.setCanceledOnTouchOutside(true);
//            dialog2.show();

            ClassDate classDate = new ClassDate();
            lastUpdate = classDate.getDateTime();
        }

        @Override
        protected Void doInBackground(Object... params) {
            result = webService.AddTeaches(App.isInternetOn(), jsonObject);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            dialog.dismiss();
            if(result!=null) {
                if (result.equals("OK")) {
                    Toast.makeText(addTeachActivity.this, "با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    Toast.makeText(addTeachActivity.this, "نا موفق", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(addTeachActivity.this, "نا موفق", Toast.LENGTH_SHORT).show();
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

//            Dialog dialog2 = new Dialog(getApplicationContext());
//            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog2.setContentView(R.layout.dialog_wait);
//            dialog2.setCancelable(true);
//            dialog2.setCanceledOnTouchOutside(true);
//            dialog2.show();

            ClassDate classDate = new ClassDate();
            lastUpdate = classDate.getDateTime();
        }

        @Override
        protected Void doInBackground(Object... params) {
            fileResult = webService.uploadFile(App.isInternetOn(), selectedFilePathTemp, selectedImgNameTemp);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            dialog.dismiss();
            if (fileResult == 200) {


            } else if (fileResult == 0) {

            } else {

            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();


    }

}
