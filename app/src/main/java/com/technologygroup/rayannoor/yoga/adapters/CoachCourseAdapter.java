package com.technologygroup.rayannoor.yoga.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.technologygroup.rayannoor.yoga.Models.CoachCourseModel;
import com.technologygroup.rayannoor.yoga.R;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CoachCourseAdapter extends RecyclerView.Adapter<CoachCourseAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachCourseModel> coachCourses;
    private boolean calledFromPanel;
    private ImageView imgClose;
    private EditText edtCourse;
    private CircularProgressButton btnOk;
    TextView title;
    public static Dialog dialogEdit;
    public CoachCourseAdapter(Context context, List<CoachCourseModel> coachCourses,boolean calledFromPanel) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.coachCourses=coachCourses;
        this.calledFromPanel=calledFromPanel;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_course_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        holder.txtTitle.setText(coachCourses.get(position).title);
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDiolog(coachCourses.get(position));
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position,coachCourses.get(position));
            }
        });
        if(!calledFromPanel)
        {
            holder.lytEdit.setVisibility(View.GONE);
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgEdit.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return coachCourses.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private LinearLayout lytEdit;
        private ImageView imgDelete;
        private ImageView imgEdit;

        myViewHolder(View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            lytEdit = (LinearLayout) itemView.findViewById(R.id.lytEdit);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
        }
    }
    private void EditDiolog(CoachCourseModel coachCourseModel)
    {
        dialogEdit = new Dialog(context);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.dialog_add_coach_course);
        title=(TextView) dialogEdit.findViewById(R.id.title);
        imgClose = (ImageView) dialogEdit.findViewById(R.id.imgClose);
        edtCourse = (EditText) dialogEdit.findViewById(R.id.edtCourse);
        btnOk = (CircularProgressButton) dialogEdit.findViewById(R.id.btnOk);
        title.setText("تغییر نام دوره");
        edtCourse.setText(coachCourseModel.title);
        dialogEdit.setCancelable(true);
        dialogEdit.setCanceledOnTouchOutside(true);
        dialogEdit.show();
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogEdit.dismiss();
            }
        });

    }
    public void removeItem(final int position, final CoachCourseModel current) {


        //alert dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("حذف");
        alert.setMessage("آیا می خواهید حذف شود؟");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.ic_delete);
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP &&
                        !event.isCanceled()) {
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });
        alert.setPositiveButton("بله", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

             //  CoachEducationAdapter.WebServiceCallBackDelete callBack = new CoachEducationAdapter.WebServiceCallBackDelete(current.id, position);
              //  callBack.execute();
            }
        });
        alert.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert.create().show();

    }
}

