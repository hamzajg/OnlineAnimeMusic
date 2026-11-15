package com.thephoenixit.onlineanimemusic.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Seeds the local song catalog from the bundled assets/catalog.json.
 * The remote api.php backend this app was built for is dead, so the MVP
 * catalog ships inside the APK — no network needed on the play path.
 */
public final class CatalogSeeder {

    private static final String CATALOG_ASSET = "catalog.json";
    private static final String ASSET_URL_PREFIX = "file:///android_asset/";

    private CatalogSeeder() {
    }

    /** Parses catalog.json text into ItemSong rows. Pure — JVM-testable. */
    public static List<ItemSong> parseCatalog(String json) throws JSONException {
        List<ItemSong> songs = new ArrayList<>();
        JSONArray array = new JSONObject(json).getJSONArray("songs");
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            songs.add(new ItemSong(
                    o.getString("id"),
                    o.optString("category_id"),
                    o.optString("category_name"),
                    o.optString("artist"),
                    ASSET_URL_PREFIX + o.getString("audio"),
                    "",
                    "",
                    o.getString("title"),
                    o.optString("duration"),
                    o.optString("description")));
        }
        return songs;
    }

    /**
     * Inserts every bundled song into the song table when it is empty.
     * Idempotent: a populated table is left untouched.
     */
    public static void seedIfEmpty(Context context) throws IOException, JSONException {
        DBHelper db = new DBHelper(context);
        db.createDataBase();
        if (!db.loadData().isEmpty()) {
            return;
        }
        String json = readAsset(context, CATALOG_ASSET);
        for (ItemSong song : parseCatalog(json)) {
            db.addSong(song);
        }
    }

    private static String readAsset(Context context, String name) throws IOException {
        try (InputStream in = context.getAssets().open(name)) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) > 0) {
                out.write(buf, 0, n);
            }
            return out.toString("UTF-8");
        }
    }
}
