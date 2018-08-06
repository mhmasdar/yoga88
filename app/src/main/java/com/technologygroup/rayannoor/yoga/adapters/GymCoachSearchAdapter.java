package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technologygroup.rayannoor.yoga.R;

/**
 * Created by Mohamad Hasan on 3/16/2018.
 */

public class GymCoachSearchAdapter extends RecyclerView.Adapter<GymCoachSearchAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;

    public GymCoachSearchAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public GymCoachSearchAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_search_coach, parent, false);
        GymCoachSearchAdapter.myViewHolder holder = new GymCoachSearchAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymCoachSearchAdapter.myViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        myViewHolder(View itemView) {
            super(itemView);

        }
    }
}