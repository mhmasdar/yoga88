package com.technologygroup.rayannoor.yoga.Notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif_details);
        initView();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
}
