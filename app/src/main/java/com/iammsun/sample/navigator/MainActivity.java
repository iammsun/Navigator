package com.iammsun.sample.navigator;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.iammsun.navigator.RuntimeNav;
import com.iammsun.navigator.Navigator;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParcelInfo parcelInfo = new ParcelInfo();
                parcelInfo.setNumIid(123);
                parcelInfo.setOuterId("out?Id=11&");
                parcelInfo.setPropertiesName("propertiesName");
                parcelInfo.setQuantity(321);
                parcelInfo.setSkuId(111);
                Parcel parcel = Parcel.obtain();
                parcelInfo.writeToParcel(parcel, 0);
                byte[] data = parcel.marshall();
                parcel.recycle();
                Navigator.open(MainActivity.this, "nav://iammsun" +
                        ".com/a?param1=aaa&param2=111&parcel=" + Uri.encode(new String(data)));
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.open(MainActivity.this, "nav://iammsun.com/b?param1=bbb&param2=222");
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.open(MainActivity.this, "nav://iammsun.com/c?param1=ccc&param2=333");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
