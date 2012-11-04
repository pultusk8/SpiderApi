package com.example.spiderapi;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ActivityCore extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_core);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_activity_core, menu);
        return true;
    }
}
