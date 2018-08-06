package com.technologygroup.rayannoor.yoga.Gyms;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.technologygroup.rayannoor.yoga.MainActivity;
import com.technologygroup.rayannoor.yoga.R;

public class GymEditProfileActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private LinearLayout lytLogOut;
    ImageView imgBack;

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

                        prefs = getSharedPreferences("MyPrefs", 0);
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

    }
}
