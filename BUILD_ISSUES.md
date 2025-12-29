# Build and Runtime Issues

## Current Status

The Android app is set up to call the Flutter module, but there are architectural challenges that need to be addressed:

### Issue 1: Flutter Engine Initialization

The current `MainActivity` tries to initialize a Flutter engine, but:
- Flutter embedding requires compiled Dart code to be included
- The Dart code from the weather module needs to be compiled and bundled
- The method channel handler needs to be registered in Dart code

### Issue 2: Method Channel Direction

Currently, the Flutter plugin's Android side (`WeatherModulePlugin`) expects calls FROM Dart TO Android.
But we need Android TO call Dart code.

### Issue 3: Dart Code Not Included

The weather module's Dart code (`WeatherService.getTodayTemperature`) needs to:
1. Be compiled to native code
2. Be included in the app's assets
3. Register a method handler for "getTodayTemperature"

## Solutions

### Option 1: Simplify - Use Direct API Calls (Recommended for Now)

Since full Flutter embedding setup is complex, a simpler approach would be to:
- Have a minimal Kotlin service that calls the API
- Keep the same interface as the Flutter module expects
- This allows the app to work immediately

### Option 2: Full Flutter Embedding Setup

To properly use the Flutter module:
1. Create a Flutter module structure in the Android project
2. Include the weather_module as a dependency
3. Compile the Dart code
4. Set up proper method channel handlers
5. Bundle the Flutter assets

This requires:
- Flutter SDK installed
- Proper Flutter module setup
- Additional build configuration

### Option 3: Modify Flutter Plugin Architecture

Modify the weather module plugin to:
- Accept calls from Android
- Make API calls directly in the Android plugin code
- Return results to Android

This would make the plugin work without full Flutter embedding.

## Recommendation

For immediate testing and development, Option 1 (simplified API calls) is recommended.
For production with code sharing between Flutter and Android, Option 2 (full Flutter embedding) is needed.

