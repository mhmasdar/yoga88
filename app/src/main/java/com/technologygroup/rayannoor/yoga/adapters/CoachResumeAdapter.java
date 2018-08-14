package com.technologygroup.rayannoor.yoga.adapters;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Coaches.CoachServicesActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachResumeModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Mohamad Hasan on 2/13/2018.
 */

public class CoachResumeAdapter extends RecyclerView.Adapter<CoachResumeAdapter.myViewHolder> implements DatePickerDialog.OnDateSetListener {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachResumeModel> list;
    private String result;
    private Dialog dialog;
    public static Dialog dialogEdit;


    // dialog add content
    EditText edtTitle, edtStartDate, edtEndDate;
    CheckBox checkContinue;
    ImageView imgClose;
    CircularProgressButton btnOk;
    TextView txtWindowTitle;


    //relates to date and time picker
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";
    String date;
    boolean startDateFlag = false, finishDateFlag = false;


    private static int idCoach;
    CoachServicesActivity activity;
    private boolean calledFromPanel = false;

    public CoachResumeAdapter(Context context, List<CoachResumeModel> list, int idCoach, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idCoach = idCoach;
        this.calledFromPanel = calledFromPanel;
        activity = (CoachServicesActivity) context;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_resume_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final CoachResumeModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        // edit and delete an item from recyclerView
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtResumeTitle;
        private TextView txtStartDate;
        private TextView txtEndDate;
        private ImageView imgDelete;
        private ImageView imgEdit;

        private int position;
        private CoachResumeModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtResumeTitle = (TextView) itemView.findViewById(R.id.txtResumeTitle);
            txtStartDate = (TextView) itemView.findViewById(R.id.txtStartDate);
            txtEndDate = (TextView) itemView.findViewById(R.id.txtEndDate);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }

        private void setData(CoachResumeModel current, int position) {

            if (!calledFromPanel){
                imgEdit.setVisibility(View.INVISIBLE);
                imgDelete.setVisibility(View.INVISIBLE);
            }

               txtResumeTitle.setText(current.Title);
           txtStartDate.setText("تاریخ شروع: " + current.startDate);
            if (current.endDate.equals("") || current.endDate.equals("0"))
                txtEndDate.setText("تاریخ پایان: تاکنون");
            else
                txtEndDate.setText("تاریخ پایان: " + current.endDate);


            this.position = position;
            this.current = current;

        }

        // edit and delete an item from recyclerView s
        public void setListeners() {
            Log.i("TAG", "onSetListeners" + position);
            imgDelete.setOnClickListener(myViewHolder.this);
            imgEdit.setOnClickListener(myViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgDelete:
                    removeItem(position, current);
                    break;
                case R.id.imgEdit:
                    editItem(position, current);
                    break;
            }
        }
    }

    // edit and delete an item from recyclerView s
    public void removeItem(final int position, final CoachResumeModel current) {


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

    public void editItem(final int position, final CoachResumeModel current) {

        showDialog(position, current);

        notifyDataSetChanged();
    }

    private void showDialog(final int position, final CoachResumeModel current) {
        dialogEdit = new Dialog(context);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.dialog_add_resume);
        dialogEdit.setCancelable(true);
        dialogEdit.setCanceledOnTouchOutside(true);
        dialogEdit.show();

        edtTitle = dialogEdit.findViewById(R.id.edtTitle);
        edtStartDate = dialogEdit.findViewById(R.id.edtStartDate);
        edtEndDate = dialogEdit.findViewById(R.id.edtEndDate);
        btnOk = dialogEdit.findViewById(R.id.btnOk);
        checkContinue = dialogEdit.findViewById(R.id.checkContinue);
        imgClose = dialogEdit.findViewById(R.id.imgClose);
        txtWindowTitle = dialogEdit.findViewById(R.id.txtWindowTitle);

        edtTitle.setText(current.Title);
        edtStartDate.setText(current.startDate);
        edtEndDate.setText(current.endDate);
        txtWindowTitle.setText("ویرایش رزومه");

        //to open date picker dialog
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CoachResumeAdapter.this,
                        now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay()
                );
                startDateFlag = true;
                dpd.show(activity.getFragmentManager(), DATEPICKER);
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                        startDateFlag = false;
                    }
                });


            }
        });
        checkContinue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ischecked) {
                if(ischecked)
                {
                    edtEndDate.setEnabled(false);
                    edtEndDate.setText("");
                }

                else
                {
                    edtEndDate.setEnabled(true);
                }
            }
        });
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersianCalendar now = new PersianCalendar();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CoachResumeAdapter.this,
                        now.getPersianYear(),
                        now.getPersianMonth(),
                        now.getPersianDay()
                );
                finishDateFlag = true;
                dpd.show(activity.getFragmentManager(), DATEPICKER);
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                        finishDateFlag = false;
                    }
                });


            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtTitle.getText().toString().equals("") && !edtStartDate.getText().toString().equals("")) {

                    CoachResumeModel tmpModel = new CoachResumeModel();

                    tmpModel.id = current.id;
                    tmpModel.idCoach = current.idCoach;
                    tmpModel.Title = edtTitle.getText().toString();
                    tmpModel.startDate = edtStartDate.getText().toString();
                    tmpModel.endDate = edtEndDate.getText().toString();

                    WebServiceCallBackEdit callBackFileDetails = new WebServiceCallBackEdit(tmpModel, position);
                    callBackFileDetails.execute();

                } else {
                    Toast.makeText(context, "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        boolean flagMonth = false, flagDay = false;

        if (dayOfMonth / 10 < 1)
            flagDay = true;
        if ((monthOfYear + 1) / 10 < 1)
            flagMonth = true;

        date = year + "";
        if (flagMonth)
            date += "/0" + (monthOfYear + 1);
        else
            date += "/" + (monthOfYear + 1);
        if (flagDay)
            date += "/0" + dayOfMonth;
        else
            date += "/" + dayOfMonth;


        if (startDateFlag) {
            edtStartDate.setText(date);
            startDateFlag = false;
        }
        if (finishDateFlag) {
            edtEndDate.setText(date);
            finishDateFlag = false;
        }
    }


    private class WebServiceCallBackDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private int id, position;

        public WebServiceCallBackDelete(int id, int position) {
            this.id = id;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_wait);
            ImageView logo = dialog.findViewById(R.id.logo);

            //logo 360 rotate
            ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
            rotation.setDuration(3000);
            rotation.setRepeatCount(Animation.INFINITE);
            rotation.start();

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.deleteCoachResume(App.isInternetOn(), id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {

                if (result.equals("OK")) {
                    Toast.makeText(context, "با موفقیت حذف شد", Toast.LENGTH_LONG).show();
                    list.remove(position);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "عملیات نا موفق", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(context, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }


    private class WebServiceCallBackEdit extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        CoachResumeModel model;
        String result;
        int pos;

        public WebServiceCallBackEdit(CoachResumeModel model, int pos) {
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
            result = webService.editCoachResume(App.isInternetOn(), model);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {
                if (result.equals("OK")) {

                    list.remove(pos);
                    list.add(pos, model);
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


}