package com.example.hashtag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * This class defines the function of the project analyzing result
 *
 * @author Yang Haoran
 */
public class ProjectResultActivity extends AppCompatActivity {
    private WebView webView_rank;
    private WebView webView_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_result);
        webView_rank = findViewById(R.id.web_project_result_rank);
        webView_amount = findViewById(R.id.web_peoject_result_amount);
        webView_rank.getSettings().setJavaScriptEnabled(true);
        webView_rank.setWebChromeClient(new WebChromeClient());
        webView_rank.setWebViewClient(new WebViewClient());
        webView_amount.getSettings().setJavaScriptEnabled(true);
        webView_amount.setWebChromeClient(new WebChromeClient());
        webView_amount.setWebViewClient(new WebViewClient());
        webView_rank.loadUrl("file:///android_asset/projectResultRank.html");
        webView_amount.loadUrl("file:///android_asset/projectResultAmount.html");


    }
}
