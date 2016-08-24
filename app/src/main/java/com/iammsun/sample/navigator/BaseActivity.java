package com.iammsun.sample.navigator;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by sunmeng on 16/8/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        TextView textView = (TextView) findViewById(R.id.content);
        Bundle bundle = getIntent().getExtras();
        Uri uri = bundle.getParcelable("request");
        textView.append("request=" + uri);
        textView.append("\n\nString:\nsource=" + bundle.getString("source"));
        if (bundle.containsKey("index")) {
            textView.append("\n\nint:\nindex=" + bundle.getInt("index"));
        }
        byte[] data = bundle.getByteArray("data");
        if (data != null) {
            Parcel p = Parcel.obtain();
            p.unmarshall(data, 0, data.length);
            p.setDataPosition(0);
            ParcelInfo info = new ParcelInfo(p);
            p.recycle();
            textView.append("\n\nbyte[]:\ndata=" + info);
        }
        Parcelable parcelable = bundle.getParcelable("parcel");
        if (parcelable != null) {
            textView.append("\n\nparcelable:\nparcel=" + parcelable);
        }
    }
}
