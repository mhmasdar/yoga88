package com.technologygroup.rayannoor.yoga;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Coaches.CoachEditDetialsActivity;
import com.technologygroup.rayannoor.yoga.Models.UserModel;
import com.technologygroup.rayannoor.yoga.Services.FilePath;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class UserprofileActivity extends AppCompatActivity {

    private ImageView imgBack;
    private LinearLayout lytChangePassword;
    private LinearLayout lytLogOut;
    private EditText edtFName;
    private EditText edtLName;
    private EditText edtEmail;
    private LinearLayout lytMobile;
    private EditText edtMobile;
    private RoundedImageView imgProfile;
    private LinearLayout lytEditInformation;
    private Dialog dialogForget;
    public boolean flagPermission = false;
    private static final int PICK_FILE_REQUEST = 4;
    private String selectedFilePath, selectedImgName = "";
    private int idimage;


    SharedPreferences prefs;
    UserModel userModel;
    private int idUser;
    Dialog dialog;

    WebServiceCallBackEdit callBackEdit;
    sendFileDetails fileDetails;
    CallBackFile callBackFile;

    boolean flagCanChange = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                flagPermission = true;
            }
        } else {
            flagPermission = true;
        }
        initView();

        prefs = getSharedPreferences("User", 0);
        idUser = getIntent().getIntExtra("idUser", -1);
        edtFName.setText(prefs.getString("Name", ""));
        edtLName.setText(prefs.getString("lName", ""));
        edtMobile.setText(prefs.getString("Mobile", ""));
        getInfo getp=new getInfo();
        getp.execute();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgProfile.setOnClickListener(imgSelectPicture_click);
        lytLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UserprofileActivity.this);
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

                        Intent intent = new Intent(UserprofileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                if (idUser > 0) {

                    if (!edtFName.getText().toString().equals("") && !edtLName.getText().toString().equals("")) {

                        if (edtMobile.getText().toString().length() == 11 && edtMobile.getText().toString().startsWith("09")) {


                            if (edtEmail.getText().toString().contains(".") && edtEmail.getText().toString().contains("@")) {

                                userModel = new UserModel();

                                userModel.id = idUser;
                                userModel.cityid = -1;
                                userModel.Name = edtFName.getText().toString();
                                userModel.lName = edtLName.getText().toString();
                                userModel.Mobile = edtMobile.getText().toString().substring(1);
                                userModel.Email = edtEmail.getText().toString();
                                userModel.Password = prefs.getString("Password", "");

                                callBackEdit = new WebServiceCallBackEdit(userModel);
                                callBackEdit.execute();

                            } else {
                                Toast.makeText(UserprofileActivity.this, "ایمیل صحیح نیست", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(UserprofileActivity.this, "شماره موبایل صحیح نیست", Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(UserprofileActivity.this, "لطفا نام و نام خانوادگی را وارد کنید", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(UserprofileActivity.this, "فرد مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        lytChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


    }

    private void showDialog() {
        dialogForget = new Dialog(UserprofileActivity.this);
        dialogForget.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForget.setContentView(R.layout.dialog_change_password);
        Button btn_cancel = (Button) dialogForget.findViewById(R.id.btn_cancel);
        Button btnPassSend = (Button) dialogForget.findViewById(R.id.btnPassSend);
        final EditText edtNewPass = (EditText) dialogForget.findViewById(R.id.edtNewPass);
        final EditText edtLastPass = (EditText) dialogForget.findViewById(R.id.edtLastPass);

        dialogForget.setCancelable(true);
        dialogForget.setCanceledOnTouchOutside(true);
        dialogForget.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForget.dismiss();
            }
        });

        btnPassSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!edtLastPass.getText().toString().equals("") && !edtLastPass.getText().toString().equals("")) {
//
//
//                    if (prefs.getString("Password", "").equals(edtLastPass.getText().toString())) {
                        WebServiceChangePass webServiceChangePass = new WebServiceChangePass(edtLastPass.getText().toString(),edtNewPass.getText().toString());
                        webServiceChangePass.execute();

//                    } else {
//                        Toast.makeText(UserprofileActivity.this, "رمز قبلی اشتباه می باشد", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    Toast.makeText(UserprofileActivity.this, "لطفا فیلد ها را پر کنید", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    private void initView() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        lytChangePassword = (LinearLayout) findViewById(R.id.lytChangePassword);
        lytLogOut = (LinearLayout) findViewById(R.id.lytLogOut);
        edtFName = (EditText) findViewById(R.id.edtFName);
        edtLName = (EditText) findViewById(R.id.edtLName);
        lytMobile = (LinearLayout) findViewById(R.id.lytMobile);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        imgProfile = (RoundedImageView) findViewById(R.id.imgProfile);
        lytEditInformation = (LinearLayout) findViewById(R.id.lytEditInformation);
    }

    private class WebServiceCallBackEdit extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        UserModel model;
        String result;
        Dialog dialog;

        public WebServiceCallBackEdit(UserModel model) {
            this.model = model;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog = new Dialog(UserprofileActivity.this);
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

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.EditUserProfile(App.isInternetOn(), model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {
                if (result.equals("OK")||result.equals("Ok")) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Name", userModel.Name);
                    editor.putString("lName", userModel.lName);
                    editor.putString("Mobile", userModel.Mobile);
                    editor.putString("Email", userModel.Email);
                    editor.putString("Password", userModel.Password);
                    editor.apply();
                    Toast.makeText(UserprofileActivity.this, "با موفقیت به روز رسانی شد", Toast.LENGTH_LONG).show();
                    dialogForget.dismiss();

                } else {
                    Toast.makeText(UserprofileActivity.this, "ناموفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(UserprofileActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
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

            dialog = new Dialog(UserprofileActivity.this);
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

            result = webService.ChangePass(App.isInternetOn(),idUser,oldpass,newpass);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {
                if (result.equals("OK")||result.equals("Ok")) {

                    Toast.makeText(UserprofileActivity.this, "با موفقیت به روز رسانی شد", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(UserprofileActivity.this, "ناموفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(UserprofileActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }

        }

    }
    View.OnClickListener imgSelectPicture_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (flagPermission) {

                if (App.isInternetOn()) {

                    if (idUser > 0) {

                        if (flagCanChange)
                            showFileChooser();
                        else
                            Toast.makeText(UserprofileActivity.this, "در حال آپلود تصویر. کمی بعد امتحان کنید", Toast.LENGTH_LONG).show();


                    }
                } else {
                    Toast.makeText(UserprofileActivity.this, "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
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
                    selectedImgName = classDate.getDateTime() + "_" + "c_" + idUser + "." + extension;
                    deleteImage fileDetails = new deleteImage();
                    fileDetails.execute();
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
    private class deleteImage extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String fileResult;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            dialog = new Dialog(UserprofileActivity.this);
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

            try {
                fileResult = webService.deleteImage(App.isInternetOn(), idimage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//
//
//            if (fileResult != null && fileResult.equals("OK")) //file uploaded successfully
//            {
//                fileDetails = new sendFileDetails(idUser);
//                fileDetails.execute();
//            }
//
//            else
//            {
//                dialog.dismiss();
//                Toast.makeText(UserprofileActivity.this, "خطا در ارسال اطلاعات...لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
//            }
            if (fileResult != null) {
                if (fileResult.equals("-2")) {
                    fileDetails = new sendFileDetails(idUser);
                    fileDetails.execute();
                }
                else if (fileResult.equals("OK")) //file uploaded successfully
                {
                    fileDetails = new sendFileDetails(idUser);
                    fileDetails.execute();
                } else {

                    Toast.makeText(UserprofileActivity.this, "خطا در ارسال اطلاعات...لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }else {
                dialog.dismiss();
                Toast.makeText(UserprofileActivity.this, "ارتباط با سرور بر قرار نشد", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class sendFileDetails extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String fileResult;

        int ObjectID;

        sendFileDetails( int ObjectID)
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

            fileResult = webService.sendFileDetails(App.isInternetOn(), selectedImgName, 1, ObjectID);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (fileResult != null && (fileResult.equals("ok")||fileResult.equals("OK"))) //file uploaded successfully
            {
                callBackFile = new CallBackFile();
                callBackFile.execute();
            }

            else
            {
                dialog.dismiss();
                Toast.makeText(UserprofileActivity.this, "خطا در ارسال اطلاعات...لطفا مجددا سعی کنید", Toast.LENGTH_SHORT).show();
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

            flagCanChange = false;
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

            flagCanChange = true;

            if (fileResult == 200) {
//                dialog2.dismiss();
                Toast.makeText(UserprofileActivity.this, "تصویر با موفقیت آپلود شد", Toast.LENGTH_SHORT).show();

            } else if (fileResult == 0) {
                Toast.makeText(UserprofileActivity.this, "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            } else {
                Toast.makeText(UserprofileActivity.this, "متاسفانه تصویر آپلود نشد", Toast.LENGTH_SHORT).show();
//                CallBackFileDelete callBackFileDelete = new CallBackFileDelete();
//                callBackFileDelete.execute();
            }
            dialog.dismiss();
            finish();
        }
    }
    private class getInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String Result;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            Result = webService.getPanelInfo(App.isInternetOn(), idUser);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (Result != null) {
                try {
                    JSONObject panelj = new JSONObject(Result);
                    edtEmail.setText(panelj.getString("Email"));
                    JSONObject imagej = panelj.getJSONObject("ProfileImage");
                    String imageName = imagej.getString("Name");
                    idimage = imagej.getInt("ID");

                    if (imageName != null)
                        if (!imageName.equals("") && !imageName.equals("null"))
                            Glide.with(UserprofileActivity.this).load(App.imgAddr + imageName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();

        if (callBackEdit != null)
            if (callBackEdit.getStatus() == AsyncTask.Status.RUNNING)
                callBackEdit.cancel(true);
    }
}
