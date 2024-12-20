package com.example.steptothebeat;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.Random;

public class WalkCalibrationActivity extends BaseActivity {
    private static final String TAG = "SensorInfo";
    private static final float STEP_THRESHOLD = 10.0f; // Threshold for detecting a step
    private static final long STEP_INTERVAL = 300; // Minimum time between steps (ms)
    private static final long STEP_DETECTION_TIME = 30000; // 30 seconds of calibration time

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private SensorEventListener accelerometerListener;

    private long lastStepTime = 0;
    private int stepCount = 0;
    private long startTime = 0;
    private long detectionStartTime = 0; // Step detection start time
    private boolean timerStarted = false; // Flag to check if the timer has started
    private boolean stopDetection = false; // Flag to stop detection after 30 seconds
    private Random random = new Random();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_calibration_activity);
        addMenuBarSpace(R.id.walk_calibration);
        setupToolbar(R.id.menubar, true);

        initializeButtons();
    }

    void initializeButtons() {
        // Toggle expanded view for calibration info box
        LinearLayout infoCollapsed = findViewById(R.id.infoCollapsed);
        LinearLayout infoExpanded = findViewById(R.id.infoExpanded);

        infoCollapsed.setOnClickListener(view -> {
            if (infoExpanded.getVisibility() == View.GONE) {
                // Expand with animation
                expandView(infoExpanded);
            } else {
                // Collapse with animation
                collapseView(infoExpanded);
            }
        });


        LinearLayout startCalibrationButton = findViewById(R.id.startCalibrationButton);
        progressBar = findViewById(R.id.progressBarWalk);

        startCalibrationButton.setOnClickListener(v -> {
            // Hide the Start button
            startCalibrationButton.setVisibility(View.GONE);

            // Show the ProgressBar
            progressBar.setVisibility(View.VISIBLE);

            // Start step detection
            startStepDetection();
        });

        // Make the word "WALK" blue
        TextView taskTextView = findViewById(R.id.taskText);
        String text = taskTextView.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        int blueColor = ContextCompat.getColor(this, R.color.blue);
        int start = text.indexOf("WALK");
        int end = start + "WALK".length();
        spannableString.setSpan(new ForegroundColorSpan(blueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Apply bold style
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);        taskTextView.setText(spannableString);
    }

    private void startStepDetection() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            Log.d(TAG, "Accelerometer sensor found. Starting step detection...");
            registerAccelerometerListener(); // Start using accelerometer to detect steps
        } else {
            Log.e(TAG, "No Accelerometer found. Simulating steps with mock data.");
            startTime = System.currentTimeMillis();
            mockAccelerometerData(); // Simulate detecting steps with random fake steps
        }
    }

    private void mockAccelerometerData() {
        new Thread(() -> {
            detectionStartTime = System.currentTimeMillis();

            // Show the progress bar when detection starts
            runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));

            while (!stopDetection) {
                try {
                    float x = random.nextFloat() * 10.0f; // Random X acceleration
                    float y = random.nextFloat() * 10.0f; // Random Y acceleration
                    float z = random.nextFloat() * 10.0f; // Random Z acceleration

                    float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

                    processMagnitude(magnitude);

                    // Update the progress bar based on the elapsed time
                    long elapsedTime = System.currentTimeMillis() - detectionStartTime;
                    int progress = (int) ((elapsedTime / (float) STEP_DETECTION_TIME) * 100);

                    // Update the progress bar
                    runOnUiThread(() -> progressBar.setProgress(progress));

                    // Stop detection after 30 seconds
                    if (elapsedTime >= STEP_DETECTION_TIME) {
                        stopDetection = true;
                    }

                    Thread.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            calibrationComplete();
        }).start();
    }

    private void registerAccelerometerListener() {
        accelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

                    processMagnitude(magnitude);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}
        };

        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_UI);

        startTime = System.currentTimeMillis();
        detectionStartTime = System.currentTimeMillis();

        timerStarted = true;

        new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;

            while (elapsedTime < STEP_DETECTION_TIME) {
                try {
                    // Calculate the progress based on elapsed time
                    elapsedTime = System.currentTimeMillis() - startTime;
                    final int progress = (int) ((elapsedTime * 100) / STEP_DETECTION_TIME);

                    // Update the ProgressBar on the UI thread
                    runOnUiThread(() -> progressBar.setProgress(progress));

                    Thread.sleep(1000);  // Update progress every second

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            calibrationComplete();
        }).start();
    }

    private void processMagnitude(float magnitude) {
        long currentTime = System.currentTimeMillis();

        if (magnitude > STEP_THRESHOLD && (currentTime - lastStepTime) > STEP_INTERVAL) {
            lastStepTime = currentTime;
            stepCount++;
            Log.d(TAG, "Step detected! Total steps: " + stepCount);
        }
    }

    private void logStepsPerMinute() {
        float elapsedMinutes = (System.currentTimeMillis() - detectionStartTime) / 60000.0f;
        int spm = Math.round(stepCount / elapsedMinutes);
        Log.d(TAG, "Steps per minute (SPM): " + spm);

        // Show the dialog with SPM
        showStepsPerMinuteDialog(spm);

        // Save the Walk steps per minute
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("walk_spm", spm);
        editor.apply();
    }

    private void calibrationComplete() {
        if (sensorManager != null && accelerometerListener != null) {
            sensorManager.unregisterListener(accelerometerListener);
        }
        stopDetection = true;
        timerStarted = false;

        Log.d(TAG, "Step detection complete. Calculating steps per minute.");
        logStepsPerMinute();
    }

    private void showStepsPerMinuteDialog(int spm) {
        runOnUiThread(() -> {
            AlertDialog dialog = new AlertDialog.Builder(WalkCalibrationActivity.this)
                    .setTitle("Walk Calibration Complete")
                    .setMessage("Steps per minute: " + spm)
                    .setPositiveButton("Next", (dialog1, which) -> {
                        // Handle "Next" button click
                        dialog1.dismiss();

                        // Start RunCalibrationActivity
                        Intent intent = new Intent(WalkCalibrationActivity.this, RunCalibrationActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .setCancelable(false) // Prevent dismissing the dialog by tapping outside
                    .show();

            // Set title text size programmatically
            TextView title = dialog.findViewById(android.R.id.title);
            if (title != null) {
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30); // Set title text size
            }

            // Set message text size programmatically
            TextView message = dialog.findViewById(android.R.id.message);
            if (message != null) {
                message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Make message bigger
                message.setTextColor(getResources().getColor(R.color.black)); // Set message color to black
            }

            // Set "Next" button text size and color
            Button nextButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (nextButton != null) {
                nextButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Make button text bigger
                nextButton.setTextColor(getResources().getColor(R.color.purple)); // Set button text color to purple
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null && accelerometerListener != null) {
            sensorManager.unregisterListener(accelerometerListener);
        }
    }


    // Smoothly expand info box
    private void expandView(View view) {
        view.setVisibility(View.VISIBLE);

        int targetHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 450, getResources().getDisplayMetrics());

        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
        animator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            view.getLayoutParams().height = animatedValue;
            view.requestLayout();
        });
        animator.setDuration(300);
        animator.start();
    }

    // Smoothly collapse info box
    private void collapseView(View view) {
        int initialHeight = view.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            view.getLayoutParams().height = animatedValue;
            view.requestLayout();
        });
        animator.setDuration(300);
        animator.start();

        // When animation ends, set visibility to GONE
        animator.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                view.setVisibility(View.GONE); // Hide after collapsing
            }
        });
    }
}
