# OnlineAnimeMusic

An Android app for streaming anime music.

## What it does

`OnlineAnimeMusic` (`com.thephoenixit.onlineanimemusic`) plays anime soundtracks
and themes, with a media player, a browsable catalogue, and push notifications
for new releases.

## Tech stack

- **Language:** Java
- **Min SDK:** 15 · **Target/compile SDK:** 26
- **Media:** ExoPlayer 2.7.2
- **UI:** Android Support Library (`appcompat`, `constraint-layout`), Calligraphy
- **Push:** OneSignal 3.8.3
- **Ads:** Firebase Ads 12.0.1

## Project layout

```
app/
  src/main/java/com/thephoenixit/onlineanimemusic/
    app/      # application class
    utils/    # helpers
    views/    # activities/fragments and adapters
  src/main/res/
  build.gradle
build.gradle
settings.gradle
```

## Building

The project targets SDK 26 and has no Gradle wrapper checked in. Open it in
Android Studio (or run Gradle with `ANDROID_HOME` pointing at an SDK that has
API 26 installed):

```bash
gradle :app:assembleDebug
```

## Configuration

The OneSignal app id is set in `app/build.gradle`. Replace it with your own
before publishing. The Firebase configuration file (`google-services.json`) is
not committed.

## Tests

```bash
gradle :app:testDebugUnitTest
```

## Status

Legacy sample app. It uses the pre-AndroidX support library and an old SDK and
ExoPlayer version; modernizing it would require an AndroidX migration and a
dependency upgrade.
