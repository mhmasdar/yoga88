package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;


public class GymNotifAdapter extends RecyclerView.Adapter<GymNotifAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public GymNotifAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_notif_list, parent, false);
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


        private TextView txtNotifTitle;
        private TextView txtNotifBody;
        private ImageView imgNotif;
        private ImageView imgDelete;
        private ImageView imgEdit;
        private TextView txtNotifDate;


        myViewHolder(View itemView) {
            super(itemView);

            txtNotifTitle = (TextView) itemView.findViewById(R.id.txtNotifTitle);
            txtNotifBody = (TextView) itemView.findViewById(R.id.txtNotifBody);
            imgNotif = (ImageView) itemView.findViewById(R.id.imgNotif);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            txtNotifDate = (TextView) itemView.findViewById(R.id.txtNotifDate);
        }
    }
}
