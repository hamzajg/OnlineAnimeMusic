# Architecture — OnlineAnimeMusic

Current-state map. Descriptive, not aspirational; see `docs/adr/` for why.

## Shape

```
OnlineAnimeMusic/   (Android Gradle, AGP 3.1.0 / Gradle 4.4)
├── app/
│   ├── build.gradle      compileSdk/target 26, minSdk 15,
│   │                     applicationId com.thephoenixit.onlineanimemusic,
│   │                     OneSignal 3.8.3 + Firebase Ads 12.0.1
│   └── src/main/java/com/thephoenixit/onlineanimemusic/
│       ├── app/MyApp.java           Application class
│       ├── utils/                   Constant, DBHelper, ItemSong,
│       │                            ItemAbout, JsonUtils
│       └── views/                   MainActivity (placeholder),
│                                    SplashActivity
├── build.gradle / settings.gradle / gradlew(bat)
└── .github/workflows/ci.yml   JDK 8 + SDK 26/27.0.3 → gradlew test
```

## Intended vs actual

The README describes a music-streaming app (player, catalogue, push for
new releases, ads) — but **only the shell exists**: `MainActivity` is a
placeholder, there's no player/service/catalogue implementation, and
ExoPlayer was removed. Treat everything beyond `utils/` POJOs + DB helper
+ splash as unbuilt.

## Dependencies of note

- **OneSignal 3.8.3** — push notifications; app id via manifest
  placeholder in `app/build.gradle`.
- **Firebase Ads 12.0.1** — needs `google-services.json` (not committed).
- **Pre-AndroidX support library** (`appcompat`, `constraint-layout`,
  Calligraphy) — legacy imports throughout.

## State

`DBHelper` (SQLite helper) exists but no feature uses it yet. No backend,
no API layer.

## Toolchain

JDK 8 + Android SDK 26 + build-tools 27.0.3 — pinned by AGP 3.1/Gradle 4.4.
