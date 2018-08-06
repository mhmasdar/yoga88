package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Teaches.TeachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Teaches.teachsActivity;
import com.technologygroup.rayannoor.yoga.Teaches.teachsListActivity;

/**
 * Created by Mohamad Hasan on 2/7/2018.
 */

public class TeachListAdapter extends RecyclerView.Adapter<TeachListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public TeachListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public TeachListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_teach_list, parent, false);
        TeachListAdapter.myViewHolder holder = new TeachListAdapter.myViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final TeachListAdapter.myViewHolder holder, int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teachsListActivity activity = (teachsListActivity)context;
                Intent intent = new Intent(activity , TeachDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private ImageView imgTeach;

        myViewHolder(View itemView) {

            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgTeach = (ImageView) itemView.findViewById(R.id.imgTeach);
        }
    }
}
