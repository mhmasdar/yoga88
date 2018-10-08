package com.technologygroup.rayannoor.yoga.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Coaches.CoachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymServiceActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.Models.GymCoachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/8/2018.
 */

public class GymCoachesAdapter extends RecyclerView.Adapter<GymCoachesAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private boolean calledFromPanel = false;
    private static int idGym;
    private static int idcoach;
    private List<GymCoachesModel> list;
    GymServiceActivity activity;
    CoachModel coachModel;

    public GymCoachesAdapter(Context context, List<GymCoachesModel> list, int idGym, boolean calledFromPanel) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.idGym = idGym;
        this.calledFromPanel = calledFromPanel;
        activity = (GymServiceActivity) context;
    }

    @Override
    public GymCoachesAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_coach_list, parent, false);
        GymCoachesAdapter.myViewHolder holder = new GymCoachesAdapter.myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final GymCoachesAdapter.myViewHolder holder, int position) {
        final GymCoachesModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentObj.idUser > 0) {

                    Intent intent = new Intent(context, CoachDetailsActivity.class);
                    intent.putExtra("idUser", currentObj.idUser);
                    context.startActivity(intent);

                } else {
                    Toast.makeText(context, "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtCertificateTitle;
        private ImageView imgDelete;
        private ImageView imgCoach;
        private TextView txtCoachName, txtDelete;

        private int position;
        private GymCoachesModel current;

        myViewHolder(View itemView) {
            super(itemView);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgCoach = (ImageView) itemView.findViewById(R.id.imgCoach);
            txtCoachName = (TextView) itemView.findViewById(R.id.txtCoachName);
            txtDelete = (TextView) itemView.findViewById(R.id.txtDelete);
        }

        private void setData(final GymCoachesModel current, final int position) {

            if (!calledFromPanel) {
                imgDelete.setVisibility(View.INVISIBLE);
                txtDelete.setVisibility(View.INVISIBLE);
            }

            txtCoachName.setText(current.fName + " " + current.lName);
            txtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeItem(position,current);
                }
            });

            this.position = position;
            this.current = current;

        }
    }
    public void removeItem(final int position, final GymCoachesModel current) {


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
                idcoach=current.idUser;
                WebServiceCoachDelete callBack = new WebServiceCoachDelete(current.idUser, position);
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
    private class WebServiceCoachInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;
        int idUser;

        public WebServiceCoachInfo(int idUser) {
            this.idUser = idUser;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            coachModel = new CoachModel();


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

            coachModel = webService.getCoachDetail(App.isInternetOn(), idUser);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (coachModel != null) {



            } else {

                Toast.makeText(context, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }
    private class WebServiceCoachDelete extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;
        int idUser;
        String response;
        int position;

        public WebServiceCoachDelete(int idUser,int position) {
            this.idUser = idUser;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            coachModel = new CoachModel();


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

            response = webService.deleteِgymcoach(App.isInternetOn(),idGym ,idcoach);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (response != null) {

                if(response.equals("ok")||response.equals("OK")||response.equals("Ok"))
                {
                    Toast.makeText(context, "مربی با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    notifyDataSetChanged();
                    
                }

            } else {

                Toast.makeText(context, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }
}
