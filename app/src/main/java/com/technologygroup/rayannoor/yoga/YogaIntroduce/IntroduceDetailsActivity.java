package com.technologygroup.rayannoor.yoga.YogaIntroduce;

import android.animation.ObjectAnimator;
import android.app.Dialog;
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
import com.technologygroup.rayannoor.yoga.Gyms.GymDetailsActivity;
import com.technologygroup.rayannoor.yoga.Models.TeachTextImage;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IntroduceDetailsActivity extends AppCompatActivity {


    private RelativeLayout btnBack;
    private TextView txtTitle;
    private LinearLayout[] lyt;
    private TextView[] txt;
    private ImageView[] img;
    private LinearLayout lytShare;
    private ImageView teachDetailsSharing;
    private LinearLayout lytLast;
    private LinearLayout lytNext;
    private String myjsonid;
    private JSONObject IDs;
    private List<Integer> IDList;
    private List<TeachTextImage> list;
    int position;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_details);
        lyt=new LinearLayout[10];
        txt=new TextView[10];
        img=new ImageView[10];
        IDList=new ArrayList<>();
        initView();
        myjsonid = getIntent().getStringExtra("IDs");
        position = getIntent().getIntExtra("ID",-1);
        try {
            IDs=new JSONObject(myjsonid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try
        {
            for(int i=0;i<IDs.length();i++)
            {
                try {
                    IDList.add(IDs.getInt("ID" + i));
                }
                catch (NullPointerException e)
                {
                    break;
                }
            }
        }

        catch (JSONException e)
        {

        }

        list = new ArrayList<>();
        WebServiceList webServiceList=new WebServiceList();
        webServiceList.execute();
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
        lytShare = (LinearLayout) findViewById(R.id.lytShare);
        teachDetailsSharing = (ImageView) findViewById(R.id.teach_details_sharing);
        lytLast = (LinearLayout) findViewById(R.id.lytLast);
        lytLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position-1 > 0) {
                    position=position-1;
                    WebServiceList webServiceList=new WebServiceList();
                    webServiceList.execute();
                }
            }
        });
        lytNext = (LinearLayout) findViewById(R.id.lytNext);
        lytNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position+1 < IDList.size()) {
                    position=position+1;
                    WebServiceList webServiceList=new WebServiceList();
                    webServiceList.execute();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void settexts()
    {
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

    private class WebServiceList extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            dialog = new Dialog(IntroduceDetailsActivity.this);
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

            list = webService.getMoves(App.isInternetOn(), IDList.get(position));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            settexts();

        }

    }
}
