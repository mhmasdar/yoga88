package com.technologygroup.rayannoor.yoga.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.R;
import com.technologygroup.rayannoor.yoga.RoundedImageView;
import com.technologygroup.rayannoor.yoga.Services.WebService;

import java.util.List;

/**
 * Created by Mohamad Hasan on 3/16/2018.
 */

public class GymCoachSearchAdapter extends RecyclerView.Adapter<GymCoachSearchAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CoachModel> coachModel;
    private int idgym;
    int idcoach;

    public GymCoachSearchAdapter(Context context, List<CoachModel> c, int id) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        coachModel = c;
        idgym = id;
    }

    @Override
    public GymCoachSearchAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gym_search_coach, parent, false);
        GymCoachSearchAdapter.myViewHolder holder = new GymCoachSearchAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(GymCoachSearchAdapter.myViewHolder holder, final int position) {
        holder.txtCoachName.setText(coachModel.get(position).fName + " " + coachModel.get(position).lName);
        //holder.txtCoachAddress.setText(coachModel.get(position).State+" - "+coachModel.get(position).City);
        holder.txtCoachAddress.setVisibility(View.GONE);
        if (coachModel.get(position).ImgName != null)
            if (!coachModel.get(position).ImgName.equals("") && !coachModel.get(position).ImgName.equals("null"))
                Glide.with(context).load(App.imgAddr + coachModel.get(position).ImgName).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imgCoach);
        holder.lytCoachSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idcoach=coachModel.get(position).id;

                WebServiceList webServiceList=new WebServiceList();
                webServiceList.execute();

            }
        });

    }

    @Override
    public int getItemCount() {
        return coachModel.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView lytCoachSelect;
        TextView txtCoachName;
        TextView txtCoachAddress;
        private RoundedImageView imgCoach;

        myViewHolder(View itemView) {
            super(itemView);
            lytCoachSelect = itemView.findViewById(R.id.lytCoachSelect);
            txtCoachName = itemView.findViewById(R.id.txtCoachName);
            txtCoachAddress = itemView.findViewById(R.id.txtCoachAddress);
            imgCoach = itemView.findViewById(R.id.imgCoach);

        }
    }

    private class WebServiceList extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.addgymcoach(App.isInternetOn(), idgym,idcoach);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (result.equals("ok")||result.equals("OK")||result.equals("Ok")||result.equals(""))
            {
                Toast.makeText(context, "مربی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();

            }
            else
            {
                Toast.makeText(context, "مربی تایید نشد", Toast.LENGTH_SHORT).show();
            }

        }
    }
}