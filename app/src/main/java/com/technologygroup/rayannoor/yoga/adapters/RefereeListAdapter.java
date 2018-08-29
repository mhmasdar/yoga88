package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.referees.RefereeDetailsActivity;
import com.technologygroup.rayannoor.yoga.referees.RefereeListActivity;

import java.util.List;

public class RefereeListAdapter extends RecyclerView.Adapter<RefereeListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    List<CoachModel> Reff;

    public RefereeListAdapter(Context context,List<CoachModel> c) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        Reff=c;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_referee_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
       // holder.txtLikeCount.setText(Reff.get(position).like);
       // holder.txtViewCount.setText(Reff.get(position).View);
        holder.txtRefereeName.setText(Reff.get(position).fName+" "+ Reff.get(position).lName);
        holder.txtLikeCount.setText(""+Reff.get(position).like);
        if (Reff.get(position).ImgName != null)
            if (!Reff.get(position).ImgName.equals("") && !Reff.get(position).ImgName.equals("null") && !Reff.get(position).ImgName.equals("DefaultProfileImage.jpg"))
                Glide.with(context).load(App.imgAddr + Reff.get(position).ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imgReferee);
        if(Reff.get(position).IsVerified)
        {
            holder.txtStatus.setText("فعال");
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.green));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefereeListActivity activity = (RefereeListActivity) context;
                Intent intent = new Intent(activity, RefereeDetailsActivity.class);
                intent.putExtra("idReffre",Reff.get(position).id);
//                intent.putExtra("instagram",Reff.get(position).Instagram);
//                intent.putExtra("Email",Reff.get(position).Email);
//                intent.putExtra("Telegram",Reff.get(position).Telegram);
//                intent.putExtra("sorosh",Reff.get(position).sorosh);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Reff.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtStatus;
        private TextView txtLikeCount;
        private TextView txtRefereeName;
        private RoundedImageView imgReferee;


        myViewHolder(View itemView) {
            super(itemView);

            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            txtRefereeName = (TextView) itemView.findViewById(R.id.txtRefereeName);
            imgReferee = (RoundedImageView) itemView.findViewById(R.id.imgReferee);
        }
    }
}
