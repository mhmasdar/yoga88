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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import org.json.JSONException;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class CoachKaryabAdapter extends RecyclerView.Adapter<CoachKaryabAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    List<ZanguleModel> list;
    Dialog dialog;
    CircularProgressButton btnOk;


    public CoachKaryabAdapter(Context context,List<ZanguleModel> l) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        list=l;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coach_karyab_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        holder.txtNotifDate.setText(list.get(position).Date);
        holder.txtNotifTitle.setText(list.get(position).title);
        holder.txtNotifBody.setText(list.get(position).Body);
        if (list.get(position).image != null)
            if (!list.get(position).image.equals("") && !list.get(position).image.equals("null"))
                Glide.with(context).load(App.imgAddr + list.get(position).image).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.img);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.removeItem(position,list.get(position));
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.showDialog(list.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNotifDate;
        private TextView txtNotifTitle;
        private TextView txtNotifBody;
        private ImageView img;
        private ImageView imgDelete;
        private ImageView imgEdit;
//        private LinearLayout lytImage;
        private TextView title;

        myViewHolder(View itemView) {
            super(itemView);

            txtNotifDate = (TextView) itemView.findViewById(R.id.txtNotifDate);
            txtNotifTitle = (TextView) itemView.findViewById(R.id.txtNotifTitle);
            txtNotifBody = (TextView) itemView.findViewById(R.id.txtNotifBody);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            img = (ImageView) itemView.findViewById(R.id.img);


            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
        }
        private void showDialog(final ZanguleModel c, final int pos) {
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_add_coach_karyab);
            txtNotifBody=dialog.findViewById(R.id.edtBody);
            txtNotifTitle=dialog.findViewById(R.id.edtTitle);
//            lytImage=dialog.findViewById(R.id.lytImage);
            title=dialog.findViewById(R.id.txtWindowTitle);
            title.setText("ویرایش کاریابی");
//            lytImage.setVisibility(View.GONE);
            btnOk=dialog.findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZanguleModel model=new ZanguleModel();
                    model.id=c.id;
                    model.title=txtNotifTitle.getText().toString();
                    model.Body=txtNotifBody.getText().toString();
                    WebServiceCallBackEdit webServiceADD=new WebServiceCallBackEdit(model,pos);
                    webServiceADD.execute();
                }
            });
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
        public void removeItem(final int position, final ZanguleModel current) {


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
                    result = webService.deletegymnotif(App.isInternetOn(), id);
                } catch (JSONException e) {
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
            ZanguleModel model;
            String result;
            int pos;

            public WebServiceCallBackEdit(ZanguleModel model, int pos) {
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
                result = webService.EditElanat(App.isInternetOn(), model.id,model.title,model.Body);

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
    }

}