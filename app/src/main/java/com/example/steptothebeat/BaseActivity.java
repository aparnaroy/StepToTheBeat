package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Subclasses must call setContentView in their onCreate()
    }

    public void addMenuBarSpace(int viewId) {
        // Make menu bar not be all the way at the top of the screen
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(viewId), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });
    }

    protected void setupToolbar(int toolbarId, boolean enableBackButton) {
        Toolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(enableBackButton);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); // Get the clicked menu item ID

        // Get the current activity's class name
        String currentActivityName = this.getClass().getSimpleName();

        if (id == R.id.action_home) {
            // Go to Home page
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings) {
            // Go to Settings page if we're not already on it
            if (!currentActivityName.equals(SettingsActivity.class.getSimpleName())) {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
            }
            return true;
        } else if (id == android.R.id.home) {
            // Go back
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
