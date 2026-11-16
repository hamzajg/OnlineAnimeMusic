# Backlog: Offline Music Player

Items are USER STORIES in the parseable format below — keep the heading
# shape `## US<n> — <story>` and the field lines intact so `wave_plan` can
# parse them. Write the story as: "As a <role>, I want <goal>, so that
# <value>" — the title after the dash IS the story.

```
## US<n> — As a <role>, I want <goal>, so that <value>
- Scenarios: @w1, @w2
- Depends: US1, US3        (optional; omit for wave-1 items)
- Size: S|M|L
- Notes: <one line>
```

Order stories by user value; dependencies express technical order only.

## US1 — As a listener, I want a bundled catalog seeded on first launch, so that the app has music without a dead backend
- Scenarios: @w1
- Size: M
- Notes: assets/catalog.json + bundled WAVs in assets/music; CatalogSeeder into song table; idempotent; JVM tests for JSON→ItemSong parsing.

## US2 — As a listener, I want to browse the catalog in MainActivity, so that I can see what exists
- Scenarios: @w2
- Depends: US1
- Size: S
- Notes: ListView+ArrayAdapter over DBHelper.loadData(); layout row item; no new deps.

## US3 — As a listener, I want to play, pause, skip and shuffle songs, so that I can actually listen
- Scenarios: @w3
- Depends: US2
- Size: L
- Notes: PlayerController pure-Java playlist state machine + thin MediaPlayer adapter; play/pause/next/prev/repeat/shuffle; now-playing bar in MainActivity.
