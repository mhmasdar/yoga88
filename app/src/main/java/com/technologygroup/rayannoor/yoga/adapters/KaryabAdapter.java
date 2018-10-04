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
import com.technologygroup.rayannoor.yoga.Coaches.CoachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymProfileActivity;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.Notification.NotifDetailsActivity;
import com.technologygroup.rayannoor.yoga.Notification.notificationActivity;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.referees.RefereeDetailsActivity;

import java.util.List;

public class KaryabAdapter extends RecyclerView.Adapter<KaryabAdapter.myViewHolder> {
    private List<ZanguleModel> list;
    private Context context;
    private LayoutInflater mInflater;



    public KaryabAdapter(Context context,List<ZanguleModel> l) {
        this.context = context;
        list=l;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_karyab_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        holder.txtNotifTitle.setText(list.get(position).title);
        holder.txtNotifBody.setText(list.get(position).Body);
        holder.txtNotifDate.setText(list.get(position).Date);
        holder.txtNotifSender.setText(list.get(position).user.Name+" "+list.get(position).user.lName);
        if (list.get(position).image != null) {
            if (!list.get(position).image.equals("") && !list.get(position).image.equals("null"))
                Glide.with(context).load(App.imgAddr + list.get(position).image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.karyab_image);
            else
                Glide.with(context).load(R.drawable.test_notif).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.karyab_image);
        }
        else
            Glide.with(context).load(R.drawable.test_notif).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.karyab_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationActivity activity = (notificationActivity) context;
                Intent intent = new Intent(activity, NotifDetailsActivity.class);
                intent.putExtra("id",list.get(position).id);
                context.startActivity(intent);
            }
        });
        holder.txtGymDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationActivity activity = (notificationActivity) context;
                if(list.get(position).user.RoleName.equals("Coach")) {
                    Intent intent = new Intent(activity, CoachDetailsActivity.class);
                    intent.putExtra("idUser",list.get(position).user.id);
                    context.startActivity(intent);
                }
                else if(list.get(position).user.RoleName.equals("Referee")) {
                    Intent intent = new Intent(activity, RefereeDetailsActivity.class);
                    intent.putExtra("idUser",list.get(position).user.id);
                    context.startActivity(intent);
                }
                else if(list.get(position).user.RoleName.equals("Gym")) {
                    Intent intent = new Intent(activity, GymProfileActivity.class);
                    intent.putExtra("idgym",list.get(position).user.id);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNotifTitle;
        private TextView txtNotifBody;
        private TextView txtNotifDate;
        private TextView txtNotifSender;
        private TextView txtGymDetails;
        private ImageView karyab_image;

        myViewHolder(View itemView) {
            super(itemView);

            txtNotifTitle = (TextView) itemView.findViewById(R.id.txtNotifTitle);
            txtNotifBody = (TextView) itemView.findViewById(R.id.txtNotifBody);
            txtNotifDate = (TextView) itemView.findViewById(R.id.txtNotifDate);
            txtNotifSender = (TextView) itemView.findViewById(R.id.txtNotifSender);
            txtGymDetails = (TextView) itemView.findViewById(R.id.txtGymDetails);
            karyab_image = (ImageView) itemView.findViewById(R.id.karyab_image);

        }
    }
}

