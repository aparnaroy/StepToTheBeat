package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.android.appremote.*;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import android.content.SharedPreferences;


public class ViewGeneratedPlaylist extends BaseActivity {
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final String CLIENT_ID = "57f00fa0bc2d45348bcd7857291e35c9";
    private static final String REDIRECT_URI = "https://open.spotify.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Toast.makeText(this, "inside on create", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_generated_playlist);
        addMenuBarSpace(R.id.view_playlist);
        setupToolbar(R.id.menubar, true);

        // Get selected pace and genre
        String genre = getIntent().getStringExtra("genre");
        String pace = getIntent().getStringExtra("pace");

        // Get saved steps per minute for walk and run
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        int walk_spm = sharedPreferences.getInt("walk_spm", -1);
        int run_spm = sharedPreferences.getInt("run_spm", -1);

        // Buttons
        LinearLayout startRun = findViewById(R.id.start_run);

        // Set Listeners
        startRun.setOnClickListener(view -> startRun());
//        Toast.makeText(this, "After clicking start run", Toast.LENGTH_SHORT).show();

        TextView selectedPlaylistTextView = findViewById(R.id.selected_playlist_text);
        TextView selectedGenreTextView = findViewById(R.id.selected_genre_text);
        TextView selectedPaceTextView = findViewById(R.id.selected_pace_text);

        // Get playlist based on genre and pace
        if (genre != null) {
            // Display selected genre, or use it for playlist generation logic
            selectedGenreTextView.setText(genre);
        }
        if (pace != null) {
            // Display selected pace
            // If walk and run are calibrated and are the selected pace, show that as the BPM
            // Else, show just the pace range if spm is not available cuz user has not calibrated yet
            if (pace.equals("Walk") && walk_spm != -1) {
                selectedPaceTextView.setText(pace + " (calibrated to ~" + walk_spm + " BPM)");
            } else if (pace.equals("Run") && run_spm != -1) {
                selectedPaceTextView.setText(pace + " (calibrated to ~" + run_spm + " BPM)");
            } else if (pace.equals("Walk")) {
                selectedPaceTextView.setText(pace + " (90–120 BPM)");
            } else if (pace.equals("Power Walk")) {
                selectedPaceTextView.setText(pace + " (120–135 BPM)");
            } else if (pace.equals("Jog")) {
                selectedPaceTextView.setText(pace + " (130–150 BPM)");
            } else if (pace.equals("Run")) {
                selectedPaceTextView.setText(pace + " (150–180 BPM)");
            } else if (pace.equals("Sprint")) {
                selectedPaceTextView.setText(pace + " (180–220 BPM)");
            }
        }

