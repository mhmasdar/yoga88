package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.technologygroup.rayannoor.yoga.Coaches.CoachServicesActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.imageActivity;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/8/2018.
 */

public class GymHonourAdapter extends RecyclerView.Adapter<GymHonourAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private boolean calledFromPanel = false;
    private static int idGym;
    private List<CoachHonorModel> list;
    GymServiceActivity activity;
    ClassDate classDate;

    public GymHonourAdapter(Context context, List<CoachHonorModel> list, int idGym, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idGym = idGym;
        this.calledFromPanel = calledFromPanel;
        activity = (GymServiceActivity) context;
        classDate = new ClassDate();
    }

    @Override
    public GymHonourAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_honour, parent, false);
        GymHonourAdapter.myViewHolder holder = new GymHonourAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymHonourAdapter.myViewHolder holder, final int position) {
        final CoachHonorModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.imgCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GymServiceActivity activity = (GymServiceActivity) context;
                Intent intent = new Intent(activity, imageActivity.class);
                intent.putExtra("ImgName", list.get(position).ImgName);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                if (currentObj.Img != null) {
//                    if (!currentObj.Img.equals("") && !currentObj.Img.equals("null")) {
//
//                        GymServiceActivity activity = (GymServiceActivity) context;
//                        Intent intent = new Intent(activity, imageActivity.class);
//                        intent.putExtra("ImgName", currentObj.Img);
//                        context.startActivity(intent);
//                    } else {
//                        Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCertificateTitle;
        private ImageView imgDelete;
        private ImageView imgEdit;
        private ImageView imgCertificate;
        private TextView txtCertificateDate;

        private int position;
        private CoachHonorModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtCertificateTitle = (TextView) itemView.findViewById(R.id.txtCertificateTitle);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            imgCertificate = (ImageView) itemView.findViewById(R.id.imgCertificate);
            txtCertificateDate = (TextView) itemView.findViewById(R.id.txtCertificateDate);
        }

        private void setData(CoachHonorModel current, int position) {

            if (!calledFromPanel){
                imgEdit.setVisibility(View.INVISIBLE);
                imgDelete.setVisibility(View.INVISIBLE);
            }

            if (current.ImgName != null)
                if (!current.ImgName.equals("") && !current.ImgName.equals("null"))
                    Glide.with(context).load(App.imgAddr + current.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCertificate);
            txtCertificateTitle.setText(current.Title);
            txtCertificateDate.setText(current.Date);
            this.position = position;
            this.current = current;

        }

    }
}

