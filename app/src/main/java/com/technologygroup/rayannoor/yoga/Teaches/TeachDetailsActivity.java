package com.technologygroup.rayannoor.yoga.Teaches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TeachDetailsActivity extends AppCompatActivity {


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
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_details);
        lyt=new LinearLayout[10];
        txt=new TextView[10];
        img=new ImageView[10];
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
                IDList.add(IDs.getInt("ID"+i));
            }
        }
        catch (JSONException e)
        {

        }
        Toast.makeText(this,IDs.toString(),Toast.LENGTH_LONG).show();
        initView();
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
        lytNext = (LinearLayout) findViewById(R.id.lytNext);
    }
    public void setImages()
    {

    }

}