        // TEMPORARY TEXT TO BE REPLACED WITH SPOTIFY PLAYLIST NAME
        selectedPlaylistTextView.setText("The Playlist Name");
        //playlist names
        if (genre != null && pace != null) {
            if (genre.equals("Pop") && pace.equals("Walk")) {
                selectedPlaylistTextView.setText("100 BPM");
            } else if (genre.equals("Pop") && pace.equals("Power Walk")) {
                selectedPlaylistTextView.setText("100 BPM");
            } else if (genre.equals("Pop") && pace.equals("Jog")) {
                selectedPlaylistTextView.setText("130BPM pop-mix");
            } else if (genre.equals("Pop") && pace.equals("Run")) {
                selectedPlaylistTextView.setText("165 bpm POP");
            } else if (genre.equals("Pop") && pace.equals("Sprint")) {
                selectedPlaylistTextView.setText("165 bpm POP");
            } else if (genre.equals("Rock") && pace.equals("Walk")) {
                selectedPlaylistTextView.setText("100 BPM rock");
            } else if (genre.equals("Rock") && pace.equals("Power Walk")) {
                selectedPlaylistTextView.setText("100 BPM rock");
            } else if (genre.equals("Rock") && pace.equals("Jog")) {
                selectedPlaylistTextView.setText("130bpm rock");
            } else if (genre.equals("Rock") && pace.equals("Run")) {
                selectedPlaylistTextView.setText("160 BPM Rock Running");
            } else if (genre.equals("Rock") && pace.equals("Sprint")) {
                selectedPlaylistTextView.setText("160 BPM Rock Running");
            } else if (genre.equals("Rap") && pace.equals("Walk")) {
                selectedPlaylistTextView.setText("100 BPM+ Rap/HipHop");
            } else if (genre.equals("Rap") && pace.equals("Power Walk")) {
                selectedPlaylistTextView.setText("100 BPM+ Rap/HipHop");
            } else if (genre.equals("Rap") && pace.equals("Jog")) {
                selectedPlaylistTextView.setText("Rap/HipHop BPM 120-130");
            } else if (genre.equals("Rap") && pace.equals("Run")) {
                selectedPlaylistTextView.setText("170 BPM RAP");
            } else if (genre.equals("Rap") && pace.equals("Sprint")) {
                selectedPlaylistTextView.setText("170 BPM RAP");
            }
        } else if (genre.equals("Dubstep") && pace.equals("Walk")) {
            selectedPlaylistTextView.setText("100 bpm EDM");
        } else if (genre.equals("Dubstep") && pace.equals("Power Walk")) {
            selectedPlaylistTextView.setText("100 bpm EDM");
        } else if (genre.equals("Dubstep") && pace.equals("Jog")) {
            selectedPlaylistTextView.setText("edm 130 bpm");
        } else if (genre.equals("Dubstep") && pace.equals("Run")) {
            selectedPlaylistTextView.setText("170 BPM Running EDM Mix");
        } else if (genre.equals("Dubstep") && pace.equals("Sprint")) {
            selectedPlaylistTextView.setText("170 BPM Running EDM Mix");
        }
        else{
        // Default playlist if no match
        selectedPlaylistTextView.setText("For you");// Default playlist's Name
        }
    }


    @Override
    protected void onStart() {
//        Toast.makeText(this, "inside on start", Toast.LENGTH_SHORT).show();

        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("ViewGeneratedPlaylist", "Connected! Yay!");

                        // Now you can start interacting with App Remote
//                        connected();
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("ViewGeneratedPlaylist", throwable.getMessage(), throwable);

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
//        Toast.makeText(this, "inside connected", Toast.LENGTH_SHORT).show();

        // Play a playlist
        String playlistUri = getPlaylistUriBasedOnSelection();
        mSpotifyAppRemote.getPlayerApi().play(playlistUri);

        // Create a Handler and a reference for the Runnable to cancel previous intent dispatch
        Handler handler = new Handler();
        final Runnable[] lastRunnable = {null};

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    Track track = playerState.track;
                    if (track != null && track.artist != null) {
                        Log.d("ViewGeneratedPlaylist", track.name + " by " + track.artist.name);

                        // Subscribe to PlayerContext to get the playlist name
                        mSpotifyAppRemote.getPlayerApi()
                                .subscribeToPlayerContext()
                                .setEventCallback(playerContext -> {
                                    String playlistName = playerContext != null && playerContext.title != null
                                            ? playerContext.title
                                            : "Unknown Playlist";
                                    Log.d("ViewGeneratedPlaylist", "Current Playlist: " + playlistName);

                                    // Cancel the previous Runnable if it's still pending
                                    if (lastRunnable[0] != null) {
                                        handler.removeCallbacks(lastRunnable[0]);
                                    }
                                    Log.d("ViewGeneratedPlaylist", track.name + " by " + track.artist.name + " before set to intent");
                                    // Prepare the intent
                                    final Intent intent = new Intent(ViewGeneratedPlaylist.this, ActiveSessionActivity.class);
                                    intent.putExtra("track", track.name);
                                    intent.putExtra("artist", track.artist.name);
                                    intent.putExtra("playlist", playlistName);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                    // Set a new Runnable to be executed after 10 seconds
                                    lastRunnable[0] = new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("ViewGeneratedPlaylist", "Starting ActiveSessionActivity after 10 seconds delay");
                                            startActivity(intent);
                                            Log.d("ViewGeneratedPlaylist", track.name + " by " + track.artist.name + " after set to intent");

                                        }
                                    };

                                    // Post the new Runnable with a 1-second delay
                                    handler.postDelayed(lastRunnable[0], 1000);

                                })
                                .setErrorCallback(throwable -> Log.e("ViewGeneratedPlaylist", "Failed to subscribe to PlayerContext", throwable));
                    }
                })
                .setErrorCallback(throwable -> Log.e("ViewGeneratedPlaylist", "Failed to subscribe to PlayerState", throwable));

