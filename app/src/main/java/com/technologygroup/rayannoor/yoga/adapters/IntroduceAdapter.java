package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.YogaIntroduce.IntroduceDetailsActivity;
import com.technologygroup.rayannoor.yoga.YogaIntroduce.YogaIntroduceActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Mohamad Hasan on 4/14/2018.
 */

public class IntroduceAdapter extends RecyclerView.Adapter<IntroduceAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<TeachesModel> list;
    JSONObject id;


    public IntroduceAdapter(Context context,List<TeachesModel> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
        id=new JSONObject();
        for(int i=0;i<list.size();i++)
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
    public IntroduceAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_article_list, parent, false);
        IntroduceAdapter.myViewHolder holder = new IntroduceAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        holder.harakat.setText(list.get(position).Title);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YogaIntroduceActivity activity = (YogaIntroduceActivity) context;
                Intent intent = new Intent(activity , IntroduceDetailsActivity.class);
                intent.putExtra("IDs",id.toString());
                intent.putExtra("ID",position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        TextView harakat;
        myViewHolder(View itemView) {
            super(itemView);
            harakat = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }
}

