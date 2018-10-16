package com.technologygroup.rayannoor.yoga.Coaches;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.MainActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.FilePath;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class CoachEditDetialsActivity extends AppCompatActivity {

    private LinearLayout header;
    private RelativeLayout relativeBack;
    private ImageView imgBack;
    private LinearLayout lytChangePassword;
    private LinearLayout lytLogOut;
    private EditText edtFName;
    private EditText edtLName;
    private EditText edtNatCode;
    private LinearLayout lytMobile;
    private EditText edtMobile;
    private LinearLayout lytTelegram;
    private EditText edtTelegram;
    private LinearLayout lytInstagram;
    private EditText edtInstagram;
    private LinearLayout lytEmail;
    private EditText edtEmail;
    private RoundedImageView imgProfile;
    private LinearLayout lytEditInformation;
    private Dialog dialogForget;

    private int idCoach;
    private boolean flagImgChanged = false;
    CoachModel current;

    public boolean flagPermission = false;
    private static final int PICK_FILE_REQUEST = 4;
    private String selectedFilePath, selectedImgName = "";

    private SharedPreferences prefs;
    WebServiceCallBackEdit callBackFileDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_edit_detials);
        initView();
        setView();

        idCoach = getIntent().getIntExtra("CoachId", -1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lytChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        imgProfile.setOnClickListener(imgSelectPicture_click);

        lytLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CoachEditDetialsActivity.this);
                alert.setTitle("خروج");
                alert.setMessage("آیا مطمئن هستید؟");
                alert.setCancelable(false);
                alert.setIcon(R.drawable.ic_exit);
                alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK &&
                                event.getAction() == KeyEvent.ACTION_UP &&
                                !event.isCanceled()) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                });
                alert.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        prefs = getSharedPreferences("User", 0);
                        prefs.edit().clear().apply();

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isFirstRun", false);
                        editor.apply();

                        Intent intent = new Intent(CoachEditDetialsActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();

                    }
                });
                alert.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alert.create().show();
            }
        });

        lytEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCoach > 0) {

                    if (!edtFName.getText().toString().equals("") && !edtLName.getText().toString().equals("")) {

                        if (edtNatCode.getText().toString().length() == 10) {

                            CoachModel tmpModel = new CoachModel();

                            if (!selectedImgName.equals("")) {

//                                if (!current.Img.equals(selectedImgName)) {
//                                    flagImgChanged = true;
//                                    tmpModel.Img = selectedImgName;
//                                } else {
//                                    flagImgChanged = false;
//                                    tmpModel.Img = current.Img;
//                                }
//                            } else {
//                                flagImgChanged = false;
//                                tmpModel.Img = current.Img;
                            }


                            tmpModel.id = idCoach;
                            tmpModel.fName = edtFName.getText().toString();
                            tmpModel.lName = edtLName.getText().toString();
                            tmpModel.natCode = edtNatCode.getText().toString();
                            tmpModel.Mobile = edtMobile.getText().toString();
                            tmpModel.Telegram = edtTelegram.getText().toString();
                            tmpModel.Instagram = edtInstagram.getText().toString();
                            tmpModel.Email = edtEmail.getText().toString();


                            callBackFileDetails = new WebServiceCallBackEdit(tmpModel);
                            callBackFileDetails.execute();


                        } else {
                            Toast.makeText(CoachEditDetialsActivity.this, "کد ملی صحیح نیست", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(CoachEditDetialsActivity.this, "لطفا نام و نام خانوادگی را وارد کنید", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(CoachEditDetialsActivity.this, "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    private void initView() {
        header = (LinearLayout) findViewById(R.id.header);
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        lytChangePassword = (LinearLayout) findViewById(R.id.lytChangePassword);
        lytLogOut = (LinearLayout) findViewById(R.id.lytLogOut);
        edtFName = (EditText) findViewById(R.id.edtFName);
        edtLName = (EditText) findViewById(R.id.edtLName);
        edtNatCode = (EditText) findViewById(R.id.edtNatCode);
        lytMobile = (LinearLayout) findViewById(R.id.lytMobile);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        lytTelegram = (LinearLayout) findViewById(R.id.lytTelegram);
        edtTelegram = (EditText) findViewById(R.id.edtTelegram);
        lytInstagram = (LinearLayout) findViewById(R.id.lytInstagram);
        edtInstagram = (EditText) findViewById(R.id.edtInstagram);
        lytEmail = (LinearLayout) findViewById(R.id.lytEmail);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        imgProfile = (RoundedImageView) findViewById(R.id.imgProfile);
        lytEditInformation = (LinearLayout) findViewById(R.id.lytEditInformation);
    }

    private void setView() {

        current = new CoachModel();

        if (getIntent().getStringExtra("CoachImg") != null)
            if (!getIntent().getStringExtra("CoachImg").equals("") && !getIntent().getStringExtra("CoachImg").equals("null")) {
                Glide.with(CoachEditDetialsActivity.this).load(App.imgAddr + getIntent().getStringExtra("CoachImg")).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgProfile);
                current.ImgName = getIntent().getStringExtra("CoachImg");
            } else
                current.ImgName = "";
        else current.ImgName = "";

        current.fName = getIntent().getStringExtra("CoachFName");
        current.lName = getIntent().getStringExtra("CoachLName");
        current.natCode = getIntent().getStringExtra("CoachNatCode");
        current.Mobile = getIntent().getStringExtra("CoachMobile");
        current.Telegram = getIntent().getStringExtra("CoachIdTelegram");
        current.Instagram = getIntent().getStringExtra("CoachIdInstagram");
        current.Email = getIntent().getStringExtra("CoachEmail");
        edtFName.setText(getIntent().getStringExtra("CoachFName"));
        edtLName.setText(getIntent().getStringExtra("CoachLName"));
        edtNatCode.setText(getIntent().getStringExtra("CoachNatCode"));
        edtMobile.setText(getIntent().getStringExtra("CoachMobile"));
        edtTelegram.setText(getIntent().getStringExtra("CoachIdTelegram"));
        edtInstagram.setText(getIntent().getStringExtra("CoachIdInstagram"));
        edtEmail.setText(getIntent().getStringExtra("CoachEmail"));

    }

    private void showDialog() {
        dialogForget = new Dialog(CoachEditDetialsActivity.this);
        dialogForget.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForget.setContentView(R.layout.dialog_change_password);
        Button btn_cancel = (Button) dialogForget.findViewById(R.id.btn_cancel);
        Button btnPassSend = (Button) dialogForget.findViewById(R.id.btnPassSend);
        final EditText edtNewPass = (EditText) dialogForget.findViewById(R.id.edtNewPass);
        final EditText edtLastPass = (EditText) dialogForget.findViewById(R.id.edtLastPass);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForget.dismiss();
            }
        });
        btnPassSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceChangePass webServiceChangePass=new WebServiceChangePass(edtLastPass.getText().toString(),edtNewPass.getText().toString());
                webServiceChangePass.execute();
            }
        });
        dialogForget.setCancelable(true);
        dialogForget.setCanceledOnTouchOutside(true);
        dialogForget.show();
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
                    Toast.makeText(CoachEditDetialsActivity.this, "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
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

                if (selectedFileUri != null)
                    if (!selectedFileUri.equals("") && !selectedFileUri.equals("null"))
                        Glide.with(this).loadFromMediaStore(selectedFileUri).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgProfile);

                selectedFilePath = FilePath.getPath(this, selectedFileUri);
                Log.i(TAG, "Selected File Path:" + selectedFilePath);

                if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    String extension = selectedFilePath.substring(selectedFilePath.lastIndexOf(".") + 1, selectedFilePath.length());
                    ClassDate classDate = new ClassDate();
                    selectedImgName = classDate.getDateTime() + "_" + "c_" + idCoach + "." + extension;

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



    private class WebServiceCallBackEdit extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        CoachModel model;
        String result;
        Dialog dialog;

        public WebServiceCallBackEdit(CoachModel model) {
            this.model = model;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog = new Dialog(CoachEditDetialsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog.findViewById(R.id.logo);

            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.EditCoachProfile(App.isInternetOn(), model);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {
                if (result.equals("Ok")) {

                    if (flagImgChanged) {
                        CallBackFile callBackFile = new CallBackFile();
                        callBackFile.execute();
                    }

                    Toast.makeText(CoachEditDetialsActivity.this, "با موفقیت به روز رسانی شد", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CoachEditDetialsActivity.this, "ناموفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(CoachEditDetialsActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
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


            if (fileResult == 200) {
//                dialog2.dismiss();
                Toast.makeText(CoachEditDetialsActivity.this, "تصویر با موفقیت آپلود شد", Toast.LENGTH_SHORT).show();

            } else if (fileResult == 0) {
                Toast.makeText(CoachEditDetialsActivity.this, "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            } else {
                Toast.makeText(CoachEditDetialsActivity.this, "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            }
        }
    }
    private class WebServiceChangePass extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String oldpass;
        String newpass;
        String result;
        Dialog dialog;

        public WebServiceChangePass(String o,String n) {
            oldpass=o;
            newpass=n;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog = new Dialog(CoachEditDetialsActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog.findViewById(R.id.logo);

            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.ChangePass(App.isInternetOn(),idCoach,oldpass,newpass);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {
                if (result.equals("OK")||result.equals("Ok")) {

                    Toast.makeText(CoachEditDetialsActivity.this, "با موفقیت به روز رسانی شد", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CoachEditDetialsActivity.this, "ناموفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(CoachEditDetialsActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }

        }

    }
    @Override
    public void onStop() {
        super.onStop();

        if (callBackFileDetails != null)
            if (callBackFileDetails.getStatus() == AsyncTask.Status.RUNNING)
                callBackFileDetails.cancel(true);
    }

}
