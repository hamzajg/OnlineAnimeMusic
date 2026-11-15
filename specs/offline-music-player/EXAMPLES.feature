# Offline Music Player — specifications by example
# Scenario tags @w<N> map items to delivery waves (see WAVES.md / BACKLOG.md).

Feature: Offline Music Player

  @w1 @happy-path
  Scenario: Catalog seeded on first launch
    Given the app data directory is empty
    When the app launches for the first time
    Then the song table contains every entry from assets/catalog.json
    And each entry points at a bundled audio file under assets/raw

  @w1
  Scenario: Seed is idempotent
    Given the catalog was already seeded
    When the app launches again
    Then the song table is not duplicated or corrupted

  @w2 @happy-path
  Scenario: Song list shows catalog
    Given the catalog is seeded
    When the listener opens MainActivity
    Then every song is listed with title, artist and duration

  @w2
  Scenario: Playlist navigation wraps
    Given a playlist of three songs and the first is current
    When the listener presses previous
    Then the last song becomes current
    When the listener presses next twice
    Then the first song becomes current again

  @w3 @happy-path
  Scenario: Tap song to play
    Given the song list is visible and playback is stopped
    When the listener taps "Song Two"
    Then "Song Two" becomes the current track and playback starts

  @w3
  Scenario: Play pause toggles
    Given "Song One" is playing
    When the listener presses pause
    Then playback pauses and position is retained
    When the listener presses play
    Then playback resumes from the retained position

  @w3
  Scenario: Shuffle and repeat work
    Given repeat is off and shuffle is on
    When the listener presses next
    Then the next song is one of the other tracks, never the current one
    When repeat-one is enabled on the last track
    And the listener presses next
    Then the same track restarts from the beginning

  @w3
  Scenario: Offline playback
    Given the device has no network connectivity
    When the listener plays any bundled song
    Then playback proceeds without any network request
