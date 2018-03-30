package com.thephoenixit.onlineanimemusic.utils;

import android.content.Context;

import com.google.android.exoplayer2.SimpleExoPlayer;

import java.io.Serializable;
import java.util.ArrayList;

public class Constant implements Serializable {


    public static final String APP_DETAILS_URL;
    public static final int GRID_PADDING = 3;
    public static final int NUM_OF_COLUMNS = 3;
    public static String SERVER_URL;
    public static final String TAG_ARTIST = "mp3_artist";
    public static final String TAG_ARTIST_IMAGE = "artist_image";
    public static final String TAG_ARTIST_NAME = "artist_name";
    public static final String TAG_ARTIST_THUMB = "artist_image_thumb";
    public static final String TAG_CAT_ID = "cat_id";
    public static final String TAG_CAT_NAME = "category_name";
    public static final String TAG_CID = "cid";
    public static final String TAG_DESC = "mp3_description";
    public static final String TAG_DURATION = "mp3_duration";
    public static final String TAG_ID = "id";
    public static final String TAG_MP3_URL = "mp3_url";
    public static final String TAG_ROOT = "ONLINE_MP3";
    public static final String TAG_SONG_NAME = "mp3_title";
    public static final String TAG_THUMB_B = "mp3_thumbnail_b";
    public static final String TAG_THUMB_S = "mp3_thumbnail_s";
    public static final String URL_ABOUT_US_LOGO;
    public static final String URL_ARTIST;
    public static final String URL_CAT;
    public static final String URL_LATEST;
    public static final String URL_SONG;
    public static final String URL_SONG_BY_ARTIST;
    public static final String URL_SONG_BY_CAT;
    public static int adCount = 0;
    public static int adDisplay = 0;
    public static ArrayList arrayList_play = new ArrayList();
    public static String backStackPage = "";
    public static int columnWidth = 0;
    public static Context context;
    public static long currentProgress = 0L;
    public static SimpleExoPlayer exoPlayer;
    public static String frag = "";
    public static Boolean isAppFirst = Boolean.valueOf(true);
    public static Boolean isAppOpen = Boolean.valueOf(false);
    public static Boolean isBackStack = Boolean.valueOf(false);
    public static Boolean isFav = Boolean.valueOf(false);
    public static Boolean isFromNoti = Boolean.valueOf(false);
    public static Boolean isFromPush = Boolean.valueOf(false);
    public static Boolean isOnline = Boolean.valueOf(true);
    public static Boolean isPlayed = Boolean.valueOf(false);
    public static Boolean isPlaying = Boolean.valueOf(false);
    public static Boolean isRepeat = Boolean.valueOf(false);
    public static Boolean isSuffle = Boolean.valueOf(false);
    public static ItemAbout itemAbout;
    public static String loadedSongPage = "";
    public static int playPos = 0;
    public static String pushID = "";
    public static long secondaryProgress = 0L;
    private static final long serialVersionUID = 1L;
    public static int volume = 25;

    static
    {
        SERVER_URL = "http://www.viaviweb.in/envato/cc/online_mp3_demo/";
        URL_LATEST = (new StringBuilder()).append(SERVER_URL).append("api.php?latest").toString();
        URL_ARTIST = (new StringBuilder()).append(SERVER_URL).append("api.php?artist_list").toString();
        URL_CAT = (new StringBuilder()).append(SERVER_URL).append("api.php?cat_list").toString();
        URL_SONG_BY_CAT = (new StringBuilder()).append(SERVER_URL).append("api.php?cat_id=").toString();
        URL_SONG_BY_ARTIST = (new StringBuilder()).append(SERVER_URL).append("api.php?artist_name=").toString();
        URL_SONG = (new StringBuilder()).append(SERVER_URL).append("api.php?mp3_id=").toString();
        APP_DETAILS_URL = (new StringBuilder()).append(SERVER_URL).append("api.php").toString();
        URL_ABOUT_US_LOGO = (new StringBuilder()).append(SERVER_URL).append("images/").toString();
        columnWidth = 0;
        playPos = 0;
        currentProgress = 0L;
        secondaryProgress = 0L;
        adCount = 0;
        adDisplay = 2;
    }
}
