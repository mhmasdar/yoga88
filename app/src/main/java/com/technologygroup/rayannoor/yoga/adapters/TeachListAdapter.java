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
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Teaches.TeachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Teaches.teachsListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Mohamad Hasan on 2/7/2018.
 */

public class TeachListAdapter extends RecyclerView.Adapter<TeachListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<TeachesModel> list;
    JSONObject id;

    public TeachListAdapter(Context context,List<TeachesModel> l)  {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        list=l;
        id=new JSONObject();
        for(int i=0;i<l.size();i++)
        {
            try {
                id.put("ID"+i,list.get(i).id);
            }
            catch (JSONException j)
            {

            }

        }
    }

    @Override
    public TeachListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_teach_list, parent, false);
        TeachListAdapter.myViewHolder holder = new TeachListAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final TeachListAdapter.myViewHolder holder, final int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teachsListActivity activity = (teachsListActivity)context;
                Intent intent = new Intent(activity , TeachDetailsActivity.class);

                intent.putExtra("ID",list.get(position).id);
                context.startActivity(intent);
            }
        });
        if (list.get(position).Images != null)
            if (!list.get(position).Images.equals("") && !list.get(position).Images.equals("null"))
                Glide.with(context).load(App.imgAddr + list.get(position).Images).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imgTeach);
        holder.txtTitle.setText(list.get(position).Title);
//
    }

    @Override
    public int getItemCount() {
        return list.size();
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
