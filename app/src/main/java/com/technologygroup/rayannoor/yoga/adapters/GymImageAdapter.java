package com.technologygroup.rayannoor.yoga.adapters;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.GalleryModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.imageActivity;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/7/2018.
 */

public class GymImageAdapter extends RecyclerView.Adapter<GymImageAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private boolean calledFromPanel = false;
    private static int idGym;
    private List<GalleryModel> list;
    GymServiceActivity activity;

    public GymImageAdapter(Context context, List<GalleryModel> list, int idGym, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idGym = idGym;
        this.calledFromPanel = calledFromPanel;
        activity = (GymServiceActivity) context;
    }

    @Override
    public GymImageAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_image, parent, false);
        GymImageAdapter.myViewHolder holder = new GymImageAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymImageAdapter.myViewHolder holder, int position) {

        final GalleryModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentObj.img != null) {
                    if (!currentObj.img.equals("") && !currentObj.img.equals("null")) {

                        GymServiceActivity activity = (GymServiceActivity) context;
                        Intent intent = new Intent(activity, imageActivity.class);
                        intent.putExtra("ImgName", currentObj.img);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "تصویر موجود نیست", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView txtBody;
        private int position;
        private GalleryModel current;

        myViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtBody = (TextView) itemView.findViewById(R.id.txtBody);
        }
        private void setData(GalleryModel current, int position) {

            if (current.img != null)
                if (!current.img.equals("") && !current.img.equals("null"))
                    Glide.with(context).load(App.imgAddr + current.img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);

            this.position = position;
            this.current = current;
            this.txtBody.setText(current.Description);

        }
    }
    public void removeItem(final int position, final GalleryModel current) {


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

            result = webService.DeletePost(App.isInternetOn(), id);

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
}
