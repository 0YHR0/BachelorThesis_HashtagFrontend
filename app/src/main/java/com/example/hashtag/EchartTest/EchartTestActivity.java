package com.example.hashtag.EchartTest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hashtag.R;
import com.example.hashtag.echarts.EchartView;

public class EchartTestActivity extends AppCompatActivity {

    public EchartView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_echart_test);
        web = findViewById(R.id.web_test);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.setWebViewClient(new MyWebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl("file:///android_asset/radar.html");

    }
    class MyWebViewClient extends WebViewClient{
//        private Boolean flag = false;
        @Override
        public void onPageFinished(WebView view, String url) {
            if(url.contains("radar.html")){
//                web.loadUrl("javascript:alert('111')");
            }


            super.onPageFinished(view, url);

        }
    }

}
