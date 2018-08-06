package com.technologygroup.rayannoor.yoga;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.technologygroup.rayannoor.yoga.Classes.App;
import com.technologygroup.rayannoor.yoga.Classes.ClassDate;
import com.technologygroup.rayannoor.yoga.Classes.ClassLevels;
import com.technologygroup.rayannoor.yoga.Coaches.CoachProfileActivity;
import com.technologygroup.rayannoor.yoga.Gyms.GymsListActivity;
import com.technologygroup.rayannoor.yoga.Models.CoachModel;
import com.technologygroup.rayannoor.yoga.Models.CommentModel;
import com.technologygroup.rayannoor.yoga.Services.WebService;
import com.technologygroup.rayannoor.yoga.adapters.GymListAdapter;
import com.technologygroup.rayannoor.yoga.adapters.commentsAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private RelativeLayout btnBack;
    private LinearLayout lytMain;
    private RecyclerView Recycler;
    private LinearLayout lytSubmitComment;
    private LinearLayout lytSendComments;
    private EditText edtComment;
    private LinearLayout lytDisconnect;
    private LinearLayout lytEmpty;

    List<CommentModel> commentList;

    private int idCoachOrGym;
    private boolean isGym;

    String resultAdd;
    public boolean canSend = true;
    SharedPreferences prefs;
    String userName;
    WebServiceAdd callBackAdd;
    WebServiceComment webServiceComment;
    Button btnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initView();

        idCoachOrGym = getIntent().getIntExtra("IdCoachOrGym", -1);
        isGym = getIntent().getBooleanExtra("IsGym", false);

        if (idCoachOrGym > 0) {

            webServiceComment = new WebServiceComment();
            webServiceComment.execute();
        } else {
            Toast.makeText(this, "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
            finish();
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lytSendComments.setOnClickListener(lytSendComments_click);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idCoachOrGym > 0) {

                    webServiceComment = new WebServiceComment();
                    webServiceComment.execute();
                } else {
                    Toast.makeText(getBaseContext(), "مربی مورد نظر یافت نشد", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    private void initView() {
        btnBack = (RelativeLayout) findViewById(R.id.btnBack);
        lytMain = (LinearLayout) findViewById(R.id.lytMain);
        Recycler = (RecyclerView) findViewById(R.id.Recycler);
        lytSubmitComment = (LinearLayout) findViewById(R.id.lytSubmitComment);
        lytSendComments = (LinearLayout) findViewById(R.id.lytSendComments);
        edtComment = (EditText) findViewById(R.id.edtComment);
        lytDisconnect = (LinearLayout) findViewById(R.id.lytDisconnect);
        lytEmpty = (LinearLayout) findViewById(R.id.lytEmpty);
        btnTryAgain = findViewById(R.id.btnTryAgain);
    }


    View.OnClickListener lytSendComments_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            prefs = getSharedPreferences("MyPrefs", 0);
            int idUser = prefs.getInt("idUser", -1);
            userName = prefs.getString("Name", "") + " " + prefs.getString("lName", "");

            if (idCoachOrGym > 0){
                if (canSend) {

                    if (idUser > 0) {

                        if (!edtComment.getText().toString().equals("")) {

                            canSend = false;
                            callBackAdd = new WebServiceAdd(idUser);
                            callBackAdd.execute();

                        } else {
                            Toast.makeText(getApplicationContext(), "لطفا متن پیام را وارد کنید", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Snackbar snackbar = Snackbar.make(lytMain, "ابتدا باید ثبت نام کنید", Snackbar.LENGTH_LONG);
                        snackbar.setAction("ثبت نام", new registerAction());

                        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
                        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f);
                        textView.setLayoutParams(parms);
                        textView.setGravity(Gravity.LEFT);
                        snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    }
                }

            } else {
                Toast.makeText(CommentsActivity.this, "ناموفق", Toast.LENGTH_LONG).show();
            }

        }
    };


    public class registerAction implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            // Code to undo the user's last action
            Intent i = new Intent(CommentsActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }

    private void setUpRecyclerView(List<CommentModel> list) {
        commentsAdapter adapter = new commentsAdapter(CommentsActivity.this, list);
        Recycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler.setLayoutManager(mLinearLayoutManagerVertical);
    }

    private class WebServiceComment extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        Dialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            commentList = new ArrayList<>();


            dialog = new Dialog(CommentsActivity.this);
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

            commentList = webService.getComments(App.isInternetOn(), isGym, idCoachOrGym);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (commentList != null) {

                if (commentList.size() > 0){

                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);

                    setUpRecyclerView(commentList);

                } else {
                    lytDisconnect.setVisibility(View.GONE);
                    lytMain.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.VISIBLE);
                }

            } else {
                lytMain.setVisibility(View.GONE);
                lytEmpty.setVisibility(View.GONE);
                lytDisconnect.setVisibility(View.VISIBLE);
            }

        }

    }

    private class WebServiceAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        CommentModel model;
        Dialog dialog;
        int idUser;

        public WebServiceAdd(int idUser){
            this.idUser = idUser;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            ClassDate classDate = new ClassDate();

            dialog = new Dialog(CommentsActivity.this);
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

            model = new CommentModel();
            model.id = -1;
            model.idRow = idCoachOrGym;
            model.idUser = idUser;
            model.name = userName;
            model.date = Integer.parseInt(classDate.getDate());
            model.body = edtComment.getText().toString();
            model.isGym = isGym;
        }

        @Override
        protected Void doInBackground(Object... params) {

            resultAdd = webService.AddComment(App.isInternetOn(), model);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog.dismiss();

            if (resultAdd != null) {

                if (Integer.parseInt(resultAdd) > 0) {

                    model.id = Integer.parseInt(resultAdd);
                    edtComment.setText("");
                    commentList.add(model);
                    lytDisconnect.setVisibility(View.GONE);
                    lytEmpty.setVisibility(View.GONE);
                    lytMain.setVisibility(View.VISIBLE);
                    Toast.makeText(CommentsActivity.this, "نظر شما ارسال شد", Toast.LENGTH_LONG).show();
                    setUpRecyclerView(commentList);

                } else if (Integer.parseInt(resultAdd) == 0) {

                    Toast.makeText(CommentsActivity.this, "ارسال نظر ناموفق است", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(CommentsActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

                }
            } else {

                Toast.makeText(CommentsActivity.this, "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();

            }

            canSend = true;

        }
    }


    @Override
    public void onStop() {
        super.onStop();

        if (callBackAdd != null)
            if (callBackAdd.getStatus() == AsyncTask.Status.RUNNING)
                callBackAdd.cancel(true);
        if (webServiceComment != null)
            if (webServiceComment.getStatus() == AsyncTask.Status.RUNNING)
                webServiceComment.cancel(true);
    }

}
