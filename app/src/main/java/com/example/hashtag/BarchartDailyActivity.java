package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class defines the activity when hashtag daily is clicked
 *
 * @author Yang Haoran
 */
public class BarchartDailyActivity extends AppCompatActivity {
    public WebView bar;
    public String data = "init";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //first get data
        Thread getdata = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        data = getData();
                        Thread.sleep(5000);
                        Log.e("from database", data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        getdata.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart_daily);
        bar = findViewById(R.id.web_barDay);
        bar.getSettings().setJavaScriptEnabled(true);
        bar.setWebChromeClient(new WebChromeClient());
        bar.setWebViewClient(new MyWebViewClient());
        bar.addJavascriptInterface(this, "java");
        bar.loadUrl("file:///android_asset/bar.html");
    }

    /**
     * call by js
     *
     * @return the data from the database
     */
    @JavascriptInterface
    public String getDataFromJava() {
        Log.e("getdata", "getdata");
        return data;

    }

    /**
     * Get the total hashtag count from the database
     *
     * @return the hashtag and the count
     * @throws Exception
     */
    public String getData() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
        String sql = "select hashtag,count_daily from hashtag_status order by count_daily";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        String result = "";
        while (resultSet.next()) {
            result = result + "#" + resultSet.getString(1);
            result = result + "#" + resultSet.getString(2);

        }

        return result;
    }

    /**
     * This class is used to load the page of html
     */
    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


            if (url.contains("bar.html")) {
                try {

                    view.loadUrl("javascript:giveData(\"" + data + "\")");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    {
        BarchartDailyActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("UI", data);
            }
        });
    }


}

