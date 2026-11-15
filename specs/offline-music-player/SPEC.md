# Spec: Offline Music Player

> Spec-driven development artifact. This file is the contract: implementation
> is done when every acceptance criterion maps to a scenario in
> `EXAMPLES.feature` and every scenario has a passing check.

## Problem

The app is a shell: `MainActivity` is a placeholder, the SQLite `song`
table starts empty, and the only catalog source is a dead remote API
(`viaviweb.in/envato/cc/online_mp3_demo`, an Envato demo backend from
~2015). There is no way to hear music — the product does not exist yet.

## Users

- **Listener** — opens the app, browses the bundled catalog, plays songs
  offline, controls playback.

## Scope (in)

- Bundled catalog: `assets/catalog.json` + generated demo audio in
  `assets/raw/`, seeded into the existing `song` table on first launch.
- Song list in `MainActivity`: title/artist/duration from `DBHelper.loadData()`.
- Playback: `MediaPlayer`-backed controller with play/pause, next/prev,
  position/duration, repeat and shuffle.
- Playlist logic as a pure-Java, JVM-unit-tested state machine; the
  `MediaPlayer` adapter stays thin.

## Scope (out)

- Reviving the remote API or any network fetch (dead dependency removed
  from the critical path, not replaced).
- Favorites/recent persistence UI (tables exist; wiring is a later feature).
- Notifications/media-session, streaming URLs, album art downloads.
- Google Play / Firebase surfaces.

## Acceptance criteria

<!-- Scenario(s): use scenario names verbatim from EXAMPLES.feature and/or
     @w<N> tags — ship_validate resolves both for traceability. -->

| # | Criterion | Scenario(s) |
|---|-----------|-------------|
| 1 | First launch seeds the bundled catalog into the song table | Catalog seeded on first launch |
| 2 | MainActivity lists every seeded song with title, artist, duration | Song list shows catalog |
| 3 | Tapping a song starts playback and shows it as current | Tap song to play |
| 4 | Play/pause toggles playback state correctly | Play pause toggles |
| 5 | Next/previous move through the playlist with wrap-around | Next previous wraps |
| 6 | Shuffle changes pick order deterministically-seeded; repeat-one loops | Shuffle and repeat work |
| 7 | The app works fully offline (no network calls on the play path) | Offline playback |

## Decisions

- Dead backend pivot: instead of reviving `api.php`, the MVP catalog is
  bundled assets — self-contained, demo-able, no secrets. The remote-API
  constants stay but leave the critical path.
- Demo audio is generated sine-wave WAVs (original, no licensing issues).
- `PlayerController` splits pure playlist/state logic (JVM tests) from a
  thin `MediaPlayer` adapter — keeps the KISS rule and makes the core
  unit-testable without an emulator.
- ListView + ArrayAdapter (framework) — no new dependencies; the repo has
  no RecyclerView and KISS beats adding one for a 3-song catalog.
