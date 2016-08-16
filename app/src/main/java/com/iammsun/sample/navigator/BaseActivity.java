package com.iammsun.sample.navigator;

import android.os.Bundle;
import android.os.Parcel;
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

        TextView textView = (TextView)findViewById(R.id.content);
        Bundle bundle = getIntent().getExtras();
        textView.append("request=" + bundle.getParcelable("request"));
        textView.append("\n\nparam1=" + bundle.getString("param1"));
        textView.append("\n\nparam2=" + bundle.getInt("param2"));
        byte[] data = bundle.getByteArray("parcel");
        if (data != null) {
            Parcel p = Parcel.obtain();
            p.unmarshall(data, 0, data.length);
            p.setDataPosition(0);
            ParcelInfo info = new ParcelInfo(p);
            p.recycle();
            textView.append("\n\nparcel=" + info);
        }
    }
}
