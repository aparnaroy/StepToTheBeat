package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectGenreActivity extends BaseActivity {
    public String[] dropdownOptions = {"Select One:","Pop","Rock","Rap","Dubstep"};
    private RadioButton selectedButton = null;

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
        RadioButton popularPicksButton = findViewById(R.id.popular_picks_button);
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
        popularPicksButton.setOnClickListener(view -> manageRadioButtons(popularPicksButton));
        surpriseMeButton.setOnClickListener(view -> manageRadioButtons(surpriseMeButton));

        generatePlaylistButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewGeneratedPlaylist.class);
            String value = "";
            if (selectedButton == genreButton) {
                value = "Genre";
            }
            else if (selectedButton == popularPicksButton) {
                value = "Popular Pick";
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
    }

}
