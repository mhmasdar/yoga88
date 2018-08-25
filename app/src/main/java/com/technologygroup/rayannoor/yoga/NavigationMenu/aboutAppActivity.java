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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

public class aboutAppActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private TextView txtAbout;
    private ImageView img1;
    private ImageView img2;
    private LinearLayout lytMain;
    private LinearLayout lytDisconnect;
    private Button btnTryAgain;
    private LinearLayout lytEmpty;
    getAboutUs aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        initView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        aboutUs = new getAboutUs();
        aboutUs.execute();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutUs = new getAboutUs();
                aboutUs.execute();
            }
        });
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        txtAbout = (TextView) findViewById(R.id.txtAbout);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
    }

    private class getAboutUs extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private String result;
        private Dialog dialog1;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog1 = new Dialog(aboutAppActivity.this);
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

            result = webService.getAboutUs(App.isInternetOn(), "aboutapp");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog1.dismiss();

            if (result != null) // server responding
            {
                if (result.equals(""))
                {
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                    lytMain.setVisibility(View.GONE);
                }

                else
                {
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);

                    txtAbout.setText(result);
                }
            }

            else
            {
                lytDisconnect.setVisibility(View.VISIBLE);
                lytEmpty.setVisibility(View.GONE);
                lytMain.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (aboutUs != null)
            if (aboutUs.getStatus() == AsyncTask.Status.RUNNING)
                aboutUs.cancel(true);
    }
}
