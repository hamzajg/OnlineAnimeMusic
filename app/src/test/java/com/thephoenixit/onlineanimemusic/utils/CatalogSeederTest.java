package com.thephoenixit.onlineanimemusic.utils;

import org.json.JSONException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CatalogSeederTest {

    private static final String JSON = "{\"songs\":[{"
            + "\"id\":\"s1\",\"title\":\"Neon Sunrise\",\"artist\":\"Demo Ensemble\","
            + "\"category_id\":\"c1\",\"category_name\":\"Opening Themes\","
            + "\"description\":\"Bright opener\",\"duration\":\"0:06\","
            + "\"audio\":\"music/neon_sunrise.wav\"}]}";

    @Test
    public void parseCatalogMapsAllFields() throws JSONException {
        List<ItemSong> songs = CatalogSeeder.parseCatalog(JSON);
        assertEquals(1, songs.size());
        ItemSong s = songs.get(0);
        // covers scenario: Catalog seeded on first launch
        assertEquals("s1", s.getId());
        assertEquals("Neon Sunrise", s.getMp3Name());
        assertEquals("Demo Ensemble", s.getArtist());
        assertEquals("0:06", s.getDuration());
        assertEquals("Bright opener", s.getDescription());
        assertEquals("c1", s.getCategoryId());
        assertEquals("Opening Themes", s.getCategoryName());
        assertEquals("file:///android_asset/music/neon_sunrise.wav", s.getMp3Url());
    }

    @Test(expected = JSONException.class)
    public void parseCatalogRejectsMalformedJson() throws JSONException {
        CatalogSeeder.parseCatalog("{not json");
    }

    @Test(expected = JSONException.class)
    public void parseCatalogRequiresSongsArray() throws JSONException {
        CatalogSeeder.parseCatalog("{\"other\":[]}");
    }
}
