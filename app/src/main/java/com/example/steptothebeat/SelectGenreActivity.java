package com.example.steptothebeat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class SelectGenreActivity extends BaseActivity {
    public String[] genreDropdownOptions = {"Select One: ", "Pop", "Rock", "Rap", "Dubstep"};
    public String[] popularPicksDropdownOptions = {"Select One: ", "Top Hits", "2010's Pop", "80's Rock"};
    private RadioButton selectedButton = null;
    private String genreDropDownSelection = null;
    private String popularPicksSelection = null;
    boolean dropdownInitialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_genre_activity);
        addMenuBarSpace(R.id.select_genre);
        setupToolbar(R.id.menubar, true);

        // Get the activity type passed from the ChoosePaceActivity
        String pace = getIntent().getStringExtra("pace");

        // Buttons and stuff
        Spinner genreDropdown = findViewById(R.id.genre_dropdown);
        Spinner popularPicksDropdown = findViewById(R.id.popular_picks_dropdown); // New dropdown
        RadioButton genreButton = findViewById(R.id.genre_button);
        RadioButton popularPicksButton = findViewById(R.id.popular_picks_button);
        RadioButton surpriseMeButton = findViewById(R.id.surprise_me_button);
        LinearLayout generatePlaylistButton = findViewById(R.id.generate_playlist);

        genreButton.setChecked(false);
        popularPicksButton.setChecked(false);
        surpriseMeButton.setChecked(false);

        // Dropdown options for Genres
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genreDropdownOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreDropdown.setAdapter(adapter);

        // Dropdown options for Popular Picks
        ArrayAdapter<String> popularPicksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, popularPicksDropdownOptions);
        popularPicksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        popularPicksDropdown.setAdapter(popularPicksAdapter);

        genreButton.setOnClickListener(view -> manageRadioButtons(genreButton));
        popularPicksButton.setOnClickListener(view -> manageRadioButtons(popularPicksButton));
        surpriseMeButton.setOnClickListener(view -> manageRadioButtons(surpriseMeButton));

        genreDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!dropdownInitialized) {
                    dropdownInitialized = true; // Mark as initialized
                    return; // Skip further execution
                }
                // Check if the item selected is not the "Select One" option
                if (position > 0) { // position 0 is "Select One: "
                    manageRadioButtons(genreButton); // Only call after a valid selection
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        popularPicksDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!dropdownInitialized) {
                    dropdownInitialized = true;
                    return;
                }
                // Check if the item selected is not the "Select One" option
                if (position > 0) { // position 0 is "Select One: "
                    manageRadioButtons(popularPicksButton); // Only call after a valid selection
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        generatePlaylistButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewGeneratedPlaylist.class);
            String value = "";
            genreDropDownSelection = genreDropdown.getSelectedItem().toString();
            popularPicksSelection = popularPicksDropdown.getSelectedItem().toString();

            if (selectedButton == genreButton) {
                value = genreDropDownSelection;
            } else if (selectedButton == popularPicksButton) {
                value = popularPicksSelection;
            } else {
                value = "Surprise Me";
            }
            intent.putExtra("genre", value); // Passing data
            intent.putExtra("pace", pace);
            startActivity(intent);
        });
    }

    public void manageRadioButtons(RadioButton clickedRadioButton) {
        if (selectedButton != null && selectedButton != clickedRadioButton) {
            selectedButton.setChecked(false);
        }
        selectedButton = clickedRadioButton;
        if (!selectedButton.isChecked()) {
            selectedButton.setChecked(true);
        }
    }
}
