package com.technologygroup.rayannoor.yoga.NavigationMenu;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class hameganiFragment2 extends Fragment {


    private TextView lytCall;
    private TextView lytAddress;

    public hameganiFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hamegani_fragment2, container, false);
        lytCall = (TextView) view.findViewById(R.id.lytCall);
        lytAddress = (TextView) view.findViewById(R.id.lytAddress);

        lytCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.fromParts("tel", "04135541944", null));
                startActivity(intentCall);
            }
        });

        lytAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "http://maps.google.com/maps?daddr=38.066947, 46.299700";

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(address));
                startActivity(intent);
            }
        });

        return view;
    }

}
