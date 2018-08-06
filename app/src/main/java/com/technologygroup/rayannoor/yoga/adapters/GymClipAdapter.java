package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;


public class GymClipAdapter extends RecyclerView.Adapter<GymClipAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;


    public GymClipAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public GymClipAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_clip, parent, false);
        GymClipAdapter.myViewHolder holder = new GymClipAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymClipAdapter.myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txtBody;

        myViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);

            //set image darker
            img.setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);

        }
    }
}

