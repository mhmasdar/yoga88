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
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Coaches.CoachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Teaches.CoachTeachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Teaches.CoachTeachsActivity;

import java.util.List;

/**
 * Created by Mohamad Hasan on 2/21/2018.
 */

public class TeachCoachesAdapter extends RecyclerView.Adapter<TeachCoachesAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<TeachesModel> list;
    ClassDate classDate;

    private int idCoach;
    private CoachModel coachModel;

    public TeachCoachesAdapter(Context context, List<TeachesModel> list) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        classDate = new ClassDate();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_teach_coach_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final TeachesModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoachTeachsActivity activity = (CoachTeachsActivity) context;
                Intent intent = new Intent(activity, CoachTeachDetailsActivity.class);
                //intent.putExtra("calledToAdd", false);
                intent.putExtra("id", currentObj.id);

                intent.putExtra("Title", currentObj.Title);
                intent.putExtra("Body", currentObj.Body);
                intent.putExtra("Images", currentObj.Images);
                intent.putExtra("coachName", currentObj.Name);
                intent.putExtra("ImagePersonal", currentObj.ImagePersonal);
                context.startActivity(intent);
            }
        });

        holder.txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoachTeachDetailsActivity activity = (CoachTeachDetailsActivity) context;
                Intent intent = new Intent(activity, CoachDetailsActivity.class);
                intent.putExtra("idUser", currentObj.id);
                intent.putExtra("calledFromPanel", false);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView txtDate;
        private TextView txtTitle;
        private TextView txtProfile;
        private TextView txtCoachName;
        private ImageView imgTeach;

        private int position;
        private TeachesModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtProfile = (TextView) itemView.findViewById(R.id.txtProfile);
            txtCoachName = (TextView) itemView.findViewById(R.id.txtCoachName);
            imgTeach = (ImageView) itemView.findViewById(R.id.imgTeach);
        }

        private void setData(TeachesModel current, int position) {

            txtTitle.setText(current.Title);
            if (!current.Date.equals("null"))
                txtDate.setText(current.Date);
            txtCoachName.setText(current.user.Name + " " +current.user.lName);
            this.position = position;
            if (current.Images != null)
                if (!list.get(position).Images.equals("") && !list.get(position).Images.equals("null"))
                    Glide.with(context).load(App.imgAddr + list.get(position).Images).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgTeach);
            this.current = current;

        }



        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtProfile:
                    break;
            }
        }

    }

}
