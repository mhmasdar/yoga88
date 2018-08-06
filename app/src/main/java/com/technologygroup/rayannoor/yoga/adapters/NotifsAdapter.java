package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Notification.NewsDetailsActivity;
import com.technologygroup.rayannoor.yoga.Notification.NotifDetailsActivity;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;

/**
 * Created by Mohamad Hasan on 3/12/2018.
 */

public class NotifsAdapter extends RecyclerView.Adapter<NotifsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public NotifsAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_notif_news_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationActivity activity = (notificationActivity) context;
                Intent intent = new Intent(activity, NewsDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }



    class myViewHolder extends RecyclerView.ViewHolder {


        myViewHolder(View itemView) {
            super(itemView);
        }
    }
}
