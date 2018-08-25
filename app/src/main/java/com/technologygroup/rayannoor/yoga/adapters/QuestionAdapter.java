package com.technologygroup.rayannoor.yoga.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Models.FAQmodel;
import com.technologygroup.rayannoor.yoga.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<FAQmodel> list;



    public QuestionAdapter(Context context, List<FAQmodel> list) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_question_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

        final FAQmodel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.expanableLayout1.isExpanded()) {
                    holder.expanableLayout1.setExpanded(true);
                } else {
                    holder.expanableLayout1.setExpanded(false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {


        private TextView txtTitle;
        private TextView txtQuestion;
        private ExpandableLayout expanableLayout1;
        private int position;
        private FAQmodel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtQuestion = (TextView) itemView.findViewById(R.id.txtQuestion);
            expanableLayout1 = (ExpandableLayout) itemView.findViewById(R.id.expanableLayout1);
        }

        private void setData(FAQmodel current, int position) {

            if (!current.question.equals(""))
                txtTitle.setText(current.question);

            if (!current.answer.equals(""))
                txtQuestion.setText(current.answer);


            this.position = position;
            this.current = current;

        }

    }
}
