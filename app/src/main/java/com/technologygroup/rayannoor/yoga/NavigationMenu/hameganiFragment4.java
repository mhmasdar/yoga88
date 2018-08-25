package com.technologygroup.rayannoor.yoga.NavigationMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class hameganiFragment4 extends Fragment {


    public hameganiFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hamegani_fragment4, container, false);
        TextView txt = view.findViewById(R.id.txt);
        return view;
    }

}
