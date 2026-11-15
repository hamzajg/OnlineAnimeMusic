package com.thephoenixit.onlineanimemusic.views;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thephoenixit.onlineanimemusic.R;
import com.thephoenixit.onlineanimemusic.utils.Constant;
import com.thephoenixit.onlineanimemusic.utils.DBHelper;
import com.thephoenixit.onlineanimemusic.utils.ItemSong;
import com.thephoenixit.onlineanimemusic.utils.MediaPlayerEngine;
import com.thephoenixit.onlineanimemusic.utils.PlayerController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Song browser + now-playing bar over the bundled catalog. Fully offline:
 * playback goes through PlayerController -> MediaPlayerEngine -> assets.
 */
public class MainActivity extends AppCompatActivity {

    private final ArrayList<ItemSong> songs = new ArrayList<>();
    private PlayerController controller;
    private MediaPlayerEngine engine;
    private TextView nowPlaying;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db = new DBHelper(this);
        try {
            db.createDataBase();
        } catch (IOException e) {
            // schema was already created by MyApp; a missing copy asset is fine
        }
        songs.addAll(db.loadData());

        engine = new MediaPlayerEngine(this);
        controller = new PlayerController(songs, engine);
        int start = Constant.playPos >= 0 && Constant.playPos < songs.size()
                ? Constant.playPos : 0;
        controller.select(start);

        nowPlaying = findViewById(R.id.text_now_playing);
        playButton = findViewById(R.id.btn_play);
        findViewById(R.id.btn_prev).setOnClickListener(v -> { controller.previous(); refreshBar(); });
        findViewById(R.id.btn_next).setOnClickListener(v -> { controller.next(); refreshBar(); });
        playButton.setOnClickListener(v -> { controller.toggle(); refreshBar(); });

        ListView list = findViewById(R.id.list_songs);
        list.setAdapter(new ArrayAdapter<ItemSong>(this, R.layout.item_song, songs) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View row = convertView != null ? convertView
                        : getLayoutInflater().inflate(R.layout.item_song, parent, false);
                ItemSong song = getItem(position);
                ((TextView) row.findViewById(R.id.text_song_title)).setText(song.getMp3Name());
                ((TextView) row.findViewById(R.id.text_song_artist))
                        .setText(song.getArtist() + "  •  " + song.getDuration());
                return row;
            }
        });
        list.setOnItemClickListener((parent, view, position, id) -> {
            controller.select(position);
            controller.play();
            Constant.playPos = position;
            refreshBar();
        });
        refreshBar();
    }

    private void refreshBar() {
        ItemSong current = controller.current();
        nowPlaying.setText(current == null ? "—" : current.getMp3Name());
        playButton.setText(controller.isPlaying() ? "Pause" : "Play");
    }

    @Override
    protected void onDestroy() {
        if (engine != null) {
            engine.release();
        }
        super.onDestroy();
    }
}
