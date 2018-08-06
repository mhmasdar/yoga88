package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Models.CommentModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

/**
 * Created by Mohamad Hasan on 2/25/2018.
 */

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    List<CommentModel> list;

    ClassDate classDate;

    public commentsAdapter(Context context, List<CommentModel> list) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        classDate = new ClassDate();
    }

    @Override
    public commentsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comment_list, parent, false);
        commentsAdapter.myViewHolder holder = new commentsAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(commentsAdapter.myViewHolder holder, int position) {

        final CommentModel currentObj = list.get(position);
        holder.setData(currentObj, position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {


        TextView txtDate, txtName, txtCommentBody;

        private int position;
        private CommentModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtDate = itemView.findViewById(R.id.txtDate);
            txtName = itemView.findViewById(R.id.txtName);
            txtCommentBody = itemView.findViewById(R.id.txtCommentBody);

        }

        private void setData(CommentModel current, int position) {

            txtDate.setText(classDate.changeDateToString(current.date));
            txtName.setText(current.name);
            txtCommentBody.setText(current.body);

            this.position = position;
            this.current = current;

        }

    }
}