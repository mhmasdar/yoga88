package com.technologygroup.rayannoor.yoga.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachHonorModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.imageActivity;

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

        }

    }
    private void showDialog(CoachHonorModel current, int position) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_honour);
        imgClose = (ImageView) dialog.findViewById(R.id.imgClose);
        edtTitle = (EditText) dialog.findViewById(R.id.edtTitle);
        edtDate = (EditText) dialog.findViewById(R.id.edtDate);
        txtNoImage = (TextView) dialog.findViewById(R.id.txtNoImage);
        imgHonour = (ImageView) dialog.findViewById(R.id.imgHonour);
        imgSelectPicture = (ImageView) dialog.findViewById(R.id.imgSelectPicture);
        edtTitle.setText(current.Title);
        edtDate.setText(current.Date);
        btnOk = (CircularProgressButton) dialog.findViewById(R.id.btnOk);
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

//                WebServiceAdd web=new WebServiceAdd();
//                web.execute();
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
    View.OnClickListener imgSelectPicture_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (flagPermission) {

                if (App.isInternetOn()) {

                    if (idGym > 0) {

                        showFileChooser();

                    }
                } else {
                    Toast.makeText(context, "به اینترنت متصل نیستید", Toast.LENGTH_LONG).show();
                }
            }

        }
    };
    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("*/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        activity.startActivityForResult(Intent.createChooser(intent, "انتخاب فایل"), PICK_FILE_REQUEST);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//         super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == PICK_FILE_REQUEST) {
//                if (data == null) {
//                    //no data present
//                    return;
//                }
//
//
//                Uri selectedFileUri = data.getData();
//
//                imgHonour.setVisibility(View.VISIBLE);
//                txtNoImage.setVisibility(View.GONE);
//
//                if (selectedFileUri != null)
//                    if (!selectedFileUri.equals("") && !selectedFileUri.equals("null"))
//                        Glide.with(context).loadFromMediaStore(selectedFileUri).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgHonour);
//
//                selectedFilePath = FilePath.getPath(this.activity, selectedFileUri);
//                Log.i(TAG, "Selected File Path:" + selectedFilePath);
//
//                if (selectedFilePath != null && !selectedFilePath.equals("")) {
//
//                    String extension = selectedFilePath.substring(selectedFilePath.lastIndexOf(".") + 1, selectedFilePath.length());
//                    ClassDate classDate = new ClassDate();
//                    selectedImgName = classDate.getDateTime() + "_" + "g_" + idGym + "." + extension;
//
//                }
//            } else {
//                Toast.makeText(context, "خطا در انتخاب فایل", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}

