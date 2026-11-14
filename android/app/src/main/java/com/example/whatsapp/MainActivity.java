package com.example.whatsapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("WhatsApp");
        tv.setTextSize(30);
        tv.setGravity(android.view.Gravity.CENTER);
        tv.setBackgroundColor(0xFF075E54);
        tv.setTextColor(0xFFFFFFFF);
        setContentView(tv);
    }
}
