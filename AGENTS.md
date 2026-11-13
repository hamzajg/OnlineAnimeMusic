# AGENTS.md — OnlineAnimeMusic

Guidance for AI agents (and humans) working in this repository.

## Project

OnlineAnimeMusic — a legacy Android sample app for streaming anime music
(`com.thephoenixit.onlineanimemusic`): media player ambitions, browsable
catalogue, OneSignal push for new releases, Firebase ads. **Early-stage /
legacy**: pre-AndroidX support library, old SDK, `MainActivity` is a
placeholder — there is no player UI yet.
Stack: Java, Android (AGP 3.1.0 / Gradle 4.4), minSdk 15 / target+compile
SDK 26, appcompat + constraint-layout + Calligraphy, OneSignal 3.8.3,
Firebase Ads 12.0.1.

## Commands

Verified 2026-11-13 against CI (`.github/workflows/ci.yml`) and README.

| Task | Command |
|---|---|
| Build APK | `sh gradlew :app:assembleDebug` |
| Unit tests | `sh gradlew :app:testDebugUnitTest` (CI runs `sh gradlew test`) |

Requires: **JDK 8** (AGP 3.1/Gradle 4.4), Android SDK **API 26 +
build-tools 27.0.3** (`ANDROID_HOME` or `local.properties`).
`google-services.json` is NOT committed — Firebase build needs it locally.

## Architecture

```
app/src/main/java/com/thephoenixit/onlineanimemusic/
  app/MyApp.java          Application class
  utils/
    Constant.java         constants
    DBHelper.java         local DB helper (209 lines)
    ItemSong.java / ItemAbout.java   model POJOs
    JsonUtils.java        JSON parsing helpers
  views/
    MainActivity.java     PLACEHOLDER shell (no player UI)
    SplashActivity.java   splash screen
app/src/main/res/         resources
app/build.gradle          SDK levels, OneSignal app id placeholder,
                          Firebase ads dep
build.gradle / settings.gradle / gradlew(bat)   Gradle 4.4 wrapper
```

Only ~10 Java files — small legacy sample. No player/service layer yet.

## Conventions

- Plain Java, old support-library Android (`android.support.*`, not
  AndroidX) — match imports; don't mix AndroidX into this codebase
  without a migration plan.
- `utils/` holds helpers/models; `views/` holds activities — the existing
  (loose) split; keep new code in the same spirit.
- OneSignal app id is a `manifestPlaceholders` value in `app/build.gradle`
  — replace before publishing.
- KISS: legacy sample; no architecture ceremony.

## Landmines

- **Default branch is `master`, not `main`.**
- **JDK 8 + SDK 26/build-tools 27.0.3 hard requirements** — CI pins them;
  newer toolchains break AGP 3.1.
- `google-services.json` not committed — Firebase-dependent builds need
  it locally; don't commit secrets.
- ExoPlayer 2.7.2 was **removed** (artifact unpublished) — don't re-add
  without checking availability.
- `MainActivity` is a placeholder — the "media player" doesn't exist;
  don't document player features.
- Pre-AndroidX + minSdk 15 — modernising = AndroidX migration + dep
  upgrades; a separate project.
- Local clone may sit on `fix/build-wrapper-and-ci` — branch from
  `origin/master`.

## Agent rules

- Branch from `master`; name branches `feat/*`, `fix/*`, `chore/*`, `docs/*`.
- Docs-only changes still go through a PR; never commit to `master` directly.
- Verify before claiming: `sh gradlew :app:testDebugUnitTest` on JDK 8 +
  SDK 26 — report the real result.
- Keep changes minimal and consistent with surrounding code. No drive-by
  refactors.
- Update AGENTS.md / docs/ARCHITECTURE.md when features land, deps change,
  or the placeholder status changes.
