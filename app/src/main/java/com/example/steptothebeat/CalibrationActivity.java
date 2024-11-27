package com.example.steptothebeat;

import android.os.Bundle;

public class CalibrationActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration_activity);
        addMenuBarSpace(R.id.calibration);
        setupToolbar(R.id.menubar, true);

//        initializeButtons();
    }
}
