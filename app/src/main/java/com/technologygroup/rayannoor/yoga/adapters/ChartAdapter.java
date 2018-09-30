package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ChartModel;
import com.technologygroup.rayannoor.yoga.Models.FAQmodel;
import com.technologygroup.rayannoor.yoga.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class ChartAdapter extends RecyclerView.Adapter<ChartAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<ChartModel> list;



    public ChartAdapter(Context context, List<ChartModel> list) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_chart_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

        ChartModel currentObj = list.get(position);
        holder.setData(currentObj, position);

//        if (!currentObj.title.equals("") || !currentObj.title.equals("null"))
//            holder.txtTitle.setText(currentObj.title);
//
//        if (!currentObj.name.equals("") || !currentObj.name.equals("null"))
//            holder.txtName.setText(currentObj.name);
//
//        if (!currentObj.image.equals("") || !currentObj.image.equals("null"))
//            Glide.with(context).load(App.imgAddr + currentObj.image).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.image);


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
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lyt1;
        private TextView txtTitle;
        private ImageView img1;
        private ExpandableLayout expanableLayout1;
        private TextView txtName;
        private ImageView image;

        private int position;
        private ChartModel current;


        myViewHolder(View itemView) {
            super(itemView);

            lyt1 = (LinearLayout) itemView.findViewById(R.id.lyt1);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            expanableLayout1 = (ExpandableLayout) itemView.findViewById(R.id.expanableLayout1);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            image = (ImageView) itemView.findViewById(R.id.image);
        }


        private void setData(ChartModel current, int position) {

            if (!current.title.equals("") || !current.title.equals("null"))
                txtTitle.setText(current.title);

            if (!current.name.equals("") || !current.name.equals("null"))
                txtName.setText(current.name);

            if (!current.image.equals("") && !current.image.equals("null") && !current.image.equals(null))
                Glide.with(context).load(App.imgAddr + current.image).diskCacheStrategy(DiskCacheStrategy.NONE).into(image);


            this.position = position;
            this.current = current;

        }
    }
}
