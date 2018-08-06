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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.GalleryModel;
import com.technologygroup.rayannoor.yoga.Models.GymCoachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.imageActivity;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/7/2018.
 */

public class GymImageAdapter extends RecyclerView.Adapter<GymImageAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private boolean calledFromPanel = false;
    private static int idGym;
    private List<GalleryModel> list;
    GymServiceActivity activity;

    public GymImageAdapter(Context context, List<GalleryModel> list, int idGym, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idGym = idGym;
        this.calledFromPanel = calledFromPanel;
        activity = (GymServiceActivity) context;
    }

    @Override
    public GymImageAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_image, parent, false);
        GymImageAdapter.myViewHolder holder = new GymImageAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymImageAdapter.myViewHolder holder, int position) {

        final GalleryModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentObj.img != null) {
                    if (!currentObj.img.equals("") && !currentObj.img.equals("null")) {

                        GymServiceActivity activity = (GymServiceActivity) context;
                        Intent intent = new Intent(activity, imageActivity.class);
                        intent.putExtra("ImgName", currentObj.img);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txtBody;
        private int position;
        private GalleryModel current;

        myViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);
        }
        private void setData(GalleryModel current, int position) {

            if (current.img != null)
                if (!current.img.equals("") && !current.img.equals("null"))
                    Glide.with(context).load(App.imgAddr + current.img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);

            this.position = position;
            this.current = current;

        }
    }
}
