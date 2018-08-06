package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;

public class CoachKaryabAdapter extends RecyclerView.Adapter<CoachKaryabAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public CoachKaryabAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_karyab_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNotifDate;
        private TextView txtNotifTitle;
        private TextView txtNotifBody;


        myViewHolder(View itemView) {
            super(itemView);

            txtNotifDate = (TextView) itemView.findViewById(R.id.txtNotifDate);
            txtNotifTitle = (TextView) itemView.findViewById(R.id.txtNotifTitle);
            txtNotifBody = (TextView) itemView.findViewById(R.id.txtNotifBody);

        }
    }
}

