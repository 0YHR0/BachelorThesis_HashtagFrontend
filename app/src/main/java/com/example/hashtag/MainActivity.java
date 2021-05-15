package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * This class defines the main entrance of the App
 *
 * @author Yang Haoran
 */

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button btn_bar;
    private Button btn_bar_weekly;
    private Button btn_bar_monthly;
    private Button btn_bar_daily;
    private Button btn_main_login;
    private Onclick onclick = new Onclick();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.view_1);
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv.getPaint().setAntiAlias(true);
        btn_bar = findViewById(R.id.btn_bar);
        btn_bar_weekly = findViewById(R.id.btn_bar_weekly);
        btn_bar_monthly = findViewById(R.id.btn_bar_monthly);
        btn_bar_daily = findViewById(R.id.btn_bar_daily);
        btn_main_login = findViewById(R.id.btn_main_login);
        setOnclink();
    }

    /**
     * set the click listener to the button
     */
    public void setOnclink(){

        btn_bar.setOnClickListener(onclick);
        btn_bar_weekly.setOnClickListener(onclick);
        btn_bar_monthly.setOnClickListener(onclick);
        btn_bar_daily.setOnClickListener(onclick);
        btn_main_login.setOnClickListener(onclick);
    }

    /**
     * this class is used to encapsulate the onclick function
     * @author Yang Haoran
     */

    private class Onclick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent= new Intent();
            Intent loading = new Intent(MainActivity.this, LoadingActivity.class);
            switch (v.getId()) {

                case R.id.btn_bar:
                    Toast.makeText(MainActivity.this, "analyzing overall hashtag... ", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "overall");
                    startActivity(loading);
                    break;
                case R.id.btn_bar_weekly:
                    Toast.makeText(MainActivity.this, "analyzing weekly hashtag...", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "week");
                    startActivity(loading);
                    break;
                case R.id.btn_bar_monthly:
                    Toast.makeText(MainActivity.this, "analyzing monthly hashtag...", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "month");
                    startActivity(loading);
                    break;
                case R.id.btn_bar_daily:
                    Toast.makeText(MainActivity.this, "analyzing daily hashtag...", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "day");
                    startActivity(loading);
                    break;
                case R.id.btn_main_login:
                    Toast.makeText(MainActivity.this, "Login...", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;


            }

        }
    }
}

