package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectGenreActivity extends BaseActivity {
    public String[] dropdownOptions = {"Select One:","Pop","Rock","Rap","Dubstep"};
    private RadioButton selectedButton = null;
    private String dropDownSelection = null;
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
        RadioButton genreButton = findViewById(R.id.genre_button);
        RadioButton topHitsButton = findViewById(R.id.top_hits_button);
        RadioButton popButton = findViewById(R.id.pop_button);
        RadioButton rockButton = findViewById(R.id.rock_button);
        RadioButton surpriseMeButton = findViewById(R.id.surprise_me_button);
        LinearLayout generatePlaylistButton = findViewById(R.id.generate_playlist);


        // Display the activity type in a TextView (just for example)
        TextView selectedActivityTextView = findViewById(R.id.selected_pace_text);
        if (pace != null) {
            selectedActivityTextView.setText("Selected: " + pace);
        }

        // Dropdown options
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dropdownOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreDropdown.setAdapter(adapter);

        genreButton.setOnClickListener(view -> manageRadioButtons(genreButton));
        topHitsButton.setOnClickListener(view -> manageRadioButtons(topHitsButton));
        popButton.setOnClickListener(view -> manageRadioButtons(popButton));
        rockButton.setOnClickListener(view -> manageRadioButtons(rockButton));
        surpriseMeButton.setOnClickListener(view -> manageRadioButtons(surpriseMeButton));

        genreDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!dropdownInitialized) {
                    dropdownInitialized = true; // Mark as initialized
                    return; // Skip further execution
                }
                manageRadioButtons(genreButton);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        generatePlaylistButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewGeneratedPlaylist.class);
            String value = "";
            dropDownSelection = genreDropdown.getSelectedItem().toString();

            if (selectedButton == genreButton) {
                value = dropDownSelection;
            }
            else if (selectedButton == topHitsButton) {
                value = "Todays Top Hits";
            }
            else if (selectedButton == popButton) {
                value = "2010's Pop";
            }
            else if (selectedButton == rockButton) {
                value = "80's Rock";
            }
            else {
                value = "Surprise Me";
            }
            intent.putExtra("playlist", value); // Passing data
            intent.putExtra("pace", pace);
            intent.putExtra("genre", genreDropdown.getSelectedItem().toString());
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
