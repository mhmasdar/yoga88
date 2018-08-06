package com.technologygroup.rayannoor.yoga;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.UserModel;
import com.technologygroup.rayannoor.yoga.Services.WebService;

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


    SharedPreferences prefs;
    UserModel userModel;
    private int idUser;

    WebServiceCallBackEdit callBackEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        initView();

        prefs = getSharedPreferences("MyPrefs", 0);
        idUser = prefs.getInt("idUser", -1);

        edtFName.setText(prefs.getString("Name", ""));
        edtLName.setText(prefs.getString("lName", ""));
        edtMobile.setText("0" + prefs.getString("Mobile", ""));
        edtEmail.setText(prefs.getString("Email", ""));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

                        prefs = getSharedPreferences("MyPrefs", 0);
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
                if (!edtLastPass.getText().toString().equals("") && !edtLastPass.getText().toString().equals("")) {


                    if (prefs.getString("Password", "").equals(edtLastPass.getText().toString())) {

                        userModel = new UserModel();

                        userModel.id = idUser;
                        userModel.cityid = -1;
                        userModel.Name = prefs.getString("Name", "");
                        userModel.lName = prefs.getString("lName", "");
                        userModel.Mobile = prefs.getString("Mobile", "");
                        userModel.Email = prefs.getString("Email", "");
                        userModel.Password = edtNewPass.getText().toString();

                        callBackEdit = new WebServiceCallBackEdit(userModel);
                        callBackEdit.execute();

                    } else {
                        Toast.makeText(UserprofileActivity.this, "رمز قبلی اشتباه می باشد", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(UserprofileActivity.this, "لطفا فیلد ها را پر کنید", Toast.LENGTH_SHORT).show();
                }
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

            result = webService.editUserInfo(App.isInternetOn(), model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {
                if (Integer.parseInt(result) == 1) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Name", userModel.Name);
                    editor.putString("lName", userModel.lName);
                    editor.putString("Mobile", userModel.Mobile);
                    editor.putString("Email", userModel.Email);
                    editor.putString("Password", userModel.Password);
                    editor.apply();


                    Toast.makeText(UserprofileActivity.this, "با موفقیت به روز رسانی شد", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(UserprofileActivity.this, "ناموفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(UserprofileActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
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
