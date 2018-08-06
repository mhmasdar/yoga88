package com.technologygroup.rayannoor.yoga.Notification;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.technologygroup.rayannoor.yoga.Models.CoachResumeModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.adapters.CoachResumeAdapter;
import com.technologygroup.rayannoor.yoga.adapters.NotifNewsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    private LinearLayout lytMain;
    private ShimmerRecyclerView Recycler;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;
    private Button btnTryAgain;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        lytMain = (LinearLayout) view.findViewById(R.id.lytMain);
        Recycler = (ShimmerRecyclerView) view.findViewById(R.id.Recycler);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) view.findViewById(R.id.lytEmpty);
        btnTryAgain = (Button) view.findViewById(R.id.btnTryAgain);


        setUpRecyclerView();

        return view;
    }


    private void setUpRecyclerView(){
        NotifNewsAdapter adapter = new NotifNewsAdapter(getActivity());
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

}
