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
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.CourseModel;
import com.technologygroup.rayannoor.yoga.Models.GymCoachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Mohamad Hasan on 3/9/2018.
 */

public class GymCourseAdapter extends RecyclerView.Adapter<GymCourseAdapter.myViewHolder> implements
        DatePickerDialog.OnDateSetListener {

    private Context context;
    private LayoutInflater mInflater;
    private boolean calledFromPanel = false;
    private static int idGym;
    private List<CourseModel> list;
    public int poscoach;
    List<String> coaches;
    List<Integer> coachesId;
    GymServiceActivity activity;
    ClassDate classDate;
    private ImageView imgClose;
    private EditText edtCourse;
    List<GymCoachesModel> listcoaches;

    TextView title;
    public static Dialog dialog;
    EditText edtDateStart;
    EditText edtDateEnd;
    EditText edtTitle;
    EditText edtTime;
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";
    String date;
    CircularProgressButton btnOk;
    Spinner selectCoach;
    EditText t;


    public GymCourseAdapter(Context context, List<CourseModel> list, int idGym, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idGym = idGym;
        this.calledFromPanel = calledFromPanel;
        activity = (GymServiceActivity) context;
        classDate = new ClassDate();
    }

    @Override
    public GymCourseAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_course, parent, false);
        GymCourseAdapter.myViewHolder holder = new GymCourseAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymCourseAdapter.myViewHolder holder, int position) {
        final CourseModel currentObj = list.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
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


        t.setText(date);
//        startDateInt = date.replace("/", "");


    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCoachName, txtDateTime, txtStartDate, txtEndDate, txtTitle;
        private ImageView imgDelete, imgEdit;

        private int position;
        private CourseModel current;

        myViewHolder(View itemView) {
            super(itemView);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            txtCoachName = (TextView) itemView.findViewById(R.id.txtCoachName);
            txtDateTime = (TextView) itemView.findViewById(R.id.txtDateTime);
            txtStartDate = (TextView) itemView.findViewById(R.id.txtStartDate);
            txtEndDate = (TextView) itemView.findViewById(R.id.txtEndDate);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

        }

        private void setData(final CourseModel current, final int position) {

            if (!calledFromPanel){
                imgDelete.setVisibility(View.INVISIBLE);
                imgEdit.setVisibility(View.INVISIBLE);
            }

            txtTitle.setText(current.Title);
            txtCoachName.setText(current.coachName);
            txtStartDate.setText(current.startDate);
            txtEndDate.setText("تا " + current.endDate);
            txtDateTime.setText(current.Days);
            this.position = position;
            this.current = current;
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(position,current);
                }
            });
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EditDiolog(current,position);
                }
            });

        }
        private void EditDiolog(final CourseModel courseModel, final int position)
        {

            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_add_gym_course);
            edtDateStart=dialog.findViewById(R.id.edtStartDate);
            edtDateEnd=dialog.findViewById(R.id.edtEndDate);
            edtTitle=dialog.findViewById(R.id.edtTitle);
            edtTime=dialog.findViewById(R.id.edtTime);
            title=dialog.findViewById(R.id.Title);
            imgClose=dialog.findViewById(R.id.imgClose);
            selectCoach=dialog.findViewById(R.id.CoachesSpinner);
            title.setText("تغییر نام دوره");
            btnOk=dialog.findViewById(R.id.btnOk);
            edtDateStart.setText(courseModel.startDate);
            edtDateEnd.setText(courseModel.endDate);
            edtTitle.setText(courseModel.Title);
            edtTime.setText(courseModel.Days);
            WebServiceListCoach webServiceListCoach=new WebServiceListCoach();
            webServiceListCoach.execute();
            edtDateStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t=edtDateStart;
                    GymServiceActivity activity = (GymServiceActivity) context;
                    PersianCalendar now = new PersianCalendar();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(GymCourseAdapter.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
                    dpd.show(activity.getFragmentManager(), DATEPICKER);
                    dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            Log.d("TimePicker", "Dialog was cancelled");

                        }
                    });

                }

            });
            edtDateEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t=edtDateEnd;
                    GymServiceActivity activity = (GymServiceActivity) context;
                    PersianCalendar now = new PersianCalendar();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(GymCourseAdapter.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
                    dpd.show(activity.getFragmentManager(), DATEPICKER);
                    dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            Log.d("TimePicker", "Dialog was cancelled");
                        }
                    });
                }
            });
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseModel model=new CourseModel();
                    model.idTerm=courseModel.idTerm;
                    model.Title=edtTitle.getText().toString();
                    model.startDate=edtDateStart.getText().toString();
                    model.endDate=edtDateEnd.getText().toString();
                    model.Days=edtTime.getText().toString();
                    model.idcoach=poscoach;
                    model.coachName=selectCoach.getSelectedItem().toString();
                    WebServiceCallBackEdit webServiceCallBackEdit=new WebServiceCallBackEdit(model,position);
                    webServiceCallBackEdit.execute();
                }
            });
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                dialog.dismiss();
                }
            });
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

        }
        public void removeItem(final int position, final CourseModel current) {


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

                    WebServiceCallBackDelete callBack = new WebServiceCallBackDelete(current.idTerm, position);
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
            CourseModel model;
            String result;
            int pos;

            public WebServiceCallBackEdit(CourseModel model, int pos) {
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

                result = webService.EditGymTerm(App.isInternetOn(), model.idTerm,idGym,model.idcoach,edtTitle.getText().toString(),edtTime.getText().toString(),edtDateStart.getText().toString(),edtDateStart.getText().toString());

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
                                dialog.dismiss();
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

                result = webService.DeleteGymTerm(App.isInternetOn(), id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);


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

private class WebServiceListCoach extends AsyncTask<Object, Void, Void> {

    private WebService webService;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        webService = new WebService();
        listcoaches = new ArrayList<>();

    }

    @Override
    protected Void doInBackground(Object... params) {

        listcoaches = webService.getGymCoaches(App.isInternetOn(), idGym);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        coaches = new ArrayList<>();
        coachesId = new ArrayList<>();
        coachesId.clear();
        coaches.clear();
        if (listcoaches != null) {
            for (int i = 0; i < listcoaches.size(); i++) {
                coaches.add(listcoaches.get(i).fName + " " + listcoaches.get(i).lName);
                coachesId.add(listcoaches.get(i).idUser);
                setSpinner();
            }

        }
    }

    }
        public void setSpinner()
        {
            ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,coaches);
            dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selectCoach.setAdapter(dataAdapterCourse);
            selectCoach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    pos=selectCoach.getSelectedItemPosition();
                    poscoach=coachesId.get(pos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    }
}
