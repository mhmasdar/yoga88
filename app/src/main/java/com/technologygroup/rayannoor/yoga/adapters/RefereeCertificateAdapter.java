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
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.imageActivity;
import com.technologygroup.rayannoor.yoga.referees.RefereeServicesActivity;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Mohamad Hasan on 2/13/2018.
 */

public class RefereeCertificateAdapter extends RecyclerView.Adapter<RefereeCertificateAdapter.myViewHolder>  implements DatePickerDialog.OnDateSetListener{

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachHonorModel> list;
    private String result;
    private Dialog dialog;
    public static Dialog dialogEdit;

    // dialog add content
    EditText edtTitle, edtBody, edtDate;
    TextView txtNoImage, txtWindowTitle;
    ImageView imgClose;
    CircularProgressButton btnOk;


    //relates to date and time picker
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog", MULTIDATEPICKER = "MultiDatePickerDialog";
    String date;


    private static final int PICK_FILE_REQUEST = 3;
    private static String selectedImgName = "", selectedFilePath;
    private static int idCoach;
    RefereeServicesActivity activity;
    private boolean flagImgChanged = false;
    private boolean calledFromPanel = false;

    WebServiceCallBackDelete callBack;
    WebServiceCallBackEdit callBackFileDetails;

    public RefereeCertificateAdapter(Context context, List<CoachHonorModel> list, int idCoach, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idCoach = idCoach;
        this.calledFromPanel = calledFromPanel;
        activity = (RefereeServicesActivity) context;
    }

    public RefereeCertificateAdapter(Context context){
        this.context = context;
        txtWindowTitle = dialogEdit.findViewById(R.id.txtWindowTitle);
        edtTitle = dialogEdit.findViewById(R.id.edtTitle);
        edtBody = dialogEdit.findViewById(R.id.edtUniversity);
        edtDate = dialogEdit.findViewById(R.id.edtDate);
        txtNoImage = dialogEdit.findViewById(R.id.txtNoImage);

        btnOk = dialogEdit.findViewById(R.id.btnOk);
        imgClose = dialogEdit.findViewById(R.id.imgClose);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_certificate_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        final CoachHonorModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.imgCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefereeServicesActivity activity = (RefereeServicesActivity) context;
                Intent intent = new Intent(activity, imageActivity.class);
                intent.putExtra("ImgName", list.get(position).ImgName);
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (currentObj.Img != null) {
//                    if (!currentObj.Img.equals("") && !currentObj.Img.equals("null")) {
//
//                        CoachServicesActivity activity = (CoachServicesActivity) context;
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


        // edit and delete an item from recyclerView
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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

        private void setData(CoachHonorModel current, int position) {

            if (!calledFromPanel){
                imgEdit.setVisibility(View.INVISIBLE);
                imgDelete.setVisibility(View.INVISIBLE);
            }

            if (current.ImgName != null)
                if (!current.ImgName.equals("") && !current.ImgName.equals("null"))
                   Glide.with(context).load(App.imgAddr + current.ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCertificate);
//
            txtCertificateTitle.setText(current.Title);
//            txtCertificateDate.setText(current.Date);
//
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
    public void removeItem(final int position, final CoachHonorModel current) {


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

                callBack = new WebServiceCallBackDelete(current.id, position);
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

    public void editItem(final int position, final CoachHonorModel current) {

        showDialog(position, current);

        notifyDataSetChanged();
    }


    private void showDialog(final int position, final CoachHonorModel current) {
        dialogEdit = new Dialog(context);
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setContentView(R.layout.dialog_add_certificate);
        dialogEdit.setCancelable(true);
        dialogEdit.setCanceledOnTouchOutside(true);
        dialogEdit.show();
        LinearLayout lytimage;
        txtWindowTitle = dialogEdit.findViewById(R.id.txtWindowTitle);
        edtTitle = dialogEdit.findViewById(R.id.edtTitle);
        lytimage = dialogEdit.findViewById(R.id.lytimage);
        lytimage.setVisibility(View.GONE);
        edtBody = dialogEdit.findViewById(R.id.edtBody);
        edtDate = dialogEdit.findViewById(R.id.edtDate);
        txtNoImage = dialogEdit.findViewById(R.id.txtNoImage);

        btnOk = dialogEdit.findViewById(R.id.btnOk);
        imgClose = dialogEdit.findViewById(R.id.imgClose);


        txtWindowTitle.setText("ویرایش سوابق تحصیلی");
        edtTitle.setText(current.Title);
//        edtBody.setText(current.Des);
        edtDate.setText(current.Date);

//        if (current.Img != null) {
//            if (!current.Img.equals("") && !current.Img.equals("null")) {
//                imgCertificate.setVisibility(View.VISIBLE);
//                txtNoImage.setVisibility(View.GONE);
//                selectedImgName = current.Img;
//                Glide.with(context).load(App.imgAddr + current.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCertificate);
//            } else {
//
//            }
//        }else{
//            imgCertificate.setVisibility(View.GONE);
//            txtNoImage.setVisibility(View.VISIBLE);
//        }


        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersianCalendar now = new PersianCalendar();

                DatePickerDialog dpd = DatePickerDialog.newInstance(RefereeCertificateAdapter.this, now.getPersianYear(), now.getPersianMonth(), now.getPersianDay());
                dpd.show(activity.getFragmentManager(), DATEPICKER);
                dpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
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

                if (!edtTitle.getText().toString().equals("") && !edtDate.getText().toString().equals("")) {



//                        if (!current.Img.equals(selectedImgName))
//                            flagImgChanged = true;
//                        else
//                            flagImgChanged = false;

                        CoachHonorModel tmpModel = new CoachHonorModel();

                        tmpModel.id = current.id;
                        tmpModel.idRow = current.idRow;
//                        tmpModel.Img = selectedImgName;
                        tmpModel.Title = edtTitle.getText().toString();
                        tmpModel.Date = edtDate.getText().toString();
//                        tmpModel.Des = edtBody.getText().toString();

                        callBackFileDetails = new WebServiceCallBackEdit(tmpModel, position);
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


        edtDate.setText(date);
//        startDateInt = date.replace("/", "");


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

            result = webService.deleteCoachHhonor(App.isInternetOn(), id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (result != null) {

                if (result.equals("true")) {
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
                if (result.equals("OK")||result.equals("Ok")) {
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






//    private void showDialog() {
//        dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_wait);
//        ImageView logo = dialog.findViewById(R.id.logo);
//
//        //logo 360 rotate
//        ObjectAnimator rotation = ObjectAnimator.ofFloat(logo, "rotationY", 0, 360);
//        rotation.setDuration(3000);
//        rotation.setRepeatCount(Animation.INFINITE);
//        rotation.start();
//
//        dialog.setCancelable(true);
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.show();
//    }
}

