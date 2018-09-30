package com.technologygroup.rayannoor.yoga.Teaches;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Coaches.CoachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.Models.TeachTextImage;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import java.util.ArrayList;
import java.util.List;

public class CoachTeachDetailsActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private TextView txtTitle;
    private LinearLayout[] lyt = new LinearLayout[10];
    private TextView[] txt = new TextView[10];
    private ImageView[] img = new ImageView[10];
    private TextView lytCoachProfile;
    private TextView txtCoachName;
    private ImageView imgCoach;

    private int visibleLyts = 0;
    private int id, idRow;
    private String Title, Images, Body, imgPersonal,coachName;
    private List<String> selectedImgName = new ArrayList<>();
    private List<String> bodyList = new ArrayList<>();
    private List<TeachTextImage> list;
    private CoachModel coachModel;
    int coachID;
    WebServiceCoachInfo webServiceCoachInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_teach_details);
        initView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id = getIntent().getIntExtra("ID",-1);
        coachName = getIntent().getStringExtra("coachName");
        coachID = getIntent().getIntExtra("coachID",-1);
        list = new ArrayList<>();
        WebServiceList webServiceList=new WebServiceList();
        webServiceList.execute();


//        lytCoachProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(CoachTeachDetailsActivity.this, CoachDetailsActivity.class);
//                //intent.putExtra("idUser", currentObj.id);
////                intent.putExtra("calledFromPanel", false);
//
//                startActivity(intent);
//            }
//        });

    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        lyt[0] = (LinearLayout) findViewById(R.id.lyt1);
        txt[0] = (TextView) findViewById(R.id.txt1);
        img[0] = (ImageView) findViewById(R.id.img1);
        lyt[1] = (LinearLayout) findViewById(R.id.lyt2);
        txt[1] = (TextView) findViewById(R.id.txt2);
        img[1] = (ImageView) findViewById(R.id.img2);
        lyt[2] = (LinearLayout) findViewById(R.id.lyt3);
        txt[2] = (TextView) findViewById(R.id.txt3);
        img[2] = (ImageView) findViewById(R.id.img3);
        lyt[3] = (LinearLayout) findViewById(R.id.lyt4);
        txt[3] = (TextView) findViewById(R.id.txt4);
        img[3] = (ImageView) findViewById(R.id.img4);
        lyt[4] = (LinearLayout) findViewById(R.id.lyt5);
        txt[4] = (TextView) findViewById(R.id.txt5);
        img[4] = (ImageView) findViewById(R.id.img5);
        lyt[5] = (LinearLayout) findViewById(R.id.lyt6);
        txt[5] = (TextView) findViewById(R.id.txt6);
        img[5] = (ImageView) findViewById(R.id.img6);
        lyt[6] = (LinearLayout) findViewById(R.id.lyt7);
        txt[6] = (TextView) findViewById(R.id.txt7);
        img[6] = (ImageView) findViewById(R.id.img7);
        lyt[7] = (LinearLayout) findViewById(R.id.lyt8);
        txt[7] = (TextView) findViewById(R.id.txt8);
        img[7] = (ImageView) findViewById(R.id.img8);
        lyt[8] = (LinearLayout) findViewById(R.id.lyt9);
        txt[8] = (TextView) findViewById(R.id.txt9);
        img[8] = (ImageView) findViewById(R.id.img9);
        lyt[9] = (LinearLayout) findViewById(R.id.lyt10);
        txt[9] = (TextView) findViewById(R.id.txt10);

        img[9] = (ImageView) findViewById(R.id.img10);
        txtCoachName = (TextView) findViewById(R.id.txtCoachName);
        //lytShare = (LinearLayout) findViewById(R.id.lytShare);
       // teachDetailsSharing = (ImageView) findViewById(R.id.teach_details_sharing);
       // lytLast = (LinearLayout) findViewById(R.id.lytLast);
       // lytNext = (LinearLayout) findViewById(R.id.lytNext);
    }

    private class WebServiceCoachInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            coachModel = new CoachModel();


            dialog = new Dialog(CoachTeachDetailsActivity.this);
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

            coachModel = webService.getCoachInfo(App.isInternetOn(), idRow);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (coachModel != null) {

                Intent intent = new Intent(CoachTeachDetailsActivity.this , CoachDetailsActivity.class);
//                Toast.makeText(view.getContext(), holder.lName+"", Toast.LENGTH_LONG).show();
                intent.putExtra("fName", coachModel.fName);
                intent.putExtra("Email", coachModel.Email);
                intent.putExtra("Instagram", coachModel.Instagram);
                intent.putExtra("lName", coachModel.lName);
                intent.putExtra("Telegram", coachModel.Telegram);
//                intent.putExtra("Img", coachModel.Img);
                intent.putExtra("id", coachModel.id);
                intent.putExtra("idCity", coachModel.idCity);
//                intent.putExtra("idCurrentPlan", coachModel.idCurrentPlan);
                intent.putExtra("like", coachModel.like);
//                intent.putExtra("lastUpdate", coachModel.lastUpdate);
                intent.putExtra("Mobile", coachModel.Mobile);
                intent.putExtra("natCode", coachModel.natCode);
                intent.putExtra("Rate", coachModel.Rate);
                intent.putExtra("Gender", coachModel.Gender);
                intent.putExtra("Rate", coachModel.Rate);
                intent.putExtra("Gender", coachModel.Gender);
                intent.putExtra("City", coachModel.City);
                intent.putExtra("State", coachModel.State);
                startActivity(intent);

            } else {

                Toast.makeText(CoachTeachDetailsActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }


    private class WebServiceList extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            list = webService.getMoves(App.isInternetOn(), id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            settexts();

        }

    }
    public void settexts()
    {
        txtCoachName.setText(coachName);
        for(int i=0;i<list.size();i++)
        {

            txt[i].setText(list.get(i).Text);
            if (list.get(i).Image != null)
                if (!list.get(i).Image.equals("") && !list.get(i).Image.equals("null"))
                    Glide.with(this).load(App.imgAddr + list.get(i).Image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img[i]);
            lyt[i].setVisibility(View.VISIBLE);

        }
        txtTitle.setText(list.get(0).Title);
    }
    public void gotoprofile(View v)
    {
        Intent intent = new Intent(CoachTeachDetailsActivity.this, CoachDetailsActivity.class);
        intent.putExtra("idUser",coachID);
        startActivity(intent);
    }
    @Override
    public void onStop() {
        super.onStop();

        if (webServiceCoachInfo != null)
            if (webServiceCoachInfo.getStatus() == AsyncTask.Status.RUNNING)
                webServiceCoachInfo.cancel(true);
    }
}
