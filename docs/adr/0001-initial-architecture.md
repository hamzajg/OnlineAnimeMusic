# ADR 0001 — Initial architecture

Date: 2026-11-13 (baseline recorded retroactively)
Status: accepted (describes what is built)

## Context

A legacy Android sample app for anime music streaming — mostly a shell
today, kept as a starting point.

## Decisions

1. **Period Android stack, frozen.** AGP 3.1.0 / Gradle 4.4 / compileSdk
   26 / minSdk 15 / JDK 8. Rationale: reproducible legacy sample;
   modernisation is explicitly a separate effort (README says so).
2. **Pre-AndroidX support library.** `appcompat` + `constraint-layout` +
   Calligraphy with `android.support.*` imports. Rationale: matches the
   era; AndroidX migration deferred.
3. **OneSignal + Firebase Ads wired early.** Push (3.8.3) and ads (12.0.1)
   declared with manifest placeholders — integrations precede features.
   Rationale: the app's monetisation/engagement hooks were set up first.
4. **ExoPlayer removed, not upgraded.** v2.7.2 was declared but unused and
   is no longer published — dropped rather than migrated. Rationale:
   dead dependency elimination; player UI doesn't exist anyway.
5. **Loose package split.** `app/` (Application), `utils/` (helpers +
   POJOs), `views/` (activities) — no layers. Rationale: sample-app
   simplicity.
6. **google-services.json uncommitted.** Firebase config stays local.
   Rationale: no secrets in the repo.

## Consequences / open questions

- "Streaming" is aspirational — no player, service, or catalogue code.
  First real feature should decide the media architecture (and likely
  revisit ExoPlayer/Media3).
- minSdk 15 + old support libs constrain every future feature.
- Any AndroidX migration should be its own ADR'd project.
