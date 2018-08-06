package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachGymsModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

/**
 * Created by Mohamad Hasan on 2/12/2018.
 */

public class CoachGymsAdapter extends RecyclerView.Adapter<CoachGymsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachGymsModel> list;

    private boolean calledFromPanel = false;

    public CoachGymsAdapter(Context context, List<CoachGymsModel> list, boolean calledFromPanel) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.calledFromPanel = calledFromPanel;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_gym_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final CoachGymsModel currentObj = list.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtGymRate;
        private TextView txtGymLikeCount;
        private TextView txtGymName;
        private ImageView imgGym;

        private int position;
        private CoachGymsModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtGymRate = (TextView) itemView.findViewById(R.id.txtGymRate);
            txtGymLikeCount = (TextView) itemView.findViewById(R.id.txtGymLikeCount);
            txtGymName = (TextView) itemView.findViewById(R.id.txtGymName);
            imgGym = (ImageView) itemView.findViewById(R.id.imgGym);
        }

        private void setData(CoachGymsModel current, int position) {

            if (current.Img != null)
                if (!current.Img.equals("") && !current.Img.equals("null"))
                    Glide.with(context).load(App.imgAddr + current.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgGym);

            txtGymName.setText(current.Name);
            txtGymLikeCount.setText(current.like + "");
            txtGymRate.setText(current.Rate + "");

            this.position = position;
            this.current = current;

        }

    }
}
