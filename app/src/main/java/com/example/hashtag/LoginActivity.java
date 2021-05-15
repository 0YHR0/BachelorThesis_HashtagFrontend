package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class is used to Login
 *
 * @author Yang Haoran
 */
public class LoginActivity extends AppCompatActivity {
    private Button btn_register;
    private Button btn_login;
    private Onclick onclick = new Onclick();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        btn_register.setOnClickListener(onclick);
        btn_login.setOnClickListener(onclick);
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
                    Toast.makeText(LoginActivity.this, "register... ", Toast.LENGTH_SHORT).show();
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btn_login:
                    EditText et_username = findViewById(R.id.et_username);
                    EditText et_password = findViewById(R.id.et_password);
                    String username = et_username.getText().toString();
                    String password = et_password.getText().toString();
                    if (username == "") {
                        Toast.makeText(LoginActivity.this, "Please input your username! ", Toast.LENGTH_LONG).show();
                        break;
                    }
                    if (password == "") {
                        Toast.makeText(LoginActivity.this, "Please input your password! ", Toast.LENGTH_LONG).show();
                        break;
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            try {

                                Class.forName("com.mysql.jdbc.Driver");
                                Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
                                String sql = "select * from user where username=? and password=?";
                                PreparedStatement ps = conn.prepareStatement(sql);
                                ps.setObject(1, username);
                                ps.setObject(2, password);
                                ResultSet result = ps.executeQuery();
                                if (!result.next()) {
                                    Toast.makeText(LoginActivity.this, "Username or password wrong!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login success !!! ", Toast.LENGTH_SHORT).show();
                                    intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                                Toast.makeText(LoginActivity.this, "Username or password wrong!", Toast.LENGTH_LONG).show();
                            }
                            Looper.loop();

                        }
                    }).start();
                    break;

            }
        }
    }
}
