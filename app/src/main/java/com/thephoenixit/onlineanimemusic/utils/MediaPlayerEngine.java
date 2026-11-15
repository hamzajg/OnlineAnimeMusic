package com.thephoenixit.onlineanimemusic.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * MediaPlayer-backed {@link PlayerController.Engine}. Streams the bundled
 * assets via AssetFileDescriptor — fully offline, no network on the path.
 */
public final class MediaPlayerEngine implements PlayerController.Engine {

    private static final String ASSET_PREFIX = "file:///android_asset/";

    private final Context context;
    private MediaPlayer player;

    public MediaPlayerEngine(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void load(String uri) {
        release();
        player = new MediaPlayer();
        try {
            if (uri != null && uri.startsWith(ASSET_PREFIX)) {
                String path = uri.substring(ASSET_PREFIX.length());
                AssetFileDescriptor afd = context.getAssets().openFd(path);
                player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
            } else {
                player.setDataSource(uri);
            }
            player.prepare();
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("cannot play " + uri, e);
        }
    }

    @Override
    public void play() {
        if (player != null) {
            player.start();
        }
    }

    @Override
    public void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    @Override
    public void seekTo(int positionMs) {
        if (player != null) {
            player.seekTo(positionMs);
        }
    }

    @Override
    public void release() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public int getDuration() {
        return player != null ? player.getDuration() : 0;
    }

    @Override
    public int getCurrentPosition() {
        return player != null ? player.getCurrentPosition() : 0;
    }

    @Override
    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }
}
