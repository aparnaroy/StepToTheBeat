package com.example.steptothebeat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.Random;

public class RunCalibrationActivity extends BaseActivity {
    private static final String TAG = "SensorInfo";
    private static final float STEP_THRESHOLD = 10.0f; // Threshold for detecting a step
    private static final long STEP_INTERVAL = 300; // Minimum time between steps (ms)
    private static final long STEP_DETECTION_TIME = 10000; // 30 seconds of calibration time

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
        setContentView(R.layout.run_calibration_activity);
        addMenuBarSpace(R.id.run_calibration);
        setupToolbar(R.id.menubar, true);

        initializeButtons();
    }

    void initializeButtons() {
        LinearLayout startCalibrationButton = findViewById(R.id.startCalibrationButton);
        progressBar = findViewById(R.id.progressBarRun);

        startCalibrationButton.setOnClickListener(v -> {
            // Hide the Start button
            startCalibrationButton.setVisibility(View.GONE);

            // Show the ProgressBar
            progressBar.setVisibility(View.VISIBLE);

            // Start step detection
            startStepDetection();
        });

        // Make the word "RUN" orchid colored
        TextView taskTextView = findViewById(R.id.taskText);
        String text = taskTextView.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        int orchidColor = ContextCompat.getColor(this, R.color.orchid);
        int start = text.indexOf("RUN");
        int end = start + "RUN".length();
        spannableString.setSpan(new ForegroundColorSpan(orchidColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        taskTextView.setText(spannableString);
    }

    private void startStepDetection() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

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
            // Create a LinearLayout to hold the message
            LinearLayout layout = new LinearLayout(RunCalibrationActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(70, 40, 70, 20); // Add padding for spacing
            // Create the main message (Steps per minute)
            TextView mainMessage = new TextView(RunCalibrationActivity.this);
            mainMessage.setText("Steps per minute: " + spm);
            mainMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Set the text size
            mainMessage.setTextColor(getResources().getColor(R.color.black)); // Set text color

            // Create the additional message (Thank you text)
            TextView additionalMessage = new TextView(RunCalibrationActivity.this);
            additionalMessage.setText("Thank you for calibrating for a more personalized app experience!");
            additionalMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Set a smaller text size
            additionalMessage.setTextColor(getResources().getColor(R.color.gray)); // Set a different color for smaller text

            // Create a space (empty view) between the two text views
            View space = new View(RunCalibrationActivity.this);
            LinearLayout.LayoutParams spaceParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 30); // Set height of space (30px or adjust as needed)
            space.setLayoutParams(spaceParams);

            // Add the views to the layout
            layout.addView(mainMessage);
            layout.addView(space); // Add space between the messages
            layout.addView(additionalMessage);

            // Create the dialog with the custom layout
            AlertDialog dialog = new AlertDialog.Builder(RunCalibrationActivity.this)
                    .setTitle("Run Calibration Complete")
                    .setView(layout) // Set the custom view here
                    .setPositiveButton("Done", (dialog1, which) -> {
                        // Handle "Done" button click
                        dialog1.dismiss();
                        finish(); // Go back to Settings
                    })
                    .setCancelable(false) // Prevent dismissing the dialog by tapping outside
                    .show();

            // Set title text size programmatically
            TextView title = dialog.findViewById(android.R.id.title);
            if (title != null) {
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30); // Set title text size
            }

            // Set "Done" button text size and color
            Button nextButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (nextButton != null) {
                nextButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Set button text size
                nextButton.setTextColor(getResources().getColor(R.color.purple)); // Set button text color
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
}
