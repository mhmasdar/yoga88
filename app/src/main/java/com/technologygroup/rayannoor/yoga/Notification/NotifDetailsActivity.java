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
import com.technologygroup.rayannoor.yoga.Coaches.CoachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymProfileActivity;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.referees.RefereeDetailsActivity;

public class NotifDetailsActivity extends AppCompatActivity {

    private ImageView imgShare;
    private ImageView imgBack;
    private TextView txtNotifDate;
    private TextView txtNotifSender;
    private TextView txtNotifTitle;
    private ImageView imgNotifDetails;
    private TextView txtNotifBody;
    private LinearLayout lytGymProfile;
    private TextView txtDetailsReserve;
    private int id;
    private ZanguleModel zanguleModel;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_details);
        id = getIntent().getIntExtra("id", -1);
        initView();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WebServiceCallgetDetail webServiceCallgetDetail=new WebServiceCallgetDetail();
        webServiceCallgetDetail.execute();


        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, zanguleModel.title + "\n" + "آخرین اخبار مربوط به هیات ورزش های همگانی را در ورزشکار پلاس ببین");
                startActivity(Intent.createChooser(share, "به اشتراک گذاري از طريق..."));

            }
        });
    }

    private void initView() {
        imgShare = (ImageView) findViewById(R.id.imgShare);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtNotifDate = (TextView) findViewById(R.id.txtNotifDate);
        txtNotifSender = (TextView) findViewById(R.id.txtNotifSender);
        txtNotifTitle = (TextView) findViewById(R.id.txtNotifTitle);
        imgNotifDetails = (ImageView) findViewById(R.id.imgNotifDetails);
        txtNotifBody = (TextView) findViewById(R.id.txtNotifBody);
        lytGymProfile = (LinearLayout) findViewById(R.id.lytGymProfile);
        txtDetailsReserve = (TextView) findViewById(R.id.txtDetailsReserve);
    }
    private class WebServiceCallgetDetail extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        double rate;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            dialog = new Dialog(NotifDetailsActivity.this);
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
            // dialog.dismiss();
            others();

        }
    }
    public void others()
    {
        txtNotifTitle.setText(zanguleModel.title);
        txtNotifBody.setText(zanguleModel.Body);
        if (zanguleModel.image!= null)
            if (!zanguleModel.image.equals("") && !zanguleModel.image.equals("null"))
                Glide.with(NotifDetailsActivity.this).load(App.imgAddr + zanguleModel.image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgNotifDetails);
        txtNotifDate.setText(zanguleModel.Date);
        txtNotifSender.setText(zanguleModel.user.Name+" "+zanguleModel.user.lName);
        
        lytGymProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
                       
                        if(zanguleModel.user.RoleName.equals("Coach")) {
                            Intent intent = new Intent(NotifDetailsActivity.this, CoachDetailsActivity.class);
                            intent.putExtra("idUser",zanguleModel.user.id);
                            startActivity(intent);
                        }
                        else if(zanguleModel.user.RoleName.equals("Referee")) {
                            Intent intent = new Intent(NotifDetailsActivity.this, RefereeDetailsActivity.class);
                            intent.putExtra("idUser",zanguleModel.user.id);
                            startActivity(intent);
                        }
                        else if(zanguleModel.user.RoleName.equals("Gym")) {
                            Intent intent = new Intent(NotifDetailsActivity.this, GymProfileActivity.class);
                            intent.putExtra("idgym",zanguleModel.user.id);
                            startActivity(intent);
                        }

            }
        });
    }
}
