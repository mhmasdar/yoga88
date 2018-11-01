package com.technologygroup.rayannoor.yoga.adapters;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachEduModel;
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.imageActivity;

import org.json.JSONException;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Mohamad Hasan on 3/8/2018.
 */

public class GymHonourAdapter extends RecyclerView.Adapter<GymHonourAdapter.myViewHolder> implements
        DatePickerDialog.OnDateSetListener{

    private Context context;
    private LayoutInflater mInflater;
    private boolean calledFromPanel = false;
    private static int idGym;
    private List<CoachHonorModel> list;
    GymServiceActivity activity;
    ClassDate classDate;
    private String selectedFilePath, selectedImgName = "";
    private Dialog dialog;
    private Dialog dialogedit;
    private ImageView imgClose;
    private EditText edtTitle;
    private EditText edtDate;
    private TextView txtNoImage;
    private ImageView imgHonour;
    private ImageView imgSelectPicture;
    private CircularProgressButton btnOk;
    public boolean flagPermission = false;
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";
    private static final int PICK_FILE_REQUEST = 1;

    public GymHonourAdapter(Context context, List<CoachHonorModel> list, int idGym, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idGym = idGym;
        this.calledFromPanel = calledFromPanel;
        activity = (GymServiceActivity) context;
        classDate = new ClassDate();
    }

    @Override
    public GymHonourAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_honour, parent, false);
        GymHonourAdapter.myViewHolder holder = new GymHonourAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymHonourAdapter.myViewHolder holder, final int position) {
        final CoachHonorModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.imgCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GymServiceActivity activity = (GymServiceActivity) context;
                Intent intent = new Intent(activity, imageActivity.class);
                intent.putExtra("ImgName", list.get(position).ImgName);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                if (currentObj.Img != null) {
//                    if (!currentObj.Img.equals("") && !currentObj.Img.equals("null")) {
//
//                        GymServiceActivity activity = (GymServiceActivity) context;
//                        Intent intent = new Intent(activity, imageActivity.class);
//                        intent.putExtra("ImgName", currentObj.Img);
//                        context.startActivity(intent);
//                    } else {
//                        Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        boolean flagMonth = false, flagDay = false;
        String date;
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


        edtDate.setText(date);
//        startDateInt = date.replace("/", "");


    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCertificateTitle;
        private ImageView imgDelete;
        private ImageView imgEdit;
        private ImageView imgCertificate;
        private TextView txtCertificateDate;

        private int position;
        private CoachHonorModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtCertificateTitle = (TextView) itemView.findViewById(R.id.txtCertificateTitle);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            imgCertificate = (ImageView) itemView.findViewById(R.id.imgCertificate);
            txtCertificateDate = (TextView) itemView.findViewById(R.id.txtCertificateDate);
        }

        private void setData(final CoachHonorModel current, final int position) {

            if (!calledFromPanel){
                imgEdit.setVisibility(View.INVISIBLE);
                imgDelete.setVisibility(View.INVISIBLE);
            }

            if (current.ImgName != null)
                if (!current.ImgName.equals("") && !current.ImgName.equals("null"))
                    Glide.with(context).load(App.imgAddr + current.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCertificate);
            txtCertificateTitle.setText(current.Title);
            txtCertificateDate.setText(current.Date);
            this.position = position;
            this.current = current;
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   showDialog(current,position);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    removeItem(current,position);
                }
            });

        }

    }
    private void showDialog(final CoachHonorModel current, final int position) {
        dialogedit = new Dialog(activity);
        TextView Title;
        LinearLayout lytimage;
        dialogedit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogedit.setContentView(R.layout.dialog_add_gym_honour);
        imgClose = (ImageView) dialogedit.findViewById(R.id.imgClose);
        Title=dialogedit.findViewById(R.id.Title);
        Title.setText("ویرایش افتخارات باشگاه");
        edtTitle = (EditText) dialogedit.findViewById(R.id.edtTitle);
        lytimage =  dialogedit.findViewById(R.id.lytimage);
        lytimage.setVisibility(View.GONE);
        edtDate = (EditText) dialogedit.findViewById(R.id.edtDate);
        txtNoImage = (TextView) dialogedit.findViewById(R.id.txtNoImage);
        imgHonour = (ImageView) dialogedit.findViewById(R.id.imgHonour);
        imgSelectPicture = (ImageView) dialogedit.findViewById(R.id.imgSelectPicture);
        edtTitle.setText(current.Title);
        edtDate.setText(current.Date);
        btnOk = (CircularProgressButton) dialogedit.findViewById(R.id.btnOk);
//        imgSelectPicture.setOnClickListener(imgSelectPicture_click);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GymServiceActivity activity = (GymServiceActivity) context;

                PersianCalendar now = new PersianCalendar();

                DatePickerDialog dpd = DatePickerDialog.newInstance(GymHonourAdapter.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
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
                CoachHonorModel c=new CoachHonorModel();
                c.id=current.id;
                c.Title=edtTitle.getText().toString();
                c.Date=edtDate.getText().toString();
                WebServiceCallBackEdit web=new WebServiceCallBackEdit(c,position);
                web.execute();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialogedit.setCancelable(true);
        dialogedit.setCanceledOnTouchOutside(true);
        dialogedit.show();
    }
    public void removeItem(final CoachHonorModel current,final int position) {


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
    private class WebServiceCallBackDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private int id, position;
        String result;
        Dialog dialog;

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

            try {
                result = webService.deleteCoachHhonor(App.isInternetOn(), id);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {

                if (result.equals("OK")||result.equals("Ok")) {
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
        CoachHonorModel model;
        String result;
        int pos;

        public WebServiceCallBackEdit(CoachHonorModel model, int pos) {
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

            result = webService.editCoachHonor(App.isInternetOn(), model);

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
                            dialogedit.dismiss();
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

