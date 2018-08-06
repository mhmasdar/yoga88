package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.YogaIntroduce.IntroduceDetailsActivity;
import com.technologygroup.rayannoor.yoga.YogaIntroduce.YogaIntroduceActivity;

/**
 * Created by Mohamad Hasan on 4/14/2018.
 */

public class IntroduceAdapter extends RecyclerView.Adapter<IntroduceAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    TextView harakat;


    public IntroduceAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public IntroduceAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_article_list, parent, false);
        IntroduceAdapter.myViewHolder holder = new IntroduceAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final IntroduceAdapter.myViewHolder holder, int position) {


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YogaIntroduceActivity activity = (YogaIntroduceActivity) context;
                Intent intent = new Intent(activity , IntroduceDetailsActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class myViewHolder extends RecyclerView.ViewHolder {


        myViewHolder(View itemView) {

            super(itemView);
            harakat = (TextView) itemView.findViewById(R.id.txtTitle);
        }
    }
}

