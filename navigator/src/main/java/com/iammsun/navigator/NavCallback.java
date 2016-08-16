package com.iammsun.navigator;

import android.content.Context;
import android.net.Uri;

/**
 * Created by sunmeng on 16/8/15.
 */
public interface NavCallback {

    void notFound(Context context, Uri uri);

    void beforeOpen(Context context, Uri uri);

    void afterOpen(Context context, Uri uri);

    void error(Context context, Uri uri, Throwable e);
}
