package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        et_start_time = findViewById(R.id.et_start_time);
        et_end_time = findViewById(R.id.et_end_time);
        btn_create_confirm = findViewById(R.id.btn_create_confirm);
        btn_create_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = et_start_time.getText().toString();
                String end = et_end_time.getText().toString();
                if (!start.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    SweetAlertDialog datewrong = new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.ERROR_TYPE);
                    datewrong.setTitleText("Oops...")
                            .setContentText("Please check the start date")
                            .show();
                } else if (!end.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    SweetAlertDialog datewrong = new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.ERROR_TYPE);
                    datewrong.setTitleText("Oops...")
                            .setContentText("Please check the end date")
                            .show();
                } else {
                    SweetAlertDialog success = new SweetAlertDialog(CreateProjectActivity.this, SweetAlertDialog.SUCCESS_TYPE);
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
                }


            }
        });
    }
}
