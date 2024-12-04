package com.example.steptothebeat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.spotify.android.appremote.*;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;


import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SensorInfo";
    private static final float STEP_THRESHOLD = 10.0f; // Threshold for detecting a step
    private static final long STEP_INTERVAL = 300; // Minimum time between steps (ms)
    private static final long STEP_DETECTION_TIME = 30000; // 30 seconds to detect steps

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

    private static final String CLIENT_ID = "57f00fa0bc2d45348bcd7857291e35c9";
    private static final String REDIRECT_URI = "https://open.spotify.com/";
    private SpotifyAppRemote mSpotifyAppRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "inside onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_session_activity);
        EdgeToEdge.enable(this);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Log all available sensors
//        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor sensor : sensors) {
//            String sensorInfo = "Name: " + sensor.getName() +
//                    ", Type: " + sensor.getType() +
//                    ", Vendor: " + sensor.getVendor();
//            Log.d(TAG, sensorInfo);
//        }

        if (accelerometer != null) {
            Log.d(TAG, "Accelerometer sensor found. Starting step detection...");
            registerAccelerometerListener(); // Start using accelerometer to detect steps
        } else {
            Log.e(TAG, "No Accelerometer found. Simulating steps with mock data.");
            startTime = System.currentTimeMillis();
            mockAccelerometerData(); // Simulate detecting steps with random fake steps
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    private void connected() {
        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                    }
                });
    }

    private void mockAccelerometerData() {
        // Simulate accelerometer data at regular intervals
        new Thread(() -> {
            // Initialize start time for the mock simulation
            detectionStartTime = System.currentTimeMillis();

            while (!stopDetection) {
                try {
                    // Simulate random acceleration data (mimicking X, Y, Z accelerometer values)
                    float x = random.nextFloat() * 10.0f; // Random X acceleration
                    float y = random.nextFloat() * 10.0f; // Random Y acceleration
                    float z = random.nextFloat() * 10.0f; // Random Z acceleration

                    // Calculate magnitude
                    float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

                    // Process magnitude to detect steps
                    processMagnitude(magnitude);

                    // Sleep for 500ms to simulate periodic sensor updates
                    Thread.sleep(500);

                    // Stop detection after 30 seconds
                    if (System.currentTimeMillis() - detectionStartTime >= STEP_DETECTION_TIME) {
                        stopDetection = true;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // After 30 seconds, log the steps per minute
            Log.d(TAG, "Step detection complete. Calculating steps per minute.");
            logStepsPerMinute(); // Calculate and log steps per minute
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

                    // Calculate acceleration magnitude
                    float magnitude = (float) Math.sqrt(x * x + y * y + z * z);

                    // Process magnitude to detect steps
                    processMagnitude(magnitude);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // Not needed for this implementation
            }
        };

        // Register the listener with normal delay
        sensorManager.registerListener(accelerometerListener, accelerometer, SensorManager.SENSOR_DELAY_UI);

        // Initialize start time
        startTime = System.currentTimeMillis();
        detectionStartTime = System.currentTimeMillis(); // Set detection start time

        // Start the timer to detect steps for 30 seconds
        timerStarted = true;

        // Automatically stop detection after 30 seconds
        new Thread(() -> {
            try {
                Thread.sleep(STEP_DETECTION_TIME); // Wait for the detection time to pass
                Log.d(TAG, "Step detection complete. Calculating steps per minute.");
                logStepsPerMinute(); // Calculate and log steps per minute
                stopStepDetection(); // Stop step detection
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void processMagnitude(float magnitude) {
        long currentTime = System.currentTimeMillis();

        // Detect step if magnitude crosses threshold and meets interval
        if (magnitude > STEP_THRESHOLD && (currentTime - lastStepTime) > STEP_INTERVAL) {
            lastStepTime = currentTime;
            stepCount++;
            Log.d(TAG, "Step detected! Total steps: " + stepCount);
        }
    }

    private void logStepsPerMinute() {
        // Calculate steps per minute
        float elapsedMinutes = (System.currentTimeMillis() - detectionStartTime) / 60000.0f;
        int spm = Math.round(stepCount / elapsedMinutes);
        Log.d(TAG, "Steps per minute (SPM): " + spm);
    }

    private void stopStepDetection() {
        // Stop step detection and unregister the accelerometer listener
        if (sensorManager != null && accelerometerListener != null) {
            sensorManager.unregisterListener(accelerometerListener);
        }
        stopDetection = true; // Set the flag to stop detection
        timerStarted = false;
        Log.d(TAG, "Step detection stopped.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the accelerometer listener to prevent memory leaks
        if (sensorManager != null && accelerometerListener != null) {
            sensorManager.unregisterListener(accelerometerListener);
        }
    }
}
