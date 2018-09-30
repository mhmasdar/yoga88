package com.technologygroup.rayannoor.yoga.NavigationMenu;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

public class SuggestionActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtTitle;
    private EditText edtBody;
    private Button btnSendSuggestion;
    private sendSuggestion suggestion;
    private Dialog dialog1;
    private String jsonRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        initView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSendSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtBody.equals(""))
                    Toast.makeText(getApplicationContext() , "لطفا متن پیام را وارد کنید" , Toast.LENGTH_LONG).show();
                else if (edtEmail.equals(""))
                    Toast.makeText(getApplicationContext() , "لطفا ایمیل خود را وارد کنید" , Toast.LENGTH_LONG).show();
                else if (edtTitle.equals(""))
                    Toast.makeText(getApplicationContext() , "لطفا عنوان پیام را وارد کنید" , Toast.LENGTH_LONG).show();
                else
                {
                    jsonRequest = "{\"SenderName\":\""+ edtName.getText().toString() +"\",\"SenderEmail\":\""+ edtEmail.getText().toString() +"\",\"Title\":\""+edtTitle.getText().toString()+"\",\"Text\":\""+ edtBody.getText().toString() +"\"}";

                    suggestion = new sendSuggestion();
                    suggestion.execute();
                }
            }
        });
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtBody = (EditText) findViewById(R.id.edtBody);
        btnSendSuggestion = (Button) findViewById(R.id.btnSendSuggestion);
    }

    private class sendSuggestion extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private String result;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            dialog1 = new Dialog(SuggestionActivity.this);
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog1.findViewById(R.id.logo);

            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();

            dialog1.setCancelable(false);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.show();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.sendSuggestion(App.isInternetOn(), jsonRequest);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog1.dismiss();

            if (result != "-1") // server responding
            {
                Toast.makeText(getApplicationContext() , "پیام شما با موفقیت ارسال شد" , Toast.LENGTH_LONG).show();
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext() , "خطا در ارسال پیام! لطفا مجددا سعی کنید" , Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (suggestion != null)
            if (suggestion.getStatus() == AsyncTask.Status.RUNNING)
                suggestion.cancel(true);
    }
}
