package com.technologygroup.rayannoor.yoga.NavigationMenu;


import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.navigationMenuModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;


/**
 * A simple {@link Fragment} subclass.
 */
public class hameganiFragment4 extends Fragment {


    private Dialog dialog1;
    private TextView txt;
    private LinearLayout lytMain;
    private LinearLayout lytDisconnect;
    private Button btnTryAgain;
    private LinearLayout lytEmpty;

    private getAbout about;

    public hameganiFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hamegani_fragment4, container, false);
        txt = view.findViewById(R.id.txt);
        lytMain = view.findViewById(R.id.lytMain);
        lytDisconnect = view.findViewById(R.id.lytDisconnect);
        btnTryAgain = view.findViewById(R.id.btnTryAgain);
        lytEmpty = view.findViewById(R.id.lytEmpty);

        about = new getAbout();
        about.execute();

        return view;
    }

    private class getAbout extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private navigationMenuModel result;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            dialog1 = new Dialog(getActivity());
            dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog1.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog1.findViewById(R.id.logo);

            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();

            dialog1.setCancelable(false);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.show();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.getAboutUs(App.isInternetOn(), "goals");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog1.dismiss();

            if (result != null) // server responding
            {
                txt.setText(result.value);
            }
            else
            {
                lytMain.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);
            }
        }
    }


}
