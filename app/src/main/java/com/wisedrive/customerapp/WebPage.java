package com.wisedrive.customerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.wisedrive.customerapp.commonclasses.SPHelper;

public class WebPage extends AppCompatActivity {

    WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);
        mWebview=findViewById(R.id.mWebview);
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        mWebview.getSettings().setLoadWithOverviewMode(true);
        mWebview.getSettings().setUseWideViewPort(true);
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.clearCache(true);

        if(SPHelper.comingfrom.equals("tnc")){
            mWebview .loadUrl(SPHelper.tnc);
        }else if(SPHelper.comingfrom.equals("pp")){
            mWebview .loadUrl(SPHelper.privacypolicy);
        }else if(SPHelper.comingfrom.equals("copy")){
                mWebview .loadUrl(SPHelper.copyrights);
        }else if(SPHelper.comingfrom.equals("vp")){
            mWebview .loadUrl(SPHelper.viewpolicy);
        }else if(SPHelper.comingfrom.equals("wp")){
            mWebview .loadUrl(SPHelper.warr_policy);
        }else if(SPHelper.comingfrom.equals("bbg")){
            mWebview .loadUrl(SPHelper.bbg_policy);
        }

    }
}