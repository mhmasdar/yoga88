package com.technologygroup.rayannoor.yoga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Coaches.CoachProfileActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymDetailsActivity;
import com.technologygroup.rayannoor.yoga.referees.RefereeProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectRoleActivity extends AppCompatActivity {

    private Spinner RoleSpinner;
    private Button btnOk;
    private int id;
    private String roles;
    private JSONObject rolej;
    private List<String> rol;
    private String selectedRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        initView();
        id = getIntent().getIntExtra("id", -1);
        roles = getIntent().getStringExtra("roles");
        rol=new ArrayList<>();
        try {
            rolej=new JSONObject(roles);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int j=0;j<5;j++)
        {
            try {
                String r=rolej.getString("Role"+j);
                if(r.equals("Referee"))
                {
                    rol.add("داور");
                }
                if(r.equals("Gym"))
                {
                    rol.add("باشگاه");
                }
                if(r.equals("Coach"))
                {
                    rol.add("مربی");
                }
                if(r.equals("User"))
                {
                    rol.add("کاربر");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,rol);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        RoleSpinner.setAdapter(adapter);
        RoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedRole=RoleSpinner.getSelectedItem().toString();
                Toast.makeText(SelectRoleActivity.this, selectedRole, Toast.LENGTH_SHORT).show();

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }




    private void initView() {
        RoleSpinner = (Spinner) findViewById(R.id.RoleSpinner);
        btnOk = (Button) findViewById(R.id.btnOk);
    }
    public void ok(View v)
    {
        if(selectedRole.equals("داور"))
        {
            Intent intent = new Intent(SelectRoleActivity.this, RefereeProfileActivity.class);
            intent.putExtra("idReffre",id);
            intent.putExtra("calledFromPanel",true);
            startActivity(intent);
        }
        if(selectedRole.equals("باشگاه"))
        {
            Intent intent = new Intent(SelectRoleActivity.this, GymDetailsActivity.class);
            intent.putExtra("idgym",id);
            intent.putExtra("calledFromPanel",true);
            startActivity(intent);
        }
        if(selectedRole.equals("مربی"))
        {
            Intent intent = new Intent(SelectRoleActivity.this, CoachProfileActivity.class);
            intent.putExtra("idUser",id);
            intent.putExtra("calledFromPanel",true);
            startActivity(intent);

        }
        if(selectedRole.equals("کاربر"))
        {
            Intent intent = new Intent(SelectRoleActivity.this, UserprofileActivity.class);
            intent.putExtra("idgym",id);
            intent.putExtra("calledFromPanel",true);
            startActivity(intent);
        }
    }
}
