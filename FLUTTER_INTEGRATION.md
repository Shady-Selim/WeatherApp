# Flutter Module Integration

## Overview

This Android app uses the Flutter weather module to handle all API calls. The Android app only needs:
- Internet permission (already in manifest)
- Flutter embedding to run the Dart code
- MethodChannel to communicate with the Flutter module

**The Flutter module handles all network requests and API logic.**

## How It Works

1. User enters city name in Android Compose UI
2. Android calls Flutter module via MethodChannel
3. Flutter module's Dart code makes the API call to wttr.in
4. Dart code returns the temperature
5. Android displays the result

## Setup Instructions

### Step 1: Build the Flutter Module AAR

```bash
cd ~/weather_module/android
./gradlew assembleRelease
```

### Step 2: Copy AAR to Android App

```bash
mkdir -p ~/AndroidStudioProjects/WeatherApp/app/libs
cp ~/weather_module/android/build/outputs/aar/build-release.aar \
   ~/AndroidStudioProjects/WeatherApp/app/libs/
```

### Step 3: Update build.gradle

In `app/build.gradle`, uncomment the AAR dependency:
```gradle
dependencies {
    // ... other dependencies ...
    
    // Weather Module AAR
    implementation(name: 'build-release', ext: 'aar')
}
```

### Step 4: Include Flutter Module Dart Code

To use the Dart code from the weather module, you need to include it in the Flutter engine. This requires:

1. **Create a Flutter entrypoint** that registers the method handler
2. **Include the Dart code** in the Flutter engine's assets

The easiest way is to create a minimal Flutter app structure or include the weather_module as a dependency in a Flutter module setup.

### Alternative: Use as Native Module

If you prefer not to set up full Flutter embedding, the current implementation uses `FlutterWeatherService` which expects the Flutter module's Dart code to be available. You would need to:

1. Set up Flutter module project structure
2. Include weather_module as a dependency
3. Register the method handler in Dart code
4. Build and include the Flutter assets

## Current Implementation

The app currently uses `FlutterWeatherService` which:
- Initializes Flutter engine
- Calls the Flutter module via MethodChannel
- Expects the Dart code to handle the `getTodayTemperature` method

The Dart code in the weather module needs to be:
1. Compiled to native code
2. Included in the app's assets
3. Registered as a method handler

## Notes

- The Android app **does NOT** make direct API calls
- All network requests are handled by the Flutter module's Dart code
- Only INTERNET permission is needed (already in manifest)
- The Flutter module encapsulates all weather API logic

