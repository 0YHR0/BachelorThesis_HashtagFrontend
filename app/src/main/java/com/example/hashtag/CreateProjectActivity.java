package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * This class defines the create project page
 *
 * @author Yang Haoran
 */
public class CreateProjectActivity extends AppCompatActivity {
    private Button btn_create_confirm;
    private EditText et_start_time;
    private EditText et_end_time;
    private EditText et_project_name;
    private EditText et_hashtagset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        et_start_time = findViewById(R.id.et_start_time);
        et_end_time = findViewById(R.id.et_end_time);
        et_project_name = findViewById(R.id.et_project_name);
        et_hashtagset = findViewById(R.id.et_hashtagset);
        btn_create_confirm = findViewById(R.id.btn_create_confirm);
        btn_create_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Creating...");
                pDialog.setCancelable(true);
                pDialog.show();
                String start = et_start_time.getText().toString();
                String end = et_end_time.getText().toString();
                String project_name = et_project_name.getText().toString();
                String hashtagset = et_hashtagset.getText().toString();
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                if (!start.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    SweetAlertDialog datewrong = new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.cancel();
                    datewrong.setTitleText("Oops...")
                            .setContentText("Please check the start date")
                            .show();
                } else if (!end.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    SweetAlertDialog datewrong = new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.cancel();
                    datewrong.setTitleText("Oops...")
                            .setContentText("Please check the end date")
                            .show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            try {

                                Class.forName("com.mysql.jdbc.Driver");
                                Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
                                String sql = "Insert into project (id,project_name,hashtag_set,time_period,username,result_rank,result_count) values (default,?,?,?,?,'not analyzed yet','not analyzed yet')";
                                PreparedStatement ps = conn.prepareStatement(sql);
                                ps.setObject(1, project_name);
                                ps.setObject(2, hashtagset);
                                ps.setObject(3, start + "-->" + end);
                                ps.setObject(4, username);
                                int result = ps.executeUpdate();
                                if (result != 0) {
                                    SweetAlertDialog success = new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                    pDialog.cancel();
                                    success.setTitleText("Good job!")
                                            .setContentText("create success!")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    CreateProjectActivity.this.finish();
                                                    success.cancel();
                                                }
                                            })
                                            .show();
                                } else {
                                    pDialog.cancel();
                                    new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Create failed!")
                                            .show();
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                                pDialog.cancel();
                                new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Create failed!")
                                        .show();
                            }
                            Looper.loop();

                        }
                    }).start();


                }


            }
        });
    }
}
