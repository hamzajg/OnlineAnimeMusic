# Validation — offline-music-player

## Traceability

| AC | Criterion | Scenarios | Wave(s) | Test evidence |
|----|-----------|-----------|---------|---------------|
| 1 | First launch seeds the bundled catalog into the song table | Catalog seeded on first launch, Seed is idempotent | 1 | CatalogSeederTest.java |
| 2 | MainActivity lists every seeded song with title, artist… | Song list shows catalog | 2 | (weak) CatalogSeederTest.java, PlayerControllerTest.java |
| 3 | Tapping a song starts playback and shows it as current | Offline playback, Play pause toggles, Playlist navigation wraps, Shuffle and repeat work, Tap song to play | 3 | PlayerControllerTest.java |
| 4 | Play/pause toggles playback state correctly | Offline playback, Play pause toggles, Playlist navigation wraps, Shuffle and repeat work, Tap song to play | 3 | PlayerControllerTest.java |
| 5 | Next/previous move through the playlist with wrap-around | Offline playback, Play pause toggles, Playlist navigation wraps, Shuffle and repeat work, Tap song to play | 3 | PlayerControllerTest.java |
| 6 | Shuffle changes pick order deterministically-seeded… | Offline playback, Play pause toggles, Playlist navigation wraps, Shuffle and repeat work, Tap song to play | 3 | PlayerControllerTest.java |
| 7 | The app works fully offline (no network calls on the play… | Offline playback, Play pause toggles, Playlist navigation wraps, Shuffle and repeat work, Tap song to play | 3 | PlayerControllerTest.java |

## Scenario coverage

- Scenarios: 8 total, 5 with detected test evidence, 1 with weak (token-match) evidence only, 2 without
- ✓ `Catalog seeded on first launch` (@w1)
- ✗ `Seed is idempotent` (@w1)
- ~ `Song list shows catalog` (@w2)
- ✓ `Playlist navigation wraps` (@w3)
- ✓ `Tap song to play` (@w3)
- ✓ `Play pause toggles` (@w3)
- ✓ `Shuffle and repeat work` (@w3)
- ✗ `Offline playback` (@w3)

## Demo script (manual walkthrough)

1. **Catalog seeded on first launch** — run the app, perform the scenario, observe the outcome.
2. **Song list shows catalog** — run the app, perform the scenario, observe the outcome.
3. **Tap song to play** — run the app, perform the scenario, observe the outcome.

## Gaps

- 2 scenario(s) lack detected test evidence — verify manually or add checks

Note: 'no evidence' means neither the scenario name nor a
token match was found in test files — verify by hand before
treating it as a real gap. '~' marks weak token-match evidence.

## Outcome follow-up

Optional; this is a post-release learning record, not a finish gate.
- Status: not yet measured
- Owner and review date: (record when known)
- Signal and observed result: (record after release; do not invent)
- Learning or next action: (record after review)