package com.iammsun.navigator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by sunmeng on 16/8/15.
 */
public interface NavCallback {

    void beforeOpen(Context context, Uri uri);

    void onNavigate(Context context, Uri uri, Intent intent);

    void afterOpen(Context context, Uri uri);

    void notFound(Context context, Uri uri);
}
