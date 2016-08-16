package com.iammsun.navigator;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by sunmeng on 16/8/15.
 */
public class Mapping {

    private static final String TAG = "Mapping";

    private final Uri format;
    private final Class<? extends Activity> activity;
    private final ExtraTypes extraTypes;

    private static final String EXTRA_REQUEST = "request";

    public Mapping(String formatUri, Class<? extends Activity> activity, ExtraTypes extraTypes) {
        if (formatUri == null) {
            throw new NullPointerException("format can not be null");
        }
        if (activity == null) {
            throw new NullPointerException("activity can not be null");
        }
        this.format = Uri.parse(formatUri);
        this.activity = activity;
        this.extraTypes = extraTypes;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    @Override
    public String toString() {
        return String.format("%s => %s", format, activity);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Mapping) {
            Mapping that = (Mapping) o;
            return that.format.equals(((Mapping) o).format);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return format.hashCode();
    }

    public boolean match(Uri uri) {
        return TextUtils.equals(format.getScheme(), uri.getScheme()) && TextUtils.equals(uri
                .getHost(), uri.getHost()) && TextUtils.equals(format.getPath(), uri.getPath());
    }

    public Bundle parseExtras(Uri uri) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_REQUEST, uri);
        List<String> extras = extraTypes.getExtras();
        for (String extra : extras) {
            put(bundle, extra, uri.getQueryParameter(extra));
        }
        return bundle;
    }

    private void put(Bundle bundle, String name, String value) {
        if (value == null) {
            return;
        }
        switch (extraTypes.getType(name)) {
            case ExtraTypes.INT:
                bundle.putInt(name, Integer.parseInt(value));
                break;
            case ExtraTypes.LONG:
                bundle.putLong(name, Long.parseLong(value));
                break;
            case ExtraTypes.BOOL:
                bundle.putBoolean(name, Boolean.parseBoolean(value));
                break;
            case ExtraTypes.SHORT:
                bundle.putShort(name, Short.parseShort(value));
                break;
            case ExtraTypes.FLOAT:
                bundle.putFloat(name, Float.parseFloat(value));
                break;
            case ExtraTypes.DOUBLE:
                bundle.putDouble(name, Double.parseDouble(value));
                break;
            case ExtraTypes.BYTE:
                bundle.putByte(name, Byte.parseByte(value));
                break;
            case ExtraTypes.CHAR:
                bundle.putChar(name, value.charAt(0));
                break;
            case ExtraTypes.STRING:
                bundle.putString(name, value);
                break;
            case ExtraTypes.DATA:
                bundle.putByteArray(name, value.getBytes());
                break;
            default:
                Log.d(TAG, String.format("unknown extra: %s=>$s", name, value));
                break;
        }
    }
}
