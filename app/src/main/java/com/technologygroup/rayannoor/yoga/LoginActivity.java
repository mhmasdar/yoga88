package com.technologygroup.rayannoor.yoga;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.UserModel;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {


    private GradientBackgroundPainter gradientBackgroundPainter;
    private LinearLayout lytLogin;
    private Spinner LoginSpinner;
    private EditText edtUserName;
    private EditText edtPass;
    private CircularProgressButton btnLogin;
    private TextView txtForgetPass;
    private TextView txtRegister;
    private SharedPreferences prefs;
    Dialog dialog;
    Dialog dialog1;
    WebServiceCall call;
    private ImageView imgClose;
    private ImageView imgClose1;
    private EditText edtEmail;
    private CircularProgressButton btnOk;
    private CircularProgressButton btnOk1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        //background color change
        final int[] drawables = new int[4];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;
        drawables[3] = R.drawable.gradient_4;
        gradientBackgroundPainter = new GradientBackgroundPainter(lytLogin, drawables);
        gradientBackgroundPainter.start();


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtUserName.getText().toString().equals("") || !edtPass.getText().toString().equals("")) {

                    if (edtUserName.getText().toString().length() == 11 && edtUserName.getText().toString().startsWith("0")) {

                        call = new WebServiceCall();
                        call.execute();

                    } else {
                        Toast.makeText(LoginActivity.this, "شماره تلفن صحیح نیست", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
            }
        });


        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    private void initView() {
        lytLogin = (LinearLayout) findViewById(R.id.lytLogin);
        LoginSpinner = (Spinner) findViewById(R.id.LoginSpinner);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (CircularProgressButton) findViewById(R.id.btnLogin);
        txtForgetPass = (TextView) findViewById(R.id.txtForgetPass);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        imgClose = (ImageView) findViewById(R.id.imgClose);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnOk = (CircularProgressButton) findViewById(R.id.btnOk);
    }

    private void showDialog() {
        dialog1 = new Dialog(LoginActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_forget_pass);
        imgClose1 = (ImageView) dialog1.findViewById(R.id.imgClose);
        edtEmail = (EditText) dialog1.findViewById(R.id.edtEmail);
        btnOk1 = (CircularProgressButton) dialog1.findViewById(R.id.btnOk);
        btnOk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceّForget webServiceّForget=new WebServiceّForget(edtEmail.getText().toString());
                webServiceّForget.execute();
            }
        });
        imgClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog1.dismiss();
            }
        });

        dialog1.setCancelable(true);
        dialog1.setCanceledOnTouchOutside(true);
        dialog1.show();
    }

    private class WebServiceCall extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        UserModel userModel;

        String userName, pass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnLogin.startAnimation();
            webService = new WebService();
            userName = edtUserName.getText().toString().substring(1);
            pass = edtPass.getText().toString();
        }

        @Override
        protected Void doInBackground(Object... params) {

            userModel = webService.userLogin(App.isInternetOn(), userName, pass);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (userModel != null) {

                if (userModel.id > 0) {

                    prefs = getSharedPreferences("User", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("idUser", userModel.id);
                    editor.putInt("SessionKey", userModel.SessionKey);
                    editor.putString("userType",userModel.userType.toString());
                    editor.putString("Name", userModel.Name);
                    editor.putString("lName", userModel.lName);
                    editor.putString("Mobile", userModel.Mobile);
                    editor.putString("Email", userModel.Email);
                    editor.putString("Password", userModel.Password);
                    editor.putString("ProfileImageName", userModel.ProfileImageName);
                    editor.putBoolean("isFirstRun", false);
                    editor.putString("idcity",String.valueOf(userModel.cityid));
                    editor.putString("userType",userModel.userType.toString());
                    editor.apply();

                    // بعد از اتمام عملیات کدهای زیر اجرا شوند
                    Bitmap icon = BitmapFactory.decodeResource(getResources(),
                            R.drawable.ic_ok);
                    btnLogin.doneLoadingAnimation(R.color.green, icon); // finish loading

                    // بستن دیالوگ حتما با تاخیر انجام شود
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //showStateDialog();

                            Intent i = new Intent(LoginActivity.this, selectSportActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            finish();
                        }
                    }, 1000);
                } else {

                    btnLogin.revertAnimation();
                    Toast.makeText(LoginActivity.this, "رمز یا نام کاربری اشتباه است", Toast.LENGTH_LONG).show();
                }

            } else {

                btnLogin.revertAnimation();
                Toast.makeText(LoginActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }
    private class WebServiceّForget extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String phone;
        String result;
        WebServiceّForget(String p)
        {
         phone=p;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnOk1.startAnimation();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.ForgetPass(App.isInternetOn(), phone.substring(1));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (result.equals("OK")||result.equals("Ok")) {
                    // بعد از اتمام عملیات کدهای زیر اجرا شوند
                    Bitmap icon = BitmapFactory.decodeResource(getResources(),
                            R.drawable.ic_ok);
                    btnOk1.doneLoadingAnimation(R.color.green, icon); // finish loading

                    // بستن دیالوگ حتما با تاخیر انجام شود
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //showStateDialog();
                            btnOk1.revertAnimation();
                            dialog1.dismiss();
                        }
                    }, 1000);
                } else {

                    btnOk1.revertAnimation();
                    Toast.makeText(LoginActivity.this, "شماره موبایل اشتباه است", Toast.LENGTH_LONG).show();
                }

            } else {

                btnOk1.revertAnimation();
                Toast.makeText(LoginActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (call != null)
            if (call.getStatus() == AsyncTask.Status.RUNNING)
                call.cancel(true);
    }

}
