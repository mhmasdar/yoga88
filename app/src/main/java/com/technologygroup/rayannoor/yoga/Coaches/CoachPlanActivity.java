package com.technologygroup.rayannoor.yoga.Coaches;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.WebActivity;

public class CoachPlanActivity extends AppCompatActivity {


    String result;
    private int idCoachOrGym, idPlan;
    private RelativeLayout btnBack;
    private Button btnBuy;
    WebServicePlan webServicePlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_plan);
        idCoachOrGym = getIntent().getIntExtra("idCoachOrGym", -1);
        idPlan = getIntent().getIntExtra("idPlan", -1);
        initView();

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServicePlan = new WebServicePlan();
                webServicePlan.execute();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        btnBuy = (Button) findViewById(R.id.btnBuy);
    }


    private class WebServicePlan extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog = new Dialog(CoachPlanActivity.this);
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

            result = webService.postPlanId(App.isInternetOn(), idPlan, idCoachOrGym);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {

                if (!result.startsWith("https://www.zarinpal.com/pg/")) {

                    if (Integer.parseInt(result) == -1) {
                        Toast.makeText(CoachPlanActivity.this, "خطای پایگاه داده", Toast.LENGTH_LONG).show();

                    } else if (Integer.parseInt(result) == -2) {
                        Toast.makeText(CoachPlanActivity.this, "ارتباط با درگاه برقرار نشد", Toast.LENGTH_LONG).show();
                    } else if (Integer.parseInt(result) == -3) {
                        Toast.makeText(CoachPlanActivity.this, "عدم وجود کاربر یا طرح موردنظر", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CoachPlanActivity.this, "ناموفق", Toast.LENGTH_LONG).show();

                    }

                } else {

//                    Toast.makeText(CoachPlanActivity.this, "موفق", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CoachPlanActivity.this, WebActivity.class);
                    intent.putExtra("payUrl", result);
                    startActivity(intent);


                }
            } else {

                Toast.makeText(CoachPlanActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (webServicePlan != null)
            if (webServicePlan.getStatus() == AsyncTask.Status.RUNNING)
                webServicePlan.cancel(true);
    }

}
