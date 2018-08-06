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

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;



    public ChartAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_chart_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.lyt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.expanableLayout1.isExpanded()) {
                    holder.expanableLayout1.setExpanded(true);
                    holder.img1.animate().rotation(180).setDuration(700).start();
                } else {
                    holder.expanableLayout1.setExpanded(false);
                    holder.img1.animate().rotation(0).setDuration(700).start();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lyt1;
        private TextView txtTitle;
        private ImageView img1;
        private ExpandableLayout expanableLayout1;
        private TextView txtName;
        private ImageView image;


        myViewHolder(View itemView) {
            super(itemView);

            lyt1 = (LinearLayout) itemView.findViewById(R.id.lyt1);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            expanableLayout1 = (ExpandableLayout) itemView.findViewById(R.id.expanableLayout1);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
