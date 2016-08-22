package com.iammsun.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.iammsun.navigator.annotation.Nav;

/**
 * Created by sunmeng on 16/8/16.
 */
public class RuntimeNav {

    private static final String TAG = "RuntimeNav";

    public static void load(Context context) {
        Navigator.clear();
        try {
            String pkgName = context.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkgName,
                    PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activity : packageInfo.activities) {
                loadClass(pkgName, activity);
            }
        } catch (PackageManager.NameNotFoundException exception) {
            Log.d(TAG, "failed to get activities", exception);
        }
    }

    private static void loadClass(String pkgName, ActivityInfo activityInfo) {
        Class<?> activityClass;
        try {
            activityClass = Class.forName(activityInfo.name);
            if (!activityClass.isAnnotationPresent(Nav.class)) {
                return;
            }
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "failed to get activity class", e);
            return;
        }
        Nav nav = activityClass.getAnnotation(Nav.class);
        for (String format : nav.value()) {
            ExtraTypes extraTypes = new ExtraTypes();
            extraTypes.setBooleanExtra(nav.booleanParams());
            extraTypes.setByteExtra(nav.byteParams());
            extraTypes.setCharExtra(nav.charParams());
            extraTypes.setDataExtra(nav.dataParams());
            extraTypes.setDoubleExtra(nav.doubleParams());
            extraTypes.setFloatExtra(nav.floatParams());
            extraTypes.setIntExtra(nav.intParams());
            extraTypes.setLongExtra(nav.longParams());
            extraTypes.setShortExtra(nav.shortParams());
            extraTypes.setStringExtra(nav.stringParams());
            Navigator.map(format, pkgName, activityInfo.name, extraTypes);
        }
    }
}
