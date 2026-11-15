package com.thephoenixit.onlineanimemusic.utils;

import java.util.List;
import java.util.Random;

/**
 * Playlist engine for the offline player. Pure Java — the playback
 * backend is behind {@link Engine} so the playlist logic is unit-testable
 * on the JVM without Android or an emulator.
 *
 * States: current index, playing flag, shuffle, repeat-one. Navigation
 * wraps around; with shuffle on, next() never repeats the current track.
 */
public final class PlayerController {

    /** Thin playback backend — MediaPlayerEngine in production, fake in tests. */
    public interface Engine {
        void load(String uri);

        void play();

        void pause();

        void seekTo(int positionMs);

        void release();

        int getDuration();

        int getCurrentPosition();

        boolean isPlaying();
    }

    private static final int PREVIOUS_RESTART_MS = 3000;

    private final List<ItemSong> playlist;
    private final Engine engine;
    private final Random random;
    private int current = -1;
    private boolean playing;
    private boolean shuffle;
    private boolean repeatOne;

    public PlayerController(List<ItemSong> playlist, Engine engine) {
        this(playlist, engine, new Random());
    }

    PlayerController(List<ItemSong> playlist, Engine engine, Random random) {
        if (playlist == null || playlist.isEmpty()) {
            throw new IllegalArgumentException("playlist must not be empty");
        }
        this.playlist = playlist;
        this.engine = engine;
        this.random = random;
    }

    public ItemSong current() {
        return current < 0 ? null : playlist.get(current);
    }

    public int currentIndex() {
        return current;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    public boolean isRepeatOne() {
        return repeatOne;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public void setRepeatOne(boolean repeatOne) {
        this.repeatOne = repeatOne;
    }

    public int size() {
        return playlist.size();
    }

    /** Selects a track and loads it, paused. Out-of-range is ignored. */
    public void select(int index) {
        if (index < 0 || index >= playlist.size()) {
            return;
        }
        current = index;
        playing = false;
        engine.load(playlist.get(index).getMp3Url());
    }

    public void play() {
        if (current < 0) {
            select(0);
        }
        if (current < 0) {
            return;
        }
        engine.play();
        playing = true;
    }

    public void pause() {
        engine.pause();
        playing = false;
    }

    public void toggle() {
        if (playing) {
            pause();
        } else {
            play();
        }
    }

    public void next() {
        if (repeatOne && current >= 0) {
            restartCurrent();
            return;
        }
        int target = shuffle ? randomOther() : (current + 1) % playlist.size();
        select(target);
        play();
    }

    public void previous() {
        if (current >= 0 && engine.getCurrentPosition() > PREVIOUS_RESTART_MS) {
            engine.seekTo(0);
            return;
        }
        int target = current <= 0 ? playlist.size() - 1 : current - 1;
        select(target);
        play();
    }

    private void restartCurrent() {
        engine.load(playlist.get(current).getMp3Url());
        engine.play();
        playing = true;
    }

    private int randomOther() {
        if (playlist.size() == 1) {
            return 0;
        }
        int pick;
        do {
            pick = random.nextInt(playlist.size());
        } while (pick == current);
        return pick;
    }
}
