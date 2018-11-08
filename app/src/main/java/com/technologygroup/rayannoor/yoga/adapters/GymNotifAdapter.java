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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.ZanguleModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import org.json.JSONException;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class GymNotifAdapter extends RecyclerView.Adapter<GymNotifAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<ZanguleModel> list;
    int idgym;
    private Dialog dialog;
    private EditText edtTitle;
    private EditText edtBody;
    CircularProgressButton btnOk;
    boolean calledFromPanel;

    public GymNotifAdapter(Context context,List<ZanguleModel> l,int i,boolean b) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        list=l;
        idgym=i;
        calledFromPanel=b;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_notif_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.setData(list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {


        private TextView txtNotifTitle;
        private TextView txtNotifBody;
        private ImageView imgNotif;
        private ImageView imgDelete;
        private ImageView imgEdit;
        private TextView txtNotifDate;



        myViewHolder(View itemView) {
            super(itemView);
            txtNotifTitle = (TextView) itemView.findViewById(R.id.txtNotifTitle);
            txtNotifBody = (TextView) itemView.findViewById(R.id.txtNotifBody);
            imgNotif = (ImageView) itemView.findViewById(R.id.imgNotif);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEdit);
            txtNotifDate = (TextView) itemView.findViewById(R.id.txtNotifDate);

        }
        private void setData(final ZanguleModel current, final int position)
        {
            txtNotifTitle.setText(current.title);
            txtNotifBody.setText(current.Body);
            txtNotifDate.setText(current.Date);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(position,current);
                }
            });
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(current,position);
                }
            });
            if(!calledFromPanel)
            {
                imgDelete.setVisibility(View.GONE);
                imgEdit.setVisibility(View.GONE);
            }
        }
    }
    private void showDialog(final ZanguleModel c, final int pos) {
        LinearLayout lytImage;
        final TextView title;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_gym_notif);
        edtBody=dialog.findViewById(R.id.edtBody);
        edtTitle=dialog.findViewById(R.id.edtTitle);
        lytImage=dialog.findViewById(R.id.lytImage);
        lytImage.setVisibility(View.GONE);
        title=dialog.findViewById(R.id.title);
        title.setText("ویرایش اعلان همگانی");

        btnOk=dialog.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZanguleModel model=new ZanguleModel();
                model.id=c.id;
                model.title=edtTitle.getText().toString();
                model.Body=edtBody.getText().toString();
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
