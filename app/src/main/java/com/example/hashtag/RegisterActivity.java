package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * This class is used to define the register function
 *
 * @author Yang Haoran
 */
public class RegisterActivity extends AppCompatActivity {
    private Button btn_register;
    private Onclick onclick = new Onclick();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(onclick);
    }

    /**
     * this class is used to encapsulate the onclick function
     *
     * @author Yang Haoran
     */
    private class Onclick implements View.OnClickListener {
        Intent intent = new Intent();

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_register:
                    SweetAlertDialog pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("registering...");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    EditText et_phoneNumber = findViewById(R.id.et_phone);
                    String phoneNumber = et_phoneNumber.getText().toString();
                    EditText et_username = findViewById(R.id.et_username);
                    String username = et_username.getText().toString();
                    EditText et_password = findViewById(R.id.et_password);
                    String password = et_password.getText().toString();
                    EditText et_password_confirm = findViewById(R.id.et_password_confirm);
                    String password_confirm = et_password_confirm.getText().toString();
                    RadioGroup group = findViewById(R.id.sex_group);
                    RadioButton btn_sex = findViewById(group.getCheckedRadioButtonId());
                    String sex = btn_sex.getText().toString();
                    //filter the input of the user
                    if (!phoneNumber.matches("^1\\d{10}$")) {
                        pDialog.cancel();
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please enter the right phone number!")
                                .show();
                        break;
                    }

                    if (username.equals("")) {
                        pDialog.cancel();
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Please input username!")
                                .show();
                        break;
                    }
                    if (!password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")) {
                        pDialog.cancel();
                        String pw = "Please check your password!";
                        SweetAlertDialog diapass = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE);
                        diapass.setTitleText("Oops...")
                                .setContentText(pw)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("Must contain")
                                                .setContentText("digit,letter.(length from 8-16) ")
                                                .show();
                                        diapass.cancel();

                                    }
                                })
                                .show();
                        break;
                    }
                    if (!password.equals(password_confirm)) {
                        pDialog.cancel();
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Confirm your password!")
                                .show();
                        break;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            try {

                                Class.forName("com.mysql.jdbc.Driver");
                                Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
                                String sql = "Insert into user (id,username,password,phone_number,sex) values (default,?,?,?,?)";
                                PreparedStatement ps = conn.prepareStatement(sql);
                                ps.setObject(1, username);
                                ps.setObject(2, password);
                                ps.setObject(3, phoneNumber);
                                ps.setObject(4, sex);
                                int result = ps.executeUpdate();
                                if (result == 0) {
                                    pDialog.cancel();
                                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Oops...")
                                            .setContentText("Username exists!")
                                            .show();
                                }
                                pDialog.cancel();
                                SweetAlertDialog success = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                success.setTitleText("Good job!")
                                        .setContentText("Register success !")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                intent = new Intent(RegisterActivity.this, UserHomeActivity.class);
                                                intent.putExtra("username", username);
                                                startActivity(intent);
                                                success.cancel();
                                            }
                                        })
                                        .show();
                            } catch (Exception e) {
                                System.out.println(e);
                                pDialog.cancel();
                                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Username exists!")
                                        .show();
                            }
                            Looper.loop();

                        }
                    }).start();
                    break;
            }
        }
    }
}
