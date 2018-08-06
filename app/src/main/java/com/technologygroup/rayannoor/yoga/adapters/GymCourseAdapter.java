package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
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
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.CourseModel;
import com.technologygroup.rayannoor.yoga.Models.GymCoachesModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/9/2018.
 */

public class GymCourseAdapter extends RecyclerView.Adapter<GymCourseAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private boolean calledFromPanel = false;
    private static int idGym;
    private List<CourseModel> list;
    GymServiceActivity activity;
    ClassDate classDate;

    public GymCourseAdapter(Context context, List<CourseModel> list, int idGym, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idGym = idGym;
        this.calledFromPanel = calledFromPanel;
        activity = (GymServiceActivity) context;
        classDate = new ClassDate();
    }

    @Override
    public GymCourseAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_course, parent, false);
        GymCourseAdapter.myViewHolder holder = new GymCourseAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymCourseAdapter.myViewHolder holder, int position) {
        final CourseModel currentObj = list.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCoachName, txtDateTime, txtStartDate, txtEndDate, txtTitle;
        private ImageView imgDelete, imgEdit;

        private int position;
        private CourseModel current;

        myViewHolder(View itemView) {
            super(itemView);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            txtCoachName = (TextView) itemView.findViewById(R.id.txtCoachName);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            txtStartDate = (TextView) itemView.findViewById(R.id.txtStartDate);
            txtEndDate = (TextView) itemView.findViewById(R.id.txtEndDate);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

        }

        private void setData(CourseModel current, int position) {

            if (!calledFromPanel){
                imgDelete.setVisibility(View.INVISIBLE);
                imgEdit.setVisibility(View.INVISIBLE);
            }

            txtTitle.setText(current.Title);
            txtCoachName.setText(current.coachName);
            txtStartDate.setText(classDate.changeDateToString(Integer.parseInt(current.startDate)));
            txtEndDate.setText("تا " + classDate.changeDateToString(Integer.parseInt(current.startDate)));
            txtDateTime.setText(current.Days + " " + current.Times);

            this.position = position;
            this.current = current;

        }

    }
}
