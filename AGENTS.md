# AGENTS.md — OnlineAnimeMusic

Guidance for AI agents (and humans) working in this repository.

## Project

OnlineAnimeMusic — an Android music player
(`com.thephoenixit.onlineanimemusic`): **offline MVP shipped** — bundled
catalog (`assets/catalog.json` + generated demo WAVs in `assets/music/`),
song list in `MainActivity`, and `PlayerController`/`MediaPlayerEngine`
playback with play/pause/next/prev/shuffle/repeat-one. The original remote
api.php backend (dead Envato demo) left the critical path. OneSignal push
and Firebase ads remain wired but unused by the player.
Stack: Java, Android (AGP 8.7.3 / Gradle 8.9), minSdk 24 /
compile+target SDK 34, AndroidX, JDK 17, appcompat + constraint-layout +
material + Calligraphy3, OneSignal 5.10.2, Firebase Ads 23.6.0.

## Commands

Verified 2026-11-13 against CI (`.github/workflows/ci.yml`) and README.

| Task | Command |
|---|---|
| Build APK | `sh gradlew :app:assembleDebug` |
| Unit tests | `sh gradlew :app:testDebugUnitTest` (CI runs `sh gradlew test`) |

Requires: **JDK 17**, Android SDK **API 34 + build-tools 34.0.0**
(`ANDROID_HOME` or `local.properties`).
`google-services.json` is NOT committed — Firebase build needs it locally.

## Architecture

```
app/src/main/java/com/thephoenixit/onlineanimemusic/
  app/MyApp.java          Application class
  utils/
    Constant.java         constants
    DBHelper.java         SQLite helper (song/recent tables, schema in onCreate)
    CatalogSeeder.java    seeds song table from assets/catalog.json (idempotent)
    PlayerController.java pure-Java playlist engine (Engine interface, JVM-tested)
    MediaPlayerEngine.java MediaPlayer adapter — streams assets via AssetFileDescriptor
    ItemSong.java / ItemAbout.java   model POJOs
    JsonUtils.java        JSON parsing helpers (legacy remote API)
  views/
    MainActivity.java     song list + now-playing bar (offline playback)
  assets/catalog.json + assets/music/*.wav   bundled catalog + demo audio
specs/offline-music-player/   spec-driven artifacts (SPEC/EXAMPLES/BACKLOG/WAVES/VALIDATION)
    SplashActivity.java   splash screen
app/src/main/res/         resources
app/build.gradle          SDK 34, OneSignal app id placeholder,
                          Firebase ads dep
build.gradle / settings.gradle / gradlew(bat)   Gradle 8.9 wrapper
```

Only ~10 Java files — small legacy sample. No player/service layer yet.

## Conventions

- Plain Java, AndroidX (`androidx.*`, `com.google.android.material.*`) —
  the support-library migration already happened; don't reintroduce
  `android.support.*`.
- `utils/` holds helpers/models; `views/` holds activities — the existing
  (loose) split; keep new code in the same spirit.
- OneSignal app id is a `manifestPlaceholders` value in `app/build.gradle`
  — replace before publishing.
- KISS: legacy sample; no architecture ceremony.

## Landmines

- **Default branch is `master`, not `main`.**
- **JDK 17 + SDK 34/build-tools 34.0.0 required** — CI pins them.
- `google-services.json` not committed — Firebase-dependent builds need
  it locally; don't commit secrets.
- ExoPlayer 2.7.2 was **removed** (artifact unpublished) — don't re-add
  without checking availability.
- Playback is offline-only by design: `PlayerController` takes an `Engine`
  interface; `MediaPlayerEngine` resolves `file:///android_asset/` URIs via
  `AssetFileDescriptor`. Don't add network playback without a spec.
- Calligraphy is `io.github.inflationx:calligraphy3` (ViewPump-based) —
  init lives in `MyApp.onCreate`.
- Local clone may sit on `fix/build-wrapper-and-ci` — branch from
  `origin/master`.

## Agent rules

- Branch from `master`; name branches `feat/*`, `fix/*`, `chore/*`, `docs/*`.
- Docs-only changes still go through a PR; never commit to `master` directly.
- Verify before claiming: `sh gradlew clean test --no-daemon` and
  `sh gradlew assembleDebug --no-daemon` on JDK 17; report the real result.
- Keep changes minimal and consistent with surrounding code. No drive-by
  refactors.
- Update AGENTS.md / docs/ARCHITECTURE.md when features land, deps change,
  or the placeholder status changes.
