package com.example.steptothebeat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.spotify.android.appremote.*;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class ViewGeneratedPlaylist extends BaseActivity {
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final String CLIENT_ID = "57f00fa0bc2d45348bcd7857291e35c9";
    private static final String REDIRECT_URI = "https://open.spotify.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_generated_playlist);
        addMenuBarSpace(R.id.view_playlist);
        setupToolbar(R.id.menubar, true);

        String genre = getIntent().getStringExtra("genre");
//        String genre = getIntent().getStringExtra("genre");
        String pace = getIntent().getStringExtra("pace");

        // Buttons
        LinearLayout startRun = findViewById(R.id.start_run);

        // Set Listeners
        startRun.setOnClickListener(view -> startRun());

        TextView selectedPlaylistTextView = findViewById(R.id.selected_playlist_text);
        TextView selectedGenreTextView = findViewById(R.id.selected_genre_text);
        TextView selectedPaceTextView = findViewById(R.id.selected_pace_text);

        // Get playlist based on genre and pace
        if (genre != null) {
            // Display selected genre, or use it for playlist generation logic
            selectedGenreTextView.setText(genre);
        }
        if (pace != null) {
            // Display selected pace, or use it for playlist generation logic
            selectedPaceTextView.setText(pace);
            Log.d("ViewGeneratedPlaylist", "Selected Pace: " + pace);
        }

        // TEMPORARY TEXT TO BE REPLACED WITH SPOTIFY PLAYLIST NAME
        selectedPlaylistTextView.setText("The Playlist Name");

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
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("ViewGeneratedPlaylist", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

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
        // Play a playlist
        String playlistUri = getPlaylistUriBasedOnSelection();
        mSpotifyAppRemote.getPlayerApi().play(playlistUri);

        // Enable shuffle mode
        mSpotifyAppRemote.getPlayerApi().setShuffle(true);

        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null && track.artist != null) {
                        Log.d("ViewGeneratedPlaylist", track.name + " by " + track.artist.name);
                        // Send track and artist info to ActiveSessionActivity
                        Intent intent = new Intent(ViewGeneratedPlaylist.this, ActiveSessionActivity.class);
                        intent.putExtra("track", track.name);
                        intent.putExtra("artist", track.artist.name);

                        // Subscribe to PlayerContext
                        mSpotifyAppRemote.getPlayerApi()
                                .subscribeToPlayerContext()
                                .setEventCallback(playerContext -> {
                                    String playlistName = playerContext != null && playerContext.title != null
                                            ? playerContext.title
                                            : "Unknown Playlist";
                                    intent.putExtra("playlist", playlistName);
                                    Log.d("ViewGeneratedPlaylist", "Current Playlist: " + playlistName);

                                    // Start ActiveSessionActivity once all data is ready
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                })
                                .setErrorCallback(throwable -> Log.e("ViewGeneratedPlaylist", "Failed to subscribe to PlayerContext", throwable));
                    }

                    Intent broadcastIntent = new Intent("com.example.steptothebeat.SONG_UPDATE");
                    broadcastIntent.putExtra("track", track.name);
                    broadcastIntent.putExtra("artist", track.artist.name);
                    broadcastIntent.putExtra("playlist", "100BPM");


                    // Sending the broadcast
                    sendBroadcast(broadcastIntent);

                })
                .setErrorCallback(throwable -> Log.e("ViewGeneratedPlaylist", "Failed to subscribe to PlayerState", throwable));

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

        } else if (genre.equals("Dubstep") && pace.equals("Walk")) {
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

        // Default playlist if no match
        return "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL"; // Default playlist URI
    }
    public void startRun() {
        Intent intent = new Intent(this, ActiveSessionActivity.class);
        startActivity(intent);
    }

}




