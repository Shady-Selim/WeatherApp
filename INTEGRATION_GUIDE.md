# Flutter Module AAR Integration Guide

This guide explains how to integrate the Flutter weather module AAR into this Android Compose app.

## Current Implementation

The app currently uses a native Kotlin `WeatherService` that implements the same functionality as the Dart weather service. This approach:
- ✅ No Flutter SDK required
- ✅ Smaller app size
- ✅ Faster startup
- ✅ Simpler build process

## Integrating the Flutter Module AAR

### Prerequisites

1. Flutter SDK installed and in PATH
2. Built weather_module AAR file
3. Android Studio with Flutter plugin (optional, for full Flutter embedding)

### Step 1: Build the Flutter Module AAR

```bash
cd ~/weather_module/android
./gradlew assembleRelease
```

Copy the AAR to your app:
```bash
cp ~/weather_module/android/build/outputs/aar/build-release.aar \
   ~/AndroidStudioProjects/WeatherApp/app/libs/
```

### Step 2: Update build.gradle

In `app/build.gradle`, uncomment/add the AAR dependency:

```gradle
dependencies {
    // ... other dependencies ...
    
    // Weather Module AAR
    implementation(name: 'build-release', ext: 'aar')
    
    // Flutter embedding (required to run Dart code)
    implementation 'io.flutter:flutter_embedding:1.0.0'
}
```

### Step 3: Set up Flutter Embedding

To use the Dart code from the weather module, you need to set up Flutter embedding. This involves:

1. **Initialize Flutter Engine** in `MainActivity`:
```kotlin
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {
    private val CHANNEL = "weather_module"
    
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                if (call.method == "getTodayTemperature") {
                    val cityName = call.argument<String>("cityName")
                    // Call will be handled by Dart code in the module
                    result.notImplemented()
                } else {
                    result.notImplemented()
                }
            }
    }
}
```

2. **Include Dart Code**: The Dart code from the weather module needs to be compiled and bundled. This typically requires:
   - Setting up a Flutter module project
   - Compiling the Dart code to native code
   - Including it in the Android app

### Step 4: Call Dart Code via MethodChannel

In your Compose UI, call the Dart method:

```kotlin
// This requires Flutter engine to be initialized
val methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "weather_module")
methodChannel.invokeMethod("getTodayTemperature", cityName) { result ->
    if (result is Double) {
        // Update UI with temperature
    } else {
        // Handle error
    }
}
```

## Alternative: Hybrid Approach

Instead of full Flutter embedding, you could:

1. Keep the Kotlin `WeatherService` (current implementation)
2. Use the AAR's plugin class for other features if needed
3. Share business logic between Flutter and Android

## Recommendation

For a native Android app, **the current Kotlin implementation is recommended** because:
- Simpler architecture
- No Flutter runtime overhead
- Easier to maintain
- Better performance

Use Flutter embedding only if you need to:
- Share code between Flutter and Android apps
- Use Flutter-specific features
- Maintain a single codebase for multiple platforms

## Building and Testing

After integration:

1. Sync Gradle files
2. Build the app: `./gradlew assembleDebug`
3. Run on device/emulator
4. Test weather fetching functionality

## Troubleshooting

### AAR Not Found
- Ensure AAR is in `app/libs/` directory
- Check `flatDir` repository is configured in `build.gradle`

### Flutter Embedding Errors
- Ensure Flutter SDK is properly installed
- Check Flutter embedding version matches Flutter SDK version
- Verify Dart code is compiled and bundled

### MethodChannel Not Working
- Verify channel name matches between Android and Dart code
- Check Flutter engine is initialized before calling methods
- Ensure Dart code is properly loaded

