package com.technologygroup.rayannoor.yoga.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Coaches.CoachServicesActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachResumeModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

public class RefereeResumeAdapter extends RecyclerView.Adapter<RefereeResumeAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachResumeModel> list;
    private String result;
    private Dialog dialog;
    public static Dialog dialogEdit;
    private int position;
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";
    String date;
    boolean startDateFlag = false, finishDateFlag = false;


    private static int idCoach;
    CoachServicesActivity activity;
    private boolean calledFromPanel = false;

    public RefereeResumeAdapter(Context context, List<CoachResumeModel> list, int idCoach, boolean calledFromPanel) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.idCoach = idCoach;
        this.calledFromPanel = calledFromPanel;
        this.list=list;
//        activity = (CoachServicesActivity) context;
    }

    @Override
    public RefereeResumeAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_resume_list, parent, false);
        RefereeResumeAdapter.myViewHolder holder = new RefereeResumeAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RefereeResumeAdapter.myViewHolder holder, int position) {
        final CoachResumeModel currentObj = list.get(position);
        holder.setData(currentObj, position);
      //  holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtResumeTitle;
        private TextView txtStartDate;
        private TextView txtEndDate;
        private ImageView imgDelete;
        private ImageView imgEdit;

        myViewHolder(View itemView) {
            super(itemView);
            txtResumeTitle = (TextView) itemView.findViewById(R.id.txtResumeTitle);
            txtStartDate = (TextView) itemView.findViewById(R.id.txtStartDate);
            txtEndDate = (TextView) itemView.findViewById(R.id.txtEndDate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
        private void setData(CoachResumeModel current, int position) {

            if (!calledFromPanel){
                imgEdit.setVisibility(View.INVISIBLE);
                imgDelete.setVisibility(View.INVISIBLE);
            }

            txtResumeTitle.setText(current.Title);
            txtStartDate.setText("تاریخ شروع: " + current.startDate);
            if (current.endDate.equals("") || current.endDate.equals("0"))
                txtEndDate.setText("تاریخ پایان: تاکنون");
            else
                txtEndDate.setText("تاریخ پایان: " + current.endDate);



        }
//        public void setListeners() {
//            Log.i("TAG", "onSetListeners" + position);
//            imgDelete.setOnClickListener(myViewHolder.this);
//            imgEdit.setOnClickListener(myViewHolder.this);
//        }

        // edit and delete an item from recyclerView s

    }

}