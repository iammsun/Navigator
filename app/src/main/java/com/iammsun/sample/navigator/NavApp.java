package com.iammsun.sample.navigator;

import android.app.Application;

import com.iammsun.navigator.RuntimeNav;

/**
 * Created by sunmeng on 16/8/16.
 */
public class NavApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.RUNTIME_ANNOTATION) {
            RuntimeNav.load(this);
        }
    }
}
