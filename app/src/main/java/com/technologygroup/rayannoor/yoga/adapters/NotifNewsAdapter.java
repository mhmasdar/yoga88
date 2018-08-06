package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Notification.NewsDetailsActivity;
import com.technologygroup.rayannoor.yoga.Notification.NotifDetailsActivity;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;
import com.technologygroup.rayannoor.yoga.R;

import net.cachapa.expandablelayout.ExpandableLayout;

public class NotifNewsAdapter extends RecyclerView.Adapter<NotifNewsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public NotifNewsAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NotifNewsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_notif_news_list, parent, false);
        NotifNewsAdapter.myViewHolder holder = new NotifNewsAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NotifNewsAdapter.myViewHolder holder, int position) {
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

        private TextView txvTitle;
        private TextView txtBody;
        private ImageView imgTitle;

        myViewHolder(View itemView) {
            super(itemView);

            txvTitle = (TextView) itemView.findViewById(R.id.txvTitle);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);
            imgTitle = (ImageView) itemView.findViewById(R.id.imgTitle);

        }
    }
}
