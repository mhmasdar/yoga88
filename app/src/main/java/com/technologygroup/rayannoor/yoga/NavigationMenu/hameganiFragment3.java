package com.technologygroup.rayannoor.yoga.NavigationMenu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.ChartAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class hameganiFragment3 extends Fragment {


    private LinearLayout lytMain;
    private ShimmerRecyclerView RecyclerCoach;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;

    public hameganiFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hamegani_fragment3, container, false);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        RecyclerCoach = (ShimmerRecyclerView) view.findViewById(R.id.RecyclerCoach);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);

        setUpRecyclerView();

        return view;
    }


    private void setUpRecyclerView() {
        ChartAdapter adapter = new ChartAdapter(getActivity());
        RecyclerCoach.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getActivity());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerCoach.setLayoutManager(mLinearLayoutManagerVertical);
    }

}
