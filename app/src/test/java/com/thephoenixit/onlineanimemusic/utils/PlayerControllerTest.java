package com.thephoenixit.onlineanimemusic.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class PlayerControllerTest {

    private static class FakeEngine implements PlayerController.Engine {
        String loaded;
        int plays, pauses, seeks;
        int position;
        boolean playing;

        public void load(String uri) { loaded = uri; }
        public void play() { plays++; playing = true; }
        public void pause() { pauses++; playing = false; }
        public void seekTo(int ms) { seeks++; position = 0; }
        public void release() { }
        public int getDuration() { return 6000; }
        public int getCurrentPosition() { return position; }
        public boolean isPlaying() { return playing; }
    }

    private static ItemSong song(String id) {
        return new ItemSong(id, "c", "cat", "artist", "file:///android_asset/" + id + ".wav",
                "", "", "title-" + id, "0:06", "desc");
    }

    private static List<ItemSong> playlist(int n) {
        List<ItemSong> songs = new ArrayList<>();
        for (int i = 0; i < n; i++) songs.add(song("s" + i));
        return songs;
    }

    private PlayerController controller(List<ItemSong> songs, FakeEngine engine) {
        return new PlayerController(songs, engine, new Random(42));
    }

    @Test
    public void nextAndPreviousWrapAround() {
        // covers scenario: Playlist navigation wraps
        FakeEngine e = new FakeEngine();
        PlayerController c = controller(playlist(3), e);
        c.select(0);
        c.next();
        assertEquals(1, c.currentIndex());
        c.next();
        c.next();
        assertEquals(0, c.currentIndex()); // wrapped past the end
        c.previous();
        assertEquals(2, c.currentIndex()); // wrapped to the end
    }

    @Test
    public void selectLoadsTrackUriPaused() {
        // covers scenario: Tap song to play
        FakeEngine e = new FakeEngine();
        PlayerController c = controller(playlist(3), e);
        c.select(2);
        assertEquals("file:///android_asset/s2.wav", e.loaded);
        assertFalse(c.isPlaying());
    }

    @Test
    public void playPauseToggle() {
        // covers scenario: Play pause toggles
        FakeEngine e = new FakeEngine();
        PlayerController c = controller(playlist(2), e);
        c.play();
        assertTrue(c.isPlaying());
        assertEquals(0, c.currentIndex()); // auto-selects first
        c.pause();
        assertFalse(c.isPlaying());
        c.toggle();
        assertTrue(c.isPlaying());
    }

    @Test
    public void shuffleNextNeverStaysOnCurrent() {
        // covers scenario: Shuffle and repeat work
        FakeEngine e = new FakeEngine();
        PlayerController c = controller(playlist(5), e);
        c.setShuffle(true);
        c.select(2);
        for (int i = 0; i < 20; i++) {
            c.select(2);
            c.next();
            assertNotEquals("shuffle must not repeat current", 2, c.currentIndex());
        }
    }

    @Test
    public void repeatOneRestartsCurrentTrack() {
        // covers scenario: Shuffle and repeat work
        FakeEngine e = new FakeEngine();
        PlayerController c = controller(playlist(3), e);
        c.select(1);
        c.setRepeatOne(true);
        c.next();
        assertEquals(1, c.currentIndex());
        assertTrue(c.isPlaying());
    }

    @Test
    public void previousRestartsTrackWhenPastIntro() {
        FakeEngine e = new FakeEngine();
        e.position = 5000;
        PlayerController c = controller(playlist(3), e);
        c.select(1);
        c.previous();
        assertEquals(1, c.currentIndex());
        assertEquals(1, e.seeks);
        assertEquals(0, e.position);
    }

    @Test
    public void emptySelectionIsSafe() {
        FakeEngine e = new FakeEngine();
        PlayerController c = controller(playlist(1), e);
        c.select(99);
        assertNull(c.current());
    }
}
