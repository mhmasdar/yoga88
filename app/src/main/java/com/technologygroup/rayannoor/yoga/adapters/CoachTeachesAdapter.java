package com.technologygroup.rayannoor.yoga.adapters;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Coaches.CoachServicesActivity;
import com.technologygroup.rayannoor.yoga.Coaches.addTeachActivity;
import com.technologygroup.rayannoor.yoga.Coaches.editTeachActivity;
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.Teaches.TeachDetailsActivity;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/16/2018.
 */

public class CoachTeachesAdapter extends RecyclerView.Adapter<CoachTeachesAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<TeachesModel> list;
    private String result;
    private Dialog dialog;
    private boolean calledFromPanel = false;
    private static int idCoach;
    CoachServicesActivity activity;
    ClassDate classDate;


    public CoachTeachesAdapter(Context context, List<TeachesModel> list, int idCoach, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idCoach = idCoach;
        this.calledFromPanel = calledFromPanel;
        activity = (CoachServicesActivity) context;
        classDate = new ClassDate();
    }

    @Override
    public CoachTeachesAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_teach_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

        final TeachesModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CoachServicesActivity activity = (CoachServicesActivity) context;

                Intent intent = new Intent(activity , TeachDetailsActivity.class);


                intent.putExtra("ID", currentObj.id);


                context.startActivity(intent);

            }
        });


        // edit and delete an item from recyclerView
        holder.setListeners();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtitle;
        private TextView txtDate;
        private ImageView imgDelete;
        private ImageView imgEdit;
   //     private ImageView imgTeach;
//        private ImageView imgCertificate;
//        private TextView txtCertificateDate;

        private int position;
        private TeachesModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtitle = (TextView) itemView.findViewById(R.id.txtitle);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
 //           imgTeach = (ImageView) itemView.findViewById(R.id.imgTeach);
//            imgCertificate = (ImageView) itemView.findViewById(R.id.imgCertificate);
//            txtCertificateDate = (TextView) itemView.findViewById(R.id.txtCertificateDate);


        }


        private void setData(TeachesModel current, int position) {

            if (!calledFromPanel) {
                imgEdit.setVisibility(View.INVISIBLE);
                imgDelete.setVisibility(View.INVISIBLE);
            }

//            if (current.Images != null)
//                if (!current.Images.equals("") && !current.Images.equals("null"))
//                    Glide.with(context).load(App.imgAddr + current.Images).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgTeach);

            txtitle.setText(current.Title);
            txtDate.setText(current.Date);

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

    public void removeItem(final int position, final TeachesModel current) {


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
    public void editItem(final int position, final TeachesModel current) {


        CoachServicesActivity activity = (CoachServicesActivity) context;

        Intent intent = new Intent(activity , editTeachActivity.class);


        intent.putExtra("idteach", current.id);


        context.startActivity(intent);

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

            result = webService.DeleteTrain(App.isInternetOn(), id);

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

}
