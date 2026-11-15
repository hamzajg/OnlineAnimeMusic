package com.thephoenixit.onlineanimemusic.views;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.thephoenixit.onlineanimemusic.R;
import com.thephoenixit.onlineanimemusic.utils.Constant;
import com.thephoenixit.onlineanimemusic.utils.DBHelper;
import com.thephoenixit.onlineanimemusic.utils.ItemSong;

import java.util.ArrayList;

/**
 * Song browser over the bundled catalog. Tap selects a track and stores
 * its position for the player (playback lands in the next wave).
 */
public class MainActivity extends AppCompatActivity {

    private final ArrayList<ItemSong> songs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper db = new DBHelper(this);
        try {
            db.createDataBase();
        } catch (java.io.IOException e) {
            // schema was already created by MyApp; a missing copy asset is fine
        }
        songs.addAll(db.loadData());

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
            Constant.playPos = position;
            Toast.makeText(this, "selected: " + songs.get(position).getMp3Name(),
                    Toast.LENGTH_SHORT).show();
        });
    }
}
