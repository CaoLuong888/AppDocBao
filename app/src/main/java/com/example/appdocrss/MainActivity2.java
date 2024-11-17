package com.example.appdocrss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity2 extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webView= (WebView) findViewById(R.id.webView);
        Intent intent=getIntent();
        String duonglink =intent.getStringExtra("link");
        //Lấy dữ liệu được gửi từ Intent trước đó thông qua key là "link". Dữ liệu này là đường link (URL) mà chúng ta muốn hiển thị trên WebView.
        webView.loadUrl (duonglink);
        webView.setWebViewClient (new WebViewClient());
        //mở các liên kết trong WebView thay vì mở trong trình duyệt mặc định của thiết bị.
    }
}