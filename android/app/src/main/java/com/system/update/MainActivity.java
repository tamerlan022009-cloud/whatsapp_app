package com.system.update;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, CoreService.class));
        finish();
    }
}
