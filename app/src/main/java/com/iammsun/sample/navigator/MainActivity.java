package com.iammsun.sample.navigator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iammsun.navigator.Navigator;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (!isOverrideUrl(uri)) {
                    return false;
                }
                if ("native".equals(uri.getQuery())) {
                    override2Activity();
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                }
                return true;
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    private static boolean isOverrideUrl(Uri uri) {
        return "nav".equals(uri.getScheme()) && "go_native".equals(uri.getHost());
    }

    private static ParcelInfo getParcelInfo() {
        ParcelInfo parcelInfo = new ParcelInfo();
        parcelInfo.setNumIid(123);
        parcelInfo.setOuterId("out?Id=11&");
        parcelInfo.setPropertiesName("propertiesName");
        parcelInfo.setQuantity(321);
        parcelInfo.setSkuId(111);
        return parcelInfo;
    }

    private void override2Activity() {
        Bundle extras = new Bundle();
        extras.putParcelable("parcel", getParcelInfo());
        new Navigator.Builder(this).setExtras(extras).build().open
                ("app://activities/a?source=override2Activity");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
