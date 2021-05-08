package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class defines the loading page
 *
 * @author Yang Haoran
 */
public class LoadingActivity extends AppCompatActivity {
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        textView = findViewById(R.id.text_load);
        Intent from = getIntent();
        String type = from.getStringExtra("type");
        if (type.equals("month")) jumpToMonth();
        if (type.equals("overall")) jumpToTotal();
        if (type.equals("week")) jumpToWeek();
        if (type.equals("day")) jumpToDay();

    }

    /**
     * Jump to daily hashtag graph
     */
    public void jumpToDay() {
        Intent jumpTo = new Intent(LoadingActivity.this, BarchartDailyActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(jumpTo);
                LoadingActivity.this.finish();
            }
        };
        timer.schedule(task, 1000);

    }

    /**
     * Jump to monthly hashtag graph
     */
    public void jumpToMonth() {
        Intent jumpTo = new Intent(LoadingActivity.this, BarchartMonthlyActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(jumpTo);
                LoadingActivity.this.finish();
            }
        };
        timer.schedule(task, 1000);

    }

    /**
     * Jump to weekly hashtag graph
     */
    public void jumpToWeek() {
        Intent jumpTo = new Intent(LoadingActivity.this, BarchartWeeklyActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(jumpTo);
                LoadingActivity.this.finish();
            }
        };
        timer.schedule(task, 1000);

    }

    /**
     * Jump to total hashtag graph
     */
    public void jumpToTotal() {
        Intent jumpTo = new Intent(LoadingActivity.this, BarChartActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(jumpTo);
                LoadingActivity.this.finish();
            }
        };
        timer.schedule(task, 1000);

    }


}
