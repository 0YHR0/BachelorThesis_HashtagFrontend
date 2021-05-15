package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is used to show the home of the user
 */
public class UserHomeActivity extends AppCompatActivity {
    private Button btn_logout;
    private Button btn_bar;
    private Button btn_bar_weekly;
    private Button btn_bar_monthly;
    private Button btn_bar_daily;
    private Onclick onclick = new Onclick();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        TextView tv_username = findViewById(R.id.tv_username);
        tv_username.setText("Welcome to your hashtag home, " + username + " !");
        btn_logout = findViewById(R.id.btn_logout);
        btn_bar = findViewById(R.id.btn_bar);
        btn_bar_weekly = findViewById(R.id.btn_bar_weekly);
        btn_bar_monthly = findViewById(R.id.btn_bar_monthly);
        btn_bar_daily = findViewById(R.id.btn_bar_daily);
        setOnclink();
    }

    /**
     * set the click listener to the button
     */
    public void setOnclink() {

        btn_bar.setOnClickListener(onclick);
        btn_bar_weekly.setOnClickListener(onclick);
        btn_bar_monthly.setOnClickListener(onclick);
        btn_bar_daily.setOnClickListener(onclick);
        btn_logout.setOnClickListener(onclick);
    }

    /**
     * This class is used to set the onclick to the button
     */
    private class Onclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent loading = new Intent(UserHomeActivity.this, LoadingActivity.class);
            switch (v.getId()) {
                case R.id.btn_logout:
                    AlertDialog.Builder confirm = new AlertDialog.Builder(UserHomeActivity.this);
                    confirm.setTitle("Confirm");
                    confirm.setMessage("Are you sure to logout?");
                    confirm.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainpage = new Intent(UserHomeActivity.this, MainActivity.class);
                            startActivity(mainpage);
                        }
                    });
                    confirm.setNegativeButton("No!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    confirm.show();
                    break;
                case R.id.btn_bar:
                    Toast.makeText(UserHomeActivity.this, "analyzing overall hashtag... ", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "overall");
                    startActivity(loading);
                    break;
                case R.id.btn_bar_weekly:
                    Toast.makeText(UserHomeActivity.this, "analyzing weekly hashtag...", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "week");
                    startActivity(loading);
                    break;
                case R.id.btn_bar_monthly:
                    Toast.makeText(UserHomeActivity.this, "analyzing monthly hashtag...", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "month");
                    startActivity(loading);
                    break;
                case R.id.btn_bar_daily:
                    Toast.makeText(UserHomeActivity.this, "analyzing daily hashtag...", Toast.LENGTH_SHORT).show();
                    loading.putExtra("type", "day");
                    startActivity(loading);
                    break;


            }

        }
    }
}
