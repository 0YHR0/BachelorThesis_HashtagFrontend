package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hashtag.echarts.EchartOptionUtil;
import com.example.hashtag.echarts.EchartView;

public class WebViewActivity extends AppCompatActivity {
//    public WebView webView;
    public EchartView chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        chart = findViewById(R.id.web_01);
        chart.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //最好在h5页面加载完毕后再加载数据，防止html的标签还未加载完成，不能正常显示
                refreshLineChart();
            }
        });



        /*webView = findViewById(R.id.web_01);
        //加载本地HTML
//        webView.loadUrl("file:///android_asset/echarts.html");
        //加载网络url
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://m.baidu.com");*/

    }
    private void refreshLineChart(){
        Object[] x = new Object[]{
                "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
        };
        Object[] y = new Object[]{
                820, 932, 901, 934, 1290, 1330, 1320
        };
        chart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y));
    }

}
