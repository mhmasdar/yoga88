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

public class RefereeCourseAdapter extends RecyclerView.Adapter<RefereeCourseAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public RefereeCourseAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RefereeCourseAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_course_list, parent, false);
        RefereeCourseAdapter.myViewHolder holder = new RefereeCourseAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RefereeCourseAdapter.myViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
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
