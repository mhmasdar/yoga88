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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.UserModel;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {


    private GradientBackgroundPainter gradientBackgroundPainter;
    private LinearLayout lytRegister;
    private EditText edtFName;
    private EditText edtLName;
    private EditText edtMobile;
    private EditText edtEmail;
    private EditText edtUserName;
    private EditText edtUserPass;
    private CircularProgressButton btnRegister;
    private TextView txtLogin;

    Dialog dialog;
    public static Spinner StateSpinner;
    public static Spinner CitySpinner;
    private int stateNumber = 1;
    private int cityNumber = 1;
    public static TextView textView;
    private SharedPreferences prefs;

    WebServiceCallReg callReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

        //background color change
        final int[] drawables = new int[4];
        drawables[0] = R.drawable.gradient_1;
        drawables[1] = R.drawable.gradient_2;
        drawables[2] = R.drawable.gradient_3;
        drawables[3] = R.drawable.gradient_4;
        gradientBackgroundPainter = new GradientBackgroundPainter(lytRegister, drawables);
        gradientBackgroundPainter.start();


        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtFName.getText().toString().equals("") || !edtLName.getText().toString().equals("") || !edtMobile.getText().toString().equals("") || !edtEmail.getText().toString().equals("") || !edtUserPass.getText().toString().equals("")) {

                    if (edtMobile.getText().toString().length() == 11 && edtMobile.getText().toString().startsWith("0")) {

                        if (edtEmail.getText().toString().contains(".") && edtEmail.getText().toString().contains("@")) {

                            callReg = new WebServiceCallReg();
                            callReg.execute();

                        } else {
                            Toast.makeText(RegisterActivity.this, "ایمیل نامعتبر است", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(RegisterActivity.this, "شماره تلفن صحیح نیست", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private void initView() {
        lytRegister = (LinearLayout) findViewById(R.id.lytRegister);
        edtFName = (EditText) findViewById(R.id.edtFName);
        edtLName = (EditText) findViewById(R.id.edtLName);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        //edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtUserPass = (EditText) findViewById(R.id.edtUserPass);
        btnRegister = (CircularProgressButton) findViewById(R.id.btnRegister);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
    }

    private class WebServiceCallReg extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        UserModel result;
        UserModel userModel;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnRegister.startAnimation();
            webService = new WebService();
            userModel = new UserModel();

            userModel.Name = edtFName.getText().toString();
            userModel.lName = edtLName.getText().toString();
            userModel.Mobile = edtMobile.getText().toString().substring(1);
            userModel.Email = edtEmail.getText().toString();
            userModel.Password = edtUserPass.getText().toString();


        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.userRegister(App.isInternetOn(), userModel);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (result.id > 0) {




                    prefs = getSharedPreferences("User", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("idUser", result.id);
                    editor.putInt("userType", 3);
                    editor.putString("Name", result.Name);
                    editor.putString("lName", result.lName);
                    editor.putString("Mobile", result.Mobile);
                    editor.putString("Email", result.Email);
                    editor.putString("Password", result.Password);
                    editor.apply();

                    // بعد از اتمام عملیات کدهای زیر اجرا شوند
                    Bitmap icon = BitmapFactory.decodeResource(getResources(),
                            R.drawable.ic_ok);
                    btnRegister.doneLoadingAnimation(R.color.green, icon); // finish loading

                    // بستن دیالوگ حتما با تاخیر انجام شود
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //showStateDialog();
                            Intent i = new Intent(RegisterActivity.this, selectSportActivity.class);
                            startActivity(i);
                            finish();

                        }
                    }, 1000);


                } else if (result.id == 0 || result.id == -1 || result.id == -2) {
                    btnRegister.revertAnimation();
                    Toast.makeText(RegisterActivity.this, "ثبت نام نا موفق است", Toast.LENGTH_LONG).show();
                } else if (result.id == -3) {
                    btnRegister.revertAnimation();
                    Toast.makeText(RegisterActivity.this, "شماره تلفن تکراری است", Toast.LENGTH_LONG).show();
                } else {

                    btnRegister.revertAnimation();
                    Toast.makeText(RegisterActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                }

            } else {

                btnRegister.revertAnimation();
                Toast.makeText(RegisterActivity.this, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }



    @Override
    public void onStop() {
        super.onStop();

        if (callReg != null)
            if (callReg.getStatus() == AsyncTask.Status.RUNNING)
                callReg.cancel(true);
    }

}
