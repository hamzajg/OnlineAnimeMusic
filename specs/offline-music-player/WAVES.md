# Waves — offline-music-player

Each wave IS an iteration: a time-boxed, independently shippable
increment — one branch, one PR, green tests, demoable outcome.
Do not start wave N+1 until wave N merges.

## Wave 1 — iteration 1

Scenarios proven by this wave:
- Catalog seeded on first launch
- Seed is idempotent

| Item | Title | Size | Deps | Notes |
|------|-------|------|------|-------|
| US1 | As a listener, I want a bundled catalog seeded on first launch, so that the app has music without a dead backend | M | — | assets/catalog.json + bundled WAVs in assets/music; CatalogSeeder into song table; idempotent; JVM tests for JSON→ItemSong parsing. |

## Wave 2 — iteration 2

Scenarios proven by this wave:
- Song list shows catalog

| Item | Title | Size | Deps | Notes |
|------|-------|------|------|-------|
| US2 | As a listener, I want to browse the catalog in MainActivity, so that I can see what exists | S | US1 | ListView+ArrayAdapter over DBHelper.loadData(); layout row item; no new deps. |

## Wave 3 — iteration 3

Scenarios proven by this wave:
- Playlist navigation wraps
- Tap song to play
- Play pause toggles
- Shuffle and repeat work
- Offline playback

| Item | Title | Size | Deps | Notes |
|------|-------|------|------|-------|
| US3 | As a listener, I want to play, pause, skip and shuffle songs, so that I can actually listen | L | US2 | PlayerController pure-Java playlist state machine + thin MediaPlayer adapter; play/pause/next/prev/repeat/shuffle; now-playing bar in MainActivity. |
