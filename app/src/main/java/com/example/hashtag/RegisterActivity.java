package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

                    EditText et_phoneNumber = findViewById(R.id.et_phone);
                    String phoneNumber = et_phoneNumber.getText().toString();
                    EditText et_username = findViewById(R.id.et_username);
                    String username = et_username.getText().toString();
                    EditText et_password = findViewById(R.id.et_password);
                    String password = et_password.getText().toString();
                    RadioGroup group = findViewById(R.id.sex_group);
                    RadioButton btn_sex = findViewById(group.getCheckedRadioButtonId());
                    String sex = btn_sex.getText().toString();
                    if (!phoneNumber.matches("^1\\d{10}$")) {
                        Toast.makeText(RegisterActivity.this, "Please enter the right phone number!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (!password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$")) {
                        Toast.makeText(RegisterActivity.this, "Please check your password! (Must contain digit,letter. Length from 8-16) ", Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (username == "") {
                        Toast.makeText(RegisterActivity.this, "Please input username!", Toast.LENGTH_LONG).show();
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
                                    Toast.makeText(RegisterActivity.this, "Username exists!", Toast.LENGTH_LONG).show();
                                }
                                Toast.makeText(RegisterActivity.this, "registering... ", Toast.LENGTH_SHORT).show();
                                intent = new Intent(RegisterActivity.this, UserHomeActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                            } catch (Exception e) {
                                System.out.println(e);
                                Toast.makeText(RegisterActivity.this, "Username exists!", Toast.LENGTH_LONG).show();
                            }
                            Looper.loop();

                        }
                    }).start();
                    break;
            }
        }
    }
}
