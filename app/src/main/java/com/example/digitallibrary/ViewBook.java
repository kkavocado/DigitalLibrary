package com.example.digitallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class ViewBook extends AppCompatActivity {

    WebView webView;
    String bookurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        bookurl=getIntent().getStringExtra("bookurl");

        webView=findViewById(R.id.bookWebView);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl(bookurl);
    }
}