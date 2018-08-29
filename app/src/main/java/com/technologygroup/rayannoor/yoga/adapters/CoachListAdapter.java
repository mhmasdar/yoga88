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
import com.technologygroup.rayannoor.yoga.Coaches.CoachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Coaches.CoachListActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;

import java.util.List;

/**
 * Created by Mohamad Hasan on 2/7/2018.
 */

public class CoachListAdapter extends RecyclerView.Adapter<CoachListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachModel> models;



//    public CoachListAdapter(Context context) {
//        this.context = context;
//        mInflater = LayoutInflater.from(context);
//    }

    public CoachListAdapter(Context context, List<CoachModel> models) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.models = models;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        holder.txtCoachName.setText("me");
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        //if (models.get(position).idState == stateNumber && models.get(position).idCity == cityNumber)


        if (models.get(position).ImgName != null)
            if (!models.get(position).ImgName.equals("") && !models.get(position).ImgName.equals("null") &&!models.get(position).ImgName.equals("DefaultProfileImage.jpg"))
                Glide.with(context).load(App.imgAddr + models.get(position).ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imgCoach);
        //glide code for load image by url. It supports offline and online offline: R.id.....


        holder.lName = models.get(position).lName;
        holder.Email = models.get(position).Email;
        holder.fName = models.get(position).fName;
        holder.id = models.get(position).id;
        holder.idCity = models.get(position).idCity;
//        holder.Img = models.get(position).Img;
        holder.Instagram = models.get(position).Instagram;
        holder.Mobile = models.get(position).Mobile;
//        holder.idCurrentPlan = models.get(position).idCurrentPlan;
//        holder.lastUpdate = models.get(position).lastUpdate;
        holder.like = models.get(position).like;
        holder.natCode = models.get(position).natCode;
        holder.Telegram = models.get(position).Telegram;
        holder.Rate = models.get(position).Rate;
        holder.Gender = models.get(position).Gender;
        holder.City = models.get(position).City;
        holder.State = models.get(position).State;



        holder.txtCoachName.setText(models.get(position).fName + " " + models.get(position).lName);
        holder.txtLikeCount.setText(""+models.get(position).like);
        if(models.get(position).IsVerified)
        {
            holder.txtStatus.setText("فعال");
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.green));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoachListActivity activity = (CoachListActivity) context;
                Intent intent = new Intent(activity, CoachDetailsActivity.class);
//                Toast.makeText(view.getContext(), holder.lName+"", Toast.LENGTH_LONG).show();
                intent.putExtra("fName", holder.fName);
                intent.putExtra("Email", holder.Email);
                intent.putExtra("Instagram", holder.Instagram);
                intent.putExtra("lName", holder.lName);
                intent.putExtra("Telegram", holder.Telegram);
                intent.putExtra("Img", holder.Img);
                intent.putExtra("idUser", holder.id);
                intent.putExtra("idCity", holder.idCity);
                intent.putExtra("idCurrentPlan", holder.idCurrentPlan);
                intent.putExtra("like", 27);
                intent.putExtra("lastUpdate", holder.lastUpdate);
                intent.putExtra("Mobile", holder.Mobile);
                intent.putExtra("natCode", holder.natCode);

                intent.putExtra("Gender", holder.Gender);
                intent.putExtra("Rate", 3.5);
                intent.putExtra("Gender", holder.Gender);
                intent.putExtra("City", holder.City);
                intent.putExtra("State", holder.State);
                intent.putExtra("IsVerified", models.get(position).IsVerified);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private String Email;
        private String fName;
        private String Instagram;
        private String lName;
        private String Telegram;
        private String Img;
        private int id;
        private int idCity;
        private int idCurrentPlan;
        private int like;
        private String lastUpdate;
        private String Mobile;
        private String natCode;
        private double Rate;
        private boolean Gender;
        private String City;
        private String State;

        private TextView txtStatus;
        private TextView txtLikeCount;
        private TextView txtCoachName;
        private RoundedImageView imgCoach;


        myViewHolder(View itemView) {
            super(itemView);

            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            txtCoachName = (TextView) itemView.findViewById(R.id.txtCoachName);
            imgCoach = (RoundedImageView) itemView.findViewById(R.id.imgCoach);
        }
    }
}