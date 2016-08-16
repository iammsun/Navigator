package com.iammsun.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunmeng on 16/8/15.
 */
public class Navigator {

    private static List<Mapping> mappings = new ArrayList<>();

    private static void initIfNeed() {
        if (!mappings.isEmpty()) {
            return;
        }
        RouterMapping.map();
    }

    public static void map(String format, Class<? extends Activity> activity, ExtraTypes
            extraTypes) {
        mappings.add(new Mapping(format, activity, extraTypes));
    }

    public static void clear() {
        mappings.clear();
    }

    public static boolean open(Context context, String url) {
        return open(context, Uri.parse(url));
    }

    public static boolean open(Context context, String url, NavCallback callback) {
        return open(context, Uri.parse(url), callback);
    }

    public static boolean open(Context context, Uri uri) {
        return open(context, uri, null);
    }

    public static boolean open(Context context, Uri uri, NavCallback callback) {
        return openForResult(context, uri, callback, -1);
    }

    public static boolean openForResult(Context context, String url, int requestCode) {
        return open(context, Uri.parse(url));
    }

    public static boolean openForResult(Context context, Uri uri, int requestCode) {
        return openForResult(context, uri, null, requestCode);
    }

    public static boolean openForResult(Context context, Uri uri, NavCallback callback, int
            requestCode) {
        boolean success = false;
        try {
            if (callback != null) {
                callback.beforeOpen(context, uri);
            }
            success = doOpen(context, uri, requestCode);
            if (callback != null) {
                if (success) {
                    callback.afterOpen(context, uri);
                } else {
                    callback.notFound(context, uri);
                }
            }
        } catch (Throwable e) {
            if (callback != null) {
                callback.error(context, uri, e);
            }
        }
        return success;
    }

    private static boolean doOpen(Context context, Uri uri, int requestCode) {
        initIfNeed();
        for (Mapping mapping : mappings) {
            if (mapping.match(uri)) {
                Intent intent = new Intent(context, mapping.getActivity());
                intent.putExtras(mapping.parseExtras(uri));
                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    ((Activity) context).startActivityForResult(intent, requestCode);
                }
                return true;
            }
        }
        return false;
    }
}
