package com.technologygroup.rayannoor.yoga.adapters;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Coaches.CoachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.Models.TeachesModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.Teaches.CoachTeachDetailsActivity;
import com.technologygroup.rayannoor.yoga.Teaches.CoachTeachsActivity;

import java.util.List;

/**
 * Created by Mohamad Hasan on 2/21/2018.
 */

public class TeachCoachesAdapter extends RecyclerView.Adapter<TeachCoachesAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<TeachesModel> list;
    ClassDate classDate;

    private int idCoach;
    private CoachModel coachModel;

    public TeachCoachesAdapter(Context context, List<TeachesModel> list) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        classDate = new ClassDate();
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_teach_coach_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final TeachesModel currentObj = list.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoachTeachsActivity activity = (CoachTeachsActivity) context;
                Intent intent = new Intent(activity, CoachTeachDetailsActivity.class);
                //intent.putExtra("calledToAdd", false);
                intent.putExtra("id", currentObj.id);

                intent.putExtra("Title", currentObj.Title);
                intent.putExtra("Body", currentObj.Body);
                intent.putExtra("Images", currentObj.Images);
                intent.putExtra("coachName", currentObj.Name);
                intent.putExtra("ImagePersonal", currentObj.ImagePersonal);
                context.startActivity(intent);
            }
        });

        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView txtDate;
        private TextView txtTitle;
        private TextView txtProfile;
        private TextView txtCoachName;
        private ImageView imgTeach;

        private int position;
        private TeachesModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtProfile = (TextView) itemView.findViewById(R.id.txtProfile);
            txtCoachName = (TextView) itemView.findViewById(R.id.txtCoachName);
            imgTeach = (ImageView) itemView.findViewById(R.id.imgTeach);
        }

        private void setData(TeachesModel current, int position) {

            txtTitle.setText(current.Title);
            txtDate.setText(current.Date);
            txtCoachName.setText(current.Name);
            this.position = position;
            this.current = current;

        }

        public void setListeners() {
            Log.i("TAG", "onSetListeners" + position);
            txtProfile.setOnClickListener(myViewHolder.this);
            //imgEdit.setOnClickListener(myViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.txtProfile:
                    break;
            }
        }

    }

    public void visitProfile(final int id) {

        if (id > 0) {

            idCoach = id;

            WebServiceCoachInfo webServiceCoachInfo = new WebServiceCoachInfo();
            webServiceCoachInfo.execute();
        } else {
            Toast.makeText(context, "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
        }

    }

    private class WebServiceCoachInfo extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;

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

            coachModel = webService.getCoachInfo(App.isInternetOn(), idCoach);

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (coachModel != null) {

                CoachTeachsActivity activity = (CoachTeachsActivity) context;
                Intent intent = new Intent(activity, CoachDetailsActivity.class);
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
