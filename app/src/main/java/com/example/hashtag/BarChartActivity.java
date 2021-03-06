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
 * This class defines the activity when hashtag overall is clicked
 *
 * @author Yang Haoran
 */

public class BarChartActivity extends AppCompatActivity {
    public WebView bar;
    public String data = "init";


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("activity","activity");
        Thread getdata = new Thread() {
            @Override
            public void run() {
                try {
                    while(true){
                        data = getData();
                        Thread.sleep(5000);
                        Log.e("from database", data);
                    }
//                    Log.e("ttt","ssss");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        getdata.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        bar = findViewById(R.id.web_bar);
        bar.getSettings().setJavaScriptEnabled(true);
//        bar.getSettings().setDomStorageEnabled(true);
        bar.setWebChromeClient(new WebChromeClient());
        bar.setWebViewClient(new MyWebViewClient());
        bar.addJavascriptInterface(this, "java");
        bar.loadUrl("file:///android_asset/bar.html");

//        bar.loadUrl("javascript:giveData(\"#hashtag1#999#hashtag2#22#hashtag3#111\")");


    }

    /**
     * call by js
     *
     * @return the data from the database
     */
    @JavascriptInterface
    public String getDataFromJava(){
        Log.e("getdata","getdata");
        return data;

    }


    /**
     * Get the total hashtag count from the database
     * @return the hashtag and the count
     * @throws Exception
     */
    public String getData() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(getResources().getString(R.string.mysqlURL), getResources().getString(R.string.user), getResources().getString(R.string.password));
        String sql = "select hashtag,count from hashtag_status order by count";
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
     * self defines the webClient to call the js
     * @author Yang Haoran
     */
    class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


            if(url.contains("bar.html")){
                try {

                    //This is for test the data to the webview
//                    view.loadUrl("javascript:giveData(\"#hashtag1#999#hashtag2#22#hashtag3#111\")");
//                    Thread.sleep(3000);
                    //-------------
                    view.loadUrl("javascript:giveData(\""+data+"\")");
                    //-------------


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    {
        BarChartActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("UI",data);
            }
        });
    }
}
