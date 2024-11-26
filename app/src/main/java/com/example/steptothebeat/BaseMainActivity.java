package com.example.steptothebeat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public abstract class BaseMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

        setContentView(R.layout.activity_codia_main);
        ViewGroup rootView = findViewById(R.id.root);
        View codiaRootView = LayoutInflater.from(this).inflate(R.layout.layout_codia_root, rootView, false);
        rootView.addView(codiaRootView, 0);
        onViewCreated();
    }

    protected abstract void onViewCreated();
}
