package com.wisedrive.customerapp;

import android.app.Application;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.google.firebase.FirebaseApp;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        AppsFlyerConversionListener conversionDataListener =
                new AppsFlyerConversionListener() {


                    @Override
                    public void onConversionDataSuccess(Map<String, Object> map) {

                    }

                    @Override
                    public void onConversionDataFail(String s) {

                    }

                    @Override
                    public void onAppOpenAttribution(Map<String, String> map) {

                    }

                    @Override
                    public void onAttributionFailure(String s) {

                    }

                };


        AppsFlyerLib.getInstance().init("irL8V75q2hCLCnQpPUmNxU", null, this);
        AppsFlyerLib.getInstance().start(this);
       // AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), "eventName", eventValues);

    }
}