//        Toast.makeText(this, "After spotify app remote stuff", Toast.LENGTH_SHORT).show();
    }

    private String getPlaylistUriBasedOnSelection() {
        String genre = getIntent().getStringExtra("genre");
        String pace = getIntent().getStringExtra("pace");

        // Example logic for playlist URI selection
        if (genre != null && pace != null) {
            if (genre.equals("Pop") && pace.equals("Walk")) {
                return "spotify:playlist:5JpANhLlGcgZcLFcrNhL7j";  // Example Pop Walk playlist URI

            } else if (genre.equals("Pop") && pace.equals("Power Walk")) {
                return "spotify:playlist:5JpANhLlGcgZcLFcrNhL7j";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Pop") && pace.equals("Jog")) {
                return "spotify:playlist:0ruA5Rqd0TvO70dXUo8GM2";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Pop") && pace.equals("Run")) {
                return "spotify:playlist:0D1khHqzapcCrPR4wr2mcs";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Pop") && pace.equals("Sprint")) {
                return "spotify:playlist:0D1khHqzapcCrPR4wr2mcs";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rock") && pace.equals("Walk")) {
                return "spotify:playlist:6Hqw1C4FilfEuwey2bQkQb";  // Example Pop Walk playlist URI
            } else if (genre.equals("Rock") && pace.equals("Power Walk")) {
                return "spotify:playlist:6Hqw1C4FilfEuwey2bQkQb";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rock") && pace.equals("Jog")) {
                return "spotify:playlist:5579lzhZDSyPct0gM6CQ8y";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rock") && pace.equals("Run")) {
                return "spotify:playlist:0EVQR3A1xeOv5IhOik9q2p";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rock") && pace.equals("Sprint")) {
                return "spotify:playlist:0EVQR3A1xeOv5IhOik9q2p";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rap") && pace.equals("Walk")) {
                return "spotify:playlist:4drAtXXgGXlcCufY8Bwc3L";  // Example Pop Walk playlist URI
            } else if (genre.equals("Rap") && pace.equals("Power Walk")) {
                return "spotify:playlist:4drAtXXgGXlcCufY8Bwc3L";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rap") && pace.equals("Jog")) {
                return "spotify:playlist:5DpF1ITA5dqwhM0EGVaa0B";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rap") && pace.equals("Run")) {
                return "spotify:playlist:4lVrJcSHHKajwRtQjCIok7";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Rap") && pace.equals("Sprint")) {
                return "spotify:playlist:4lVrJcSHHKajwRtQjCIok7";  // Example Rock Sprint playlist URI
            }
            else if (genre.equals("Dubstep") && pace.equals("Walk")) {
                return "spotify:playlist:7jEbBfCBmmS6T4YJNlElaz";  // Example Pop Walk playlist URI
            } else if (genre.equals("Dubstep") && pace.equals("Power Walk")) {
                return "spotify:playlist:7jEbBfCBmmS6T4YJNlElaz";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Dubstep") && pace.equals("Jog")) {
                return "spotify:playlist:0NKuEOASOPZPZJXl101qRf";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Dubstep") && pace.equals("Run")) {
                return "spotify:playlist:37i9dQZF1EIdFa1mD9SkGv";  // Example Rock Sprint playlist URI
            } else if (genre.equals("Dubstep") && pace.equals("Sprint")) {
                return "spotify:playlist:37i9dQZF1EIdFa1mD9SkGv";  // Example Rock Sprint playlist URI
            }
        }

        // Default playlist if no match
        return "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL"; // Default playlist URI

    }
    public void startRun() {
//        Toast.makeText(this, "inside start run", Toast.LENGTH_SHORT).show();
        if (mSpotifyAppRemote != null && mSpotifyAppRemote.isConnected()) {
            connected();
        } else {
            Toast.makeText(this, "Spotify is not connected yet. Please wait...", Toast.LENGTH_SHORT).show();
            Log.e("ViewGeneratedPlaylist", "SpotifyAppRemote is not connected.");
        }
    }

}




