package com.technologygroup.rayannoor.yoga;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;

public class SelectRoleActivity extends AppCompatActivity {

    private Spinner RoleSpinner;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        initView();
    }

    private void initView() {
        RoleSpinner = (Spinner) findViewById(R.id.RoleSpinner);
        btnOk = (Button) findViewById(R.id.btnOk);
    }
}
