package com.technologygroup.rayannoor.yoga.Notification;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.imageActivity;
import com.technologygroup.rayannoor.yoga.referees.RefereeDetailsActivity;

public class NewsDetailsActivity extends AppCompatActivity {

    private ImageView imgNews;
    private TextView txtTitle;
    private TextView txtNewsBody;
    private TextView txtDate;
    private LinearLayout lytShare;
    private int id;
    private ZanguleModel zanguleModel;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        id = getIntent().getIntExtra("id", -1);
        initView();
        WebServiceCallgetDetail webServiceCallgetDetail=new WebServiceCallgetDetail();
        webServiceCallgetDetail.execute();


        lytShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, zanguleModel.title + "\n" + "آخرین اخبار مربوط به هیات ورزش های همگانی را در ورزشکار پلاس ببین");
                startActivity(Intent.createChooser(share, "به اشتراک گذاري از طريق..."));

            }
        });


        imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsDetailsActivity.this, imageActivity.class);
                intent.putExtra("ImgName", zanguleModel.image);
                startActivity(intent);

            }
        });
    }

    private void initView() {
        imgNews = (ImageView) findViewById(R.id.imgNews);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtNewsBody = (TextView) findViewById(R.id.txtNewsBody);
        txtDate = (TextView) findViewById(R.id.txtDate);
        lytShare = (LinearLayout) findViewById(R.id.lytShare);
    }
    private class WebServiceCallgetDetail extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        double rate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            dialog = new Dialog(NewsDetailsActivity.this);
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

            // id is for place
            zanguleModel = webService.getZanguleDetail(App.isInternetOn(), id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            others();

        }
        public void others()
        {
            txtTitle.setText(zanguleModel.title);
            txtNewsBody.setText(zanguleModel.Body);
            if (zanguleModel.image!= null)
                if (!zanguleModel.image.equals("") && !zanguleModel.image.equals("null"))
                    Glide.with(NewsDetailsActivity.this).load(App.imgAddr + zanguleModel.image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgNews);
            txtDate.setText(txtDate.getText()+" "+zanguleModel.Date);
        }

    }
}
