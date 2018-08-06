package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;

import net.cachapa.expandablelayout.ExpandableLayout;

public class RefereeEducationAdapter extends RecyclerView.Adapter<RefereeEducationAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public RefereeEducationAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RefereeEducationAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_education_list, parent, false);
        RefereeEducationAdapter.myViewHolder holder = new RefereeEducationAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RefereeEducationAdapter.myViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtEducationTitle;
        private ImageView imgEducation;
        private ImageView imgDelete;
        private ImageView imgEdit;


        myViewHolder(View itemView) {
            super(itemView);
            txtEducationTitle = (TextView) itemView.findViewById(R.id.txtEducationTitle);
            imgEducation = (ImageView) itemView.findViewById(R.id.imgEducation);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);

        }
    }
}
