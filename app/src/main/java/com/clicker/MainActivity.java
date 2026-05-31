package com.clicker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    static {
        System.loadLibrary("clicker"); // грузим наш .so
    }

    // объявляем нативные методы
    public native long nativeClick();
    public native void nativeReset();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView countView = findViewById(R.id.countView);
        Button clickBtn   = findViewById(R.id.clickBtn);

        clickBtn.setOnClickListener(v -> {
            long count = nativeClick();
            countView.setText(String.valueOf(count));
        });
    }
}
