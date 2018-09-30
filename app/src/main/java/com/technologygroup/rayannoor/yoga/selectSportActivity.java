package com.technologygroup.rayannoor.yoga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.StateCityFieldModel;
import com.technologygroup.rayannoor.yoga.Services.WebService;

public class selectSportActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private LinearLayout lytMain;
    private TextView txt1;
    private TextView txt2;
    private LinearLayout lyt1;
    private Spinner stateSpinner;
    private LinearLayout lyt2;
    private Spinner citySpinner;
    private LinearLayout lyt3;
    private Spinner sportSpinner;
    private Button btn;
    private int selectedState = 0, selectedCity = 0, selectedField = 0;
    SharedPreferences prefs;
    StateCityFieldModel allthing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sport);
        initView();

        allthing=new StateCityFieldModel();
        prefs = getSharedPreferences("User", 0);

        //چک می کنیم که آیا برنامه برای اولین بار اجرا شده است یا خیر. اگر اولین اجرای برنامه باشد، متن خوش آمدید را نمایش می دهد. در غیر اینصورت، مستقیما موارد اصلی نمایش داده می شوند.
        if (prefs.getBoolean("isFirstRun",true))
        {
            final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_text);
            txtWelcome.startAnimation(anim);
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welcome_text2);
                    txtWelcome.startAnimation(anim2);
                }
            }, 2200);

            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtWelcome.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);
                    Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.select1);
                    Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.select2);
                    Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.select3);
                    Animation anim4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.select4);
                    Animation anim5 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.select5);
                    txt1.startAnimation(anim1);
                    txt2.startAnimation(anim1);
                    lyt1.startAnimation(anim2);
                    lyt2.startAnimation(anim3);
                    lyt3.startAnimation(anim4);
                    btn.startAnimation(anim5);
                }
            }, 3200);
        }

        else
        {
            txtWelcome.setVisibility(View.GONE);
            lytMain.setVisibility(View.VISIBLE);
        }

        WebServiceCallState callState = new WebServiceCallState();
        callState.execute();

        WebServiceCallField callFiled = new WebServiceCallField();
        callFiled.execute();

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //yearName = parent.getItemAtPosition(position).toString();
               selectedState = allthing.State.get(position).id;

                WebServiceCallCity callCity = new WebServiceCallCity();
                callCity.execute();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //yearName = parent.getItemAtPosition(position).toString();
                selectedCity = allthing.City.get(position).id;
                WebServiceCallField callField = new WebServiceCallField();
                callField.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                //yearName = parent.getItemAtPosition(position).toString();
                selectedField = allthing.Field.get(position).id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedCity!=0 && selectedField!=0 && selectedState!=0) {
                    prefs = getSharedPreferences("User", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("idCity", selectedCity);
                    editor.putInt("idState", selectedState);
                    editor.putInt("idField", selectedField);
                    editor.putBoolean("isFirstRun", false);
                    editor.apply();

                    Intent intent = new Intent(selectSportActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(selectSportActivity.this, "یکی از موارد انتخاب نشده است", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void initView() {
        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        lyt1 = (LinearLayout) findViewById(R.id.lyt1);
        stateSpinner = (Spinner) findViewById(R.id.stateSpinner);
        lyt2 = (LinearLayout) findViewById(R.id.lyt2);
        citySpinner = (Spinner) findViewById(R.id.citySpinner);
        lyt3 = (LinearLayout) findViewById(R.id.lyt3);
        sportSpinner = (Spinner) findViewById(R.id.sportSpinner);
        btn = (Button) findViewById(R.id.btn);
    }


    private class WebServiceCallState extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {
            allthing.State= webService.getStates(App.isInternetOn());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (allthing.State != null) {

                if (allthing.State.size() == 0) {
                    Toast.makeText(selectSportActivity.this, "هیچ استانی وجود ندارد", Toast.LENGTH_LONG).show();

                } else {
                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, allthing.spinnerState());
                    // Drop down layout style - list view with radio button
                    dataAdapterCourse.setDropDownViewResource(R.layout.spinner_text);
                    // attaching data adapter to spinner
                    stateSpinner.setAdapter(dataAdapterCourse);

                }


            } else {
                Toast.makeText(selectSportActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }

    private class WebServiceCallCity extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            allthing.City = webService.getCiteies(App.isInternetOn(), selectedState);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (allthing.City != null) {

                if (allthing.City.size() == 0) {
                    Toast.makeText(selectSportActivity.this, "هیچ شهری وجود ندارد", Toast.LENGTH_LONG).show();

                } else {



                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, allthing.spinnerCity());
                    // Drop down layout style - list view with radio button
                    dataAdapterCourse.setDropDownViewResource(R.layout.spinner_text);
                    // attaching data adapter to spinner
                    citySpinner.setAdapter(dataAdapterCourse);

                }


            } else {
                Toast.makeText(selectSportActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }

    private class WebServiceCallField extends AsyncTask<Object, Void, Void> {

        private WebService webService;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            allthing.Field = webService.getFields(App.isInternetOn());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (allthing.Field != null) {

                if (allthing.Field.size() == 0) {
                    Toast.makeText(selectSportActivity.this, "هیچ رشته ای وجود ندارد", Toast.LENGTH_LONG).show();

                } else {



                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, allthing.spinnerField());
                    // Drop down layout style - list view with radio button
                    dataAdapterCourse.setDropDownViewResource(R.layout.spinner_text);
                    // attaching data adapter to spinner
                    sportSpinner.setAdapter(dataAdapterCourse);

                }


            } else {
                Toast.makeText(selectSportActivity.this, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();
            }

        }

    }

    @Override
    public void onBackPressed() {
        if (prefs.getBoolean("isFirstRun",true))
            super.onBackPressed();
        else
        {
            Intent intent = new Intent(selectSportActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
