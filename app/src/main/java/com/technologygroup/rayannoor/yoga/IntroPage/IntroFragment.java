package com.technologygroup.rayannoor.yoga.IntroPage;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.technologygroup.rayannoor.yoga.MainActivity;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.selectSportActivity;

/**
 * Created by mohamadHasan on 20/07/2017.
 */

public class IntroFragment extends Fragment {

    public static String[] provinces;
    private static final String PAGE = "page";
    int layoutResId;
    private int mPage;
    public static Dialog dialog;
    public static TextView textView;
    public static TextView textView2;
    public static Spinner StateSpinner;
    public static Spinner CitySpinner;
    private int stateNumber = 1;
    private int cityNumber = 1;
    ArrayAdapter<String> spinnerArrayAdapter;
    private SharedPreferences prefs;

    public static IntroFragment newInstance(int page) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        //b.putInt(BACKGROUND_COLOR, backgroundColor);
        b.putInt(PAGE, page);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Select a layout based on the current page

        switch (mPage) {
            case 0:
                layoutResId = R.layout.intro_fragment_layout_1;
                break;
            case 1:
                layoutResId = R.layout.intro_fragment_layout_2;
                break;
            case 2:
                layoutResId = R.layout.intro_fragment_layout_3;
                break;
            case 3:
                layoutResId = R.layout.intro_fragment_layout_7;
                break;
            case 4:
                layoutResId = R.layout.intro_fragment_layout_4;
                break;
            case 5:
                layoutResId = R.layout.intro_fragment_layout_5;
                break;
            default:
                layoutResId = R.layout.intro_fragment_layout_6;
                break;
        }

        // Inflate the layout resource file
        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);

        // Set the current page index as the View's tag (useful in the PageTransformer)
        view.setTag(mPage);


        if (mPage == 6) {
            Button btnGuset = (Button) view.findViewById(R.id.btnGuset);
            btnGuset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showStateDialog();
                    prefs = getContext().getSharedPreferences("MyPrefs", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    // -1 means guest
                    editor.putInt("idUser", -1);
                    // 1 means normal users
                    editor.putInt("userType", -1);

                    Intent i = new Intent(getContext(), selectSportActivity.class);
                    getContext().startActivity(i);
                    getActivity().finish();
                }
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

   }
