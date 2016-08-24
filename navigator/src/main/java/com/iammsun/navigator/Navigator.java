package com.iammsun.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunmeng on 16/8/15.
 */
public class Navigator implements NavCallback {

    private static List<Mapping> mappings = new ArrayList<>();

    private Context context;
    private Bundle extras;
    private NavCallback callback;

    @Override
    public void notFound(Context context, Uri uri) {
        if (callback == null) {
            return;
        }
        callback.notFound(context, uri);
    }

    @Override
    public void beforeOpen(Context context, Uri uri) {
        if (callback == null) {
            return;
        }
        callback.beforeOpen(context, uri);
    }

    @Override
    public void onNavigate(Context context, Uri uri, Intent intent) {
        if (callback == null) {
            return;
        }
        callback.onNavigate(context, uri, intent);
    }

    @Override
    public void afterOpen(Context context, Uri uri) {
        if (callback == null) {
            return;
        }
        callback.afterOpen(context, uri);
    }

    public static class Builder {
        private Context context;
        private Bundle extras;
        private NavCallback callback;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setExtras(Bundle extras) {
            this.extras = extras;
            return this;
        }

        public Builder setCallback(NavCallback callback) {
            this.callback = callback;
            return this;
        }

        public Navigator build() {
            Navigator nav = new Navigator();
            nav.context = context;
            nav.extras = extras;
            nav.callback = callback;
            return nav;
        }
    }

    private static void initIfNeed() {
        if (!mappings.isEmpty()) {
            return;
        }
        RouterMapping.map();
    }

    public static void map(String format, String pkgName, String clsName, ExtraTypes extraTypes) {
        mappings.add(new Mapping(format, pkgName, clsName, extraTypes));
    }

    public static void clear() {
        mappings.clear();
    }

    public void open(String url) {
        open(Uri.parse(url));
    }

    public void open(Uri uri) {
        openForResult(uri, -1);
    }

    public void openForResult(String url, int requestCode) {
        openForResult(Uri.parse(url), requestCode);
    }

    public void openForResult(Uri uri, int requestCode) {
        beforeOpen(context, uri);
        Intent intent = nav(uri);
        if (intent != null) {
            onNavigate(context, uri, intent);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
            afterOpen(context, uri);
        } else {
            notFound(context, uri);
        }
    }

    private Intent nav(Uri uri) {
        initIfNeed();
        for (Mapping mapping : mappings) {
            if (mapping.match(uri)) {
                Intent intent = new Intent();
                intent.setComponent(mapping.getComponentName(context));
                Bundle uriBundle = mapping.parseExtras(uri);
                if (uriBundle != null && !uriBundle.isEmpty()) {
                    intent.putExtras(uriBundle);
                }
                if (extras != null && !extras.isEmpty()) {
                    intent.putExtras(extras);
                }
                return intent;
            }
        }
        return null;
    }
}
