package com.iammsun.navigator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by sunmeng on 16/8/23.
 */
public class RouterActivity extends AppCompatActivity {

    private NavCallback mNavCallback = new NavCallback() {
        @Override
        public void notFound(Context context, Uri uri) {
            Toast.makeText(RouterActivity.this, "activity not found: " + uri, Toast.LENGTH_SHORT)
                    .show();
            finish();
        }

        @Override
        public void beforeOpen(Context context, Uri uri) {

        }

        @Override
        public void onNavigate(Context context, Uri uri, Intent intent) {

        }

        @Override
        public void afterOpen(Context context, Uri uri) {
        }
    };

    private static final int REQUEST_CODE_ROUTER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        if (data == null) {
            finish();
            return;
        }
        String format = data.getQuery();
        if (format == null) {
            finish();
            return;
        }
        new Navigator.Builder(this).setCallback(mNavCallback).build().openForResult(format,
                REQUEST_CODE_ROUTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_ROUTER == requestCode) {
            setResult(resultCode, data);
            finish();
        }
    }
}
