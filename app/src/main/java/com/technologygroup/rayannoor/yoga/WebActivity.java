package com.technologygroup.rayannoor.yoga;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

    private String payUrl;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        payUrl = getIntent().getStringExtra("payUrl");

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(payUrl);

//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setHorizontalScrollBarEnabled(false);
//        webView.addJavascriptInterface(new MyJavaScriptInterface(), "android");
//
//        webView.setWebViewClient(new MyBrowser());
//        webView.getSettings().setLoadsImagesAutomatically(true);
//
//        String url = payUrl;
//
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.loadUrl(url);

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:window.android.onUrlChange(window.location.href);");
        }

    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        public void onUrlChange(String url) {
            Log.d("hydrated", "onUrlChange" + url);
            Toast.makeText(WebActivity.this, "url:" + url, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
