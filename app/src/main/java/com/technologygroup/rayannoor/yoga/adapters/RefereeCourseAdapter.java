package com.technologygroup.rayannoor.yoga.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
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
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachCourseModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RefereeCourseAdapter extends RecyclerView.Adapter<RefereeCourseAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachCourseModel> coachCourses;
    private boolean calledFromPanel;
    private ImageView imgClose;
    private EditText edtCourse;
    private CircularProgressButton btnOk;
    TextView title;
    public static Dialog dialogEdit;


    public RefereeCourseAdapter(Context context, List<CoachCourseModel> coachCourses,boolean calledFromPanel) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.coachCourses=coachCourses;
        this.calledFromPanel=calledFromPanel;
    }

    @Override
    public RefereeCourseAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_course_list, parent, false);
        RefereeCourseAdapter.myViewHolder holder = new RefereeCourseAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RefereeCourseAdapter.myViewHolder holder, final int position) {
        holder.txtTitle.setText(coachCourses.get(position).title);
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDiolog(coachCourses.get(position),position);
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
    private void EditDiolog(final CoachCourseModel coachCourseModel, final int position)
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
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtCourse.getText().equals(""))
                {
                    CoachCourseModel tmpModel = new CoachCourseModel();
                    tmpModel.id = coachCourseModel.id;
                    tmpModel.title=edtCourse.getText().toString();
                    WebServiceCallBackEdit callBackFileDetails = new WebServiceCallBackEdit(tmpModel, position);
                    callBackFileDetails.execute();
                }
                else
                {
                    Toast.makeText(context, "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
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

                WebServiceCallBackDelete callBack = new WebServiceCallBackDelete(current.id, position);
                callBack.execute();
            }
        });
        alert.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        alert.create().show();

    }
    private class WebServiceCallBackEdit extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        CoachCourseModel model;
        String result;
        int pos;

        public WebServiceCallBackEdit(CoachCourseModel model, int pos) {
            this.model = model;
            this.pos = pos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnOk.startAnimation();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.editCoachCourse(App.isInternetOn(), model);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {
                if (result.equals("OK")) {
                    coachCourses.remove(pos);
                    coachCourses.add(pos, model);
                    notifyDataSetChanged();

                    // بعد از اتمام عملیات کدهای زیر اجرا شوند
                    Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.ic_ok);
                    btnOk.doneLoadingAnimation(R.color.green, icon); // finish loading

                    // بستن دیالوگ حتما با تاخیر انجام شود
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialogEdit.dismiss();
                        }
                    }, 1000);
                    //Toast.makeText(context, "با موفقیت به روز رسانی شد", Toast.LENGTH_LONG).show();
                } else {
                    btnOk.revertAnimation();
                    Toast.makeText(context, "ناموفق", Toast.LENGTH_LONG).show();
                }
            } else {
                btnOk.revertAnimation();
                Toast.makeText(context, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }
    private class WebServiceCallBackDelete extends AsyncTask<Object, Void, Void> {
        private WebService webService;
        private int id, position;
        String result;
        public WebServiceCallBackDelete(int id, int position) {
            this.id = id;
            this.position = position;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.deleteCoachCourse(App.isInternetOn(), id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if (result != null) {

                if (result.equals("OK")) {
                    Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_LONG).show();
                    coachCourses.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "عملیات نا موفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(context, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }
}
