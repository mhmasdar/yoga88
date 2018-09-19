package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.Notification.NewsDetailsActivity;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

public class NotifNewsAdapter extends RecyclerView.Adapter<NotifNewsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<ZanguleModel> list;



    public NotifNewsAdapter(Context context,List<ZanguleModel> l) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        list=l;
    }

    @Override
    public NotifNewsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_notif_news_list, parent, false);
        NotifNewsAdapter.myViewHolder holder = new NotifNewsAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NotifNewsAdapter.myViewHolder holder, final int position) {
        holder.txtTitle.setText(list.get(position).title);
        holder.txtBody.setText(list.get(position).Body);
        if (list.get(position).image != null)
            if (!list.get(position).image.equals("") && !list.get(position).image.equals("null"))
                Glide.with(context).load(App.imgAddr + list.get(position).image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imgTitle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationActivity activity = (notificationActivity) context;
                Intent intent = new Intent(activity, NewsDetailsActivity.class);
                intent.putExtra("id",list.get(position).id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtBody;
        private ImageView imgTitle;

        myViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);
            imgTitle = (ImageView) itemView.findViewById(R.id.imgTitle);

        }
    }
}
