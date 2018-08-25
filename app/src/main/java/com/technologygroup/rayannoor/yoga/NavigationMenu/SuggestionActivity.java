package com.technologygroup.rayannoor.yoga.NavigationMenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.technologygroup.rayannoor.yoga.R;

public class SuggestionActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtTitle;
    private EditText edtBody;
    private Button btnSendSuggestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        initView();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtBody = (EditText) findViewById(R.id.edtBody);
        btnSendSuggestion = (Button) findViewById(R.id.btnSendSuggestion);
    }
}
