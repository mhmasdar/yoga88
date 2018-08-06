package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Models.CoachCourseModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

public class CoachCourseAdapter extends RecyclerView.Adapter<CoachCourseAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachCourseModel> coachCourses;
    private boolean calledFromPanel;

    public CoachCourseAdapter(Context context, List<CoachCourseModel> coachCourses,boolean calledFromPanel) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.coachCourses=coachCourses;
        this.calledFromPanel=calledFromPanel;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_course_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.txtTitle.setText(coachCourses.get(position).title);
        if(!calledFromPanel)
        {
            holder.lytEdit.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return coachCourses.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private LinearLayout lytEdit;
        private ImageView imgDelete;
        private ImageView imgEdit;

        myViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lytEdit = (LinearLayout) itemView.findViewById(R.id.lytEdit);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
        }
    }
}

