package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ChartModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;


public class aboutAppAdapter extends RecyclerView.Adapter<aboutAppAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<String> images;



    public aboutAppAdapter(Context context, List<String> images) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.images = images;
    }

    @Override
    public aboutAppAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_about_images_list, parent, false);
        aboutAppAdapter.myViewHolder holder = new aboutAppAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final aboutAppAdapter.myViewHolder holder, int position) {

        String currentObj = images.get(position);
        Glide.with(context).load(App.imgAddr + currentObj).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;

        myViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }
    }
}