package com.custom.browser;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    static {
        // Rust ডাইনামিক লাইব্রেরি লোড করা
        System.loadLibrary("browser_core");
    }

    // Rust নেটিভ ফাংশনাল ইন্টারফেস
    public native void startRustEngine(int port);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ব্যাকগ্রাউন্ড থ্রেডে Rust নেটওয়ার্ক ইঞ্জিন চালু করা
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRustEngine(8080);
            }
        }).start();

        // কাস্টম ব্রাউজার ইন্টারফেস (WebView)
        WebView webView = new WebView(this);
        setContentView(webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        
        // লোকাল ডিক্রিপশন ইঞ্জিনের মাধ্যমে সাইট লোড করা
        webView.loadUrl("http://127.0.0.1:8080");
    }
}
