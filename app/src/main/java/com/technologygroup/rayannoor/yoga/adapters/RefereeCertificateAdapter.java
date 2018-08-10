package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Coaches.CoachServicesActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

public class RefereeCertificateAdapter extends RecyclerView.Adapter<RefereeCertificateAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachHonorModel> list;
    private int idCoach;
    private boolean calledFromPanel;
    CoachServicesActivity activity;


    public RefereeCertificateAdapter(Context context, List<CoachHonorModel> list, int idCoach, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idCoach = idCoach;
        this.calledFromPanel = calledFromPanel;
//        activity = (CoachServicesActivity) context;
    }

    @Override
    public RefereeCertificateAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_certificate_list, parent, false);
        RefereeCertificateAdapter.myViewHolder holder = new RefereeCertificateAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RefereeCertificateAdapter.myViewHolder holder, int position) {

      final CoachHonorModel currentObj = list.get(position);
      holder.setData(currentObj, position);
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

//            if (current.Img != null)
//                if (!current.Img.equals("") && !current.Img.equals("null"))
//                    Glide.with(context).load(App.imgAddr + current.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCertificate);
//
            txtCertificateTitle.setText(current.Title);
             txtCertificateDate.setText(current.Date);
//
            this.position = position;
            this.current = current;

        }
    }

}
