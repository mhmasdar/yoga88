package com.technologygroup.rayannoor.yoga.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
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

                    WebServiceCoachInfo webServiceCoachInfo = new WebServiceCoachInfo(currentObj.idUser);
                    webServiceCoachInfo.execute();
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

        private void setData(GymCoachesModel current, int position) {

            if (!calledFromPanel) {
                imgDelete.setVisibility(View.INVISIBLE);
                txtDelete.setVisibility(View.INVISIBLE);
            }

            if (current.Img != null)
                if (!current.Img.equals("") && !current.Img.equals("null"))
                    Glide.with(context).load(App.imgAddr + current.Img).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgCoach);


            txtCoachName.setText(current.fName + " " + current.lName);

            this.position = position;
            this.current = current;

        }
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

            coachModel = webService.getCoachInfo(App.isInternetOn(), idUser);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (coachModel != null) {

                Intent intent = new Intent(context, CoachDetailsActivity.class);
//                Toast.makeText(view.getContext(), holder.lName+"", Toast.LENGTH_LONG).show();
                intent.putExtra("fName", coachModel.fName);
                intent.putExtra("Email", coachModel.Email);
                intent.putExtra("Instagram", coachModel.Instagram);
                intent.putExtra("lName", coachModel.lName);
                intent.putExtra("Telegram", coachModel.Telegram);
//                intent.putExtra("Img", coachModel.Img);
                intent.putExtra("id", coachModel.id);
                intent.putExtra("idCity", coachModel.idCity);
//                intent.putExtra("idCurrentPlan", coachModel.idCurrentPlan);
                intent.putExtra("like", coachModel.like);
//                intent.putExtra("lastUpdate", coachModel.lastUpdate);
                intent.putExtra("Mobile", coachModel.Mobile);
                intent.putExtra("natCode", coachModel.natCode);
                intent.putExtra("Rate", coachModel.Rate);
                intent.putExtra("Gender", coachModel.Gender);
                intent.putExtra("Rate", coachModel.Rate);
                intent.putExtra("Gender", coachModel.Gender);
                intent.putExtra("City", coachModel.City);
                intent.putExtra("State", coachModel.State);
                context.startActivity(intent);

            } else {

                Toast.makeText(context, "ارتباط با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }
}
