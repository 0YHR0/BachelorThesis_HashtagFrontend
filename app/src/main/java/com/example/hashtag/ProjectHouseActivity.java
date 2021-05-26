package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * This class defines the function of the project house
 *
 * @author Yang Haoran
 */
public class ProjectHouseActivity extends AppCompatActivity {
    private Button btn_search;
    private Button btn_create;
    private Button btn_show;
    private RelativeLayout layout;
    ResultSet result = null;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_house);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        TextView projectHouse = findViewById(R.id.tv_project);
        layout = findViewById(R.id.layout);
        projectHouse.setText(username + "'s project house");
        btn_search = findViewById(R.id.btn_search);
        btn_create = findViewById(R.id.btn_create);
        btn_show = findViewById(R.id.btn_show);

        //get the project
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
                    String sql = "select project_name from project where username=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setObject(1, username);
                    result = ps.executeQuery();
                } catch (Exception e) {
                    System.out.println(e);
                }

                Looper.loop();

            }
        }).start();

        //add the listener for the create button
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectHouseActivity.this, CreateProjectActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        //add the listener for the search button
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(ProjectHouseActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Searching...");
                pDialog.setCancelable(true);
                pDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        try {
                            EditText et_hashtag_search = findViewById(R.id.et_search_hashtag);
                            String hashtag = et_hashtag_search.getText().toString();
                            String rank = "default";
                            String count = "default";
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
                            String sql_rank = "select count(*) from hashtag_status where count>=(select count from hashtag_status where hashtag=?)";
                            String sql_count = "select count from hashtag_status where hashtag=?";
                            PreparedStatement ps_rank = conn.prepareStatement(sql_rank);
                            PreparedStatement ps_count = conn.prepareStatement(sql_count);
                            ps_count.setObject(1, hashtag);
                            ps_rank.setObject(1, hashtag);
                            ResultSet result_rank = ps_rank.executeQuery();
                            //has result
                            if (result_rank.next()) {
                                rank = result_rank.getString(1);
                                result_rank.close();
                                ResultSet result_count = ps_count.executeQuery();
                                if (result_count.next()) {
                                    count = result_count.getString(1);
                                }
                                pDialog.cancel();
                                SweetAlertDialog success = new SweetAlertDialog(ProjectHouseActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                                success.setTitleText("Success!")
                                        .setContentText("The rank of '" + hashtag + "'" + " is: " + rank + "\n The count of '" + hashtag + "' is: " + count + " !")
                                        .show();

                            }
                            // no result
                            else {
                                result_rank.close();
                                pDialog.cancel();
                                new SweetAlertDialog(ProjectHouseActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("no such hashtag!")
                                        .show();

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Looper.loop();
                    }
                }).start();
            }
        });
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setProject();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * set the existing project
     */
    public void setProject() throws SQLException {
// get the project when the page returns to this activity
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
                    String sql = "select project_name from project where username=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setObject(1, username);
                    result = ps.executeQuery();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }).start();
        //set the button to the view
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        RelativeLayout layoutR = new RelativeLayout(this);
        int i = 0;
        int j = -1;
        List<Button> btn = new ArrayList<Button>();
        while (result.next()) {
            if (i % 2 == 0) {
                j++;
            }
            Button button = new Button(this);
            button.setId(2000 + i);
            button.setText(result.getString(1));
            button.setBackgroundResource(R.drawable.btn_project);
            button.setAllCaps(false);
            RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams((width - 50) / 2, 200);  //set the size of button
            btParams.leftMargin = 15 + ((width - 50) / 2 + 20) * (i % 2);   //x axis
            btParams.topMargin = 20 + 220 * j;   //y axis
            layoutR.addView(button, btParams);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProjectHouseActivity.this, ProjectResultActivity.class);
                    startActivity(intent);
                }
            });
            i++;
        }
        layout.addView(layoutR);


    }


}
