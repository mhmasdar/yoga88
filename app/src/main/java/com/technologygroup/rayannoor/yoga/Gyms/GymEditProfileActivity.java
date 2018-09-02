package com.technologygroup.rayannoor.yoga.Gyms;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.MainActivity;
import com.technologygroup.rayannoor.yoga.Models.GymModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;

public class GymEditProfileActivity extends AppCompatActivity {

    private LinearLayout header;
    private RelativeLayout relativeBack;
    private ImageView imgBack;
    private LinearLayout lytChangePassword;
    private LinearLayout lytLogOut;
    private EditText edtName;
    private EditText edtAddress;
    private EditText edtNatCode;
    private LinearLayout lytMobile;
    private EditText edtMobile;
    private LinearLayout lytTelegram;
    private EditText edtTelegram;
    private EditText edtFName;
    private LinearLayout lytInstagram;
    private EditText edtInstagram;
    private EditText edtLName;
    private LinearLayout lytEmail;
    private EditText edtEmail;
    private RoundedImageView imgProfile;
    private LinearLayout lytEditInformation;
    private Dialog dialogForget;

    private int idCoach;
    private boolean flagImgChanged = false;
   

    public boolean flagPermission = false;
    private static final int PICK_FILE_REQUEST = 4;
    private String selectedFilePath, selectedImgName = "";

    private SharedPreferences prefs;
   //WebServiceCallBackEdit callBackFileDetails;

    GymModel current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_edit_profile);
        lytLogOut = (LinearLayout) findViewById(R.id.lytLogOut);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lytLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(GymEditProfileActivity.this);
                alert.setTitle("خروج");
                alert.setMessage("آیا مطمئن هستید؟");
                alert.setCancelable(false);
                alert.setIcon(R.drawable.ic_exit);
                alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK &&
                                event.getAction() == KeyEvent.ACTION_UP &&
                                !event.isCanceled()) {
                            dialog.cancel();
                            return true;
                        }
                        return false;
                    }
                });
                alert.setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        prefs = getSharedPreferences("User", 0);
                        prefs.edit().clear().apply();

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("isFirstRun", false);
                        editor.apply();
                        Intent intent = new Intent(GymEditProfileActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                });
                alert.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alert.create().show();
            }
        });
        initView();
        setView();

    }
    private void initView() {
        header = (LinearLayout) findViewById(R.id.header);
        relativeBack = (RelativeLayout) findViewById(R.id.relative_back);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        lytChangePassword = (LinearLayout) findViewById(R.id.lytChangePassword);
        lytLogOut = (LinearLayout) findViewById(R.id.lytLogOut);
        edtFName = (EditText) findViewById(R.id.edtFName);
        lytMobile = (LinearLayout) findViewById(R.id.lytMobile);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtLName = (EditText) findViewById(R.id.edtLName);
        lytTelegram = (LinearLayout) findViewById(R.id.lytTelegram);
        edtTelegram = (EditText) findViewById(R.id.edtTelegram);
        lytInstagram = (LinearLayout) findViewById(R.id.lytInstagram);
        edtInstagram = (EditText) findViewById(R.id.edtInstagram);
        lytEmail = (LinearLayout) findViewById(R.id.lytEmail);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        imgProfile = (RoundedImageView) findViewById(R.id.imgProfile);
        lytEditInformation = (LinearLayout) findViewById(R.id.lytEditInformation);
    }
    private void setView() {
        current = new GymModel();

        if (getIntent().getStringExtra("CoachImg") != null)
            if (!getIntent().getStringExtra("CoachImg").equals("") && !getIntent().getStringExtra("CoachImg").equals("null")) {
                Glide.with(GymEditProfileActivity.this).load(App.imgAddr + getIntent().getStringExtra("CoachImg")).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgProfile);
                current.ImgName = getIntent().getStringExtra("CoachImg");
            } else
                current.ImgName = "";
        else current.ImgName = "";

        current.Name = getIntent().getStringExtra("CoachName");
        current.Address = getIntent().getStringExtra("GymAddress");
        current.Mobile = getIntent().getStringExtra("CoachMobile");
        current.Telegram = getIntent().getStringExtra("CoachIdTelegram");
        current.Instagram = getIntent().getStringExtra("CoachIdInstagram");
        current.Email = getIntent().getStringExtra("CoachEmail");
        edtFName.setText(getIntent().getStringExtra("CoachName"));
        edtLName.setText(getIntent().getStringExtra("GymAddress"));
        edtMobile.setText(getIntent().getStringExtra("CoachMobile"));
        edtTelegram.setText(getIntent().getStringExtra("CoachIdTelegram"));
        edtInstagram.setText(getIntent().getStringExtra("CoachIdInstagram"));
        edtEmail.setText(getIntent().getStringExtra("CoachEmail"));

    }
}
