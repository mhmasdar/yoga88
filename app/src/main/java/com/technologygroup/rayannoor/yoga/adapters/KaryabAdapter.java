package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Coaches.CoachServicesActivity;
import com.technologygroup.rayannoor.yoga.Notification.NotifDetailsActivity;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.imageActivity;

public class KaryabAdapter extends RecyclerView.Adapter<KaryabAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public KaryabAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_karyab_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationActivity activity = (notificationActivity) context;
                Intent intent = new Intent(activity, NotifDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNotifTitle;
        private TextView txtNotifBody;
        private TextView txtNotifDate;
        private TextView txtNotifSender;
        private TextView txtGymDetails;

        myViewHolder(View itemView) {
            super(itemView);

            txtNotifTitle = (TextView) itemView.findViewById(R.id.txtNotifTitle);
            txtNotifBody = (TextView) itemView.findViewById(R.id.txtNotifBody);
            txtNotifDate = (TextView) itemView.findViewById(R.id.txtNotifDate);
            txtNotifSender = (TextView) itemView.findViewById(R.id.txtNotifSender);
            txtGymDetails = (TextView) itemView.findViewById(R.id.txtGymDetails);
        }
    }
}

