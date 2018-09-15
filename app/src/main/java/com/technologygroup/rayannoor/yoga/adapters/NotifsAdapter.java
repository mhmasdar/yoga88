package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.Notification.NewsDetailsActivity;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/12/2018.
 */

public class NotifsAdapter extends RecyclerView.Adapter<NotifsAdapter.myViewHolder> {
    private List<ZanguleModel> list;
    private Context context;
    private LayoutInflater mInflater;


    public NotifsAdapter(Context context,List<ZanguleModel> l) {
        this.context = context;
        list=l;
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
        holder.txtBody.setText(list.get(position).Body);
        holder.txtTitle.setText(list.get(position).title);
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
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTitle;
        TextView txtTitle;
        TextView txtBody;
        myViewHolder(View itemView) {
            super(itemView);
            imgTitle=itemView.findViewById(R.id.imgTitle);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtBody=itemView.findViewById(R.id.txtBody);

        }
    }
}
