# Unit Tests for Tiny Music Player

This directory contains comprehensive unit tests for the Tiny Music Player Android application.

## Test Files

### AudioPlayerTest.java
Tests for the `AudioPlayer` class that handles media playback:
- ✓ `isLooping()` behavior on different Android SDK versions
- ✓ `isPlaying()` delegation to MediaPlayer
- ✓ `setState()` for playing/pausing and looping
- ✓ `onCompletion()` stops the service
- ✓ `interrupt()` releases MediaPlayer resources

### ServiceTest.java
Tests for the `Service` class that manages the music player service:
- ✓ Service creation and initialization
- ✓ `onBind()` returns null (unbound service)
- ✓ `setState()` delegates to all components (AudioPlayer, HWListener, Notifications)
- ✓ `onStartCommand()` returns START_STICKY
- ✓ Handling of PLAY_PAUSE action
- ✓ Handling of KILL action

### HWListenerTest.java
Tests for the `HWListener` class that handles hardware button events:
- ✓ Constructor tests (with and without service)
- ✓ `onReceive()` with MEDIA_PLAY key event
- ✓ `onReceive()` with MEDIA_PAUSE key event
- ✓ `onReceive()` with MEDIA_PLAY_PAUSE key event
- ✓ `onReceive()` with MEDIA_STOP key event
- ✓ Ignoring ACTION_UP events
- ✓ Ignoring unsupported key codes
- ✓ `setState()` doesn't throw exceptions

### ExceptionsTest.java
Tests for the `Exceptions` class that handles error messages:
- ✓ IllegalArgument message constant
- ✓ IllegalState message constant
- ✓ IO message constant
- ✓ Security message constant
- ✓ `throwError()` doesn't throw exceptions

### LauncherTest.java
Tests for the `Launcher` activity that controls playback:
- ✓ All launcher constants (TYPE, NULL, PLAY_PAUSE, KILL, PLAY, PAUSE, LOOP)
- ✓ `onCreate()` with ACTION_VIEW intent
- ✓ `onCreate()` with ACTION_SEND intent
- ✓ `onNewIntent()` handling

## Running the Tests

### Command Line
```bash
./gradlew test
```

### Android Studio
1. Right-click on the `test` directory
2. Select "Run 'Tests in 'tinymusicplayer''"

### Running Individual Test Classes
```bash
./gradlew test --tests AudioPlayerTest
./gradlew test --tests ServiceTest
./gradlew test --tests HWListenerTest
./gradlew test --tests ExceptionsTest
./gradlew test --tests LauncherTest
```

## Test Coverage

The test suite provides comprehensive coverage of:
- **Core playback logic** (AudioPlayer)
- **Service lifecycle** (Service)
- **Hardware button handling** (HWListener)
- **Error handling** (Exceptions)
- **Activity flow** (Launcher)

## Testing Framework

- **JUnit 4.13.2** - Test framework
- **Mockito 4.8.0** - Mocking framework
- **Robolectric 4.9** - Android unit testing framework

## Notes

- Tests use Robolectric to run Android tests on the JVM without requiring an emulator
- Mockito is used to mock Android framework classes like MediaPlayer, Service, Context
- Tests are configured to run on Android SDK 21 (Lollipop) by default, with version-specific tests where needed
- Reflection is used in some tests to inject mocks into private fields for better unit testing isolation
