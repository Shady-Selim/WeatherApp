# Weather App - Android Compose

An Android Kotlin Compose application that fetches and displays weather temperature for any city using the wttr.in API.

## Features

- ✅ Modern Jetpack Compose UI
- ✅ Text field for city name input
- ✅ Real-time temperature fetching
- ✅ Error handling and loading states
- ✅ Clean Material Design 3 interface

## Project Structure

```
WeatherApp/
├── app/
│   ├── build.gradle              # App-level dependencies
│   └── src/main/
│       ├── AndroidManifest.xml   # App manifest
│       ├── java/com/weatherapp/
│       │   ├── MainActivity.kt   # Main Compose activity
│       │   └── WeatherService.kt # Weather API service
│       └── res/                  # Resources
├── build.gradle                  # Project-level config
├── settings.gradle               # Project settings
└── gradle.properties             # Gradle properties
```

## Setup

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK with API level 24+ (Android 7.0)
- Java JDK 17 or higher

### Build and Run

1. Open the project in Android Studio:
   ```bash
   cd ~/AndroidStudioProjects/WeatherApp
   ```

2. Sync Gradle files (Android Studio will do this automatically)

3. Run the app on an emulator or device

## Usage

1. Enter a city name in the text field (e.g., "London", "New York", "Tokyo")
2. Tap the "Get Temperature" button
3. The current temperature in Celsius will be displayed

## Architecture

This app is designed to use the **Flutter weather module** for all API calls. The Android app:
- ✅ Only handles UI (Compose TextField, Button, Text display)
- ✅ Calls the Flutter module via MethodChannel
- ✅ Receives temperature data from the Flutter module
- ❌ **Does NOT make any direct API calls**
- ❌ **Does NOT handle network requests**

All network logic, API calls, and error handling are done by the **Flutter module's Dart code**.

## Flutter Module Integration

The app uses `FlutterWeatherService` which communicates with the Flutter module via MethodChannel. 

**Important**: To use the Flutter module's Dart code, you need to:
1. Build the Flutter module AAR (see BUILD_AAR.md in weather_module)
2. Copy the AAR to `app/libs/`
3. Uncomment the AAR dependency in `build.gradle`
4. Set up Flutter embedding with the Dart code compiled and included

See `FLUTTER_INTEGRATION.md` for detailed setup instructions.

## API Details

The app uses the [wttr.in](https://wttr.in) weather service:
- **Endpoint**: `https://wttr.in/{cityName}?format=j1`
- **Format**: JSON
- **Free**: No API key required
- **Temperature**: Returns in Celsius

## Building the Flutter Module AAR

If you want to build the weather_module AAR:

```bash
cd ~/weather_module/android
./gradlew assembleRelease
```

The AAR will be at: `android/build/outputs/aar/build-release.aar`

## Dependencies

- **Jetpack Compose**: Modern declarative UI framework
- **Material Design 3**: Material You design system
- **OkHttp**: HTTP client for API calls
- **Kotlin Coroutines**: Asynchronous programming
- **AndroidX Lifecycle**: Lifecycle-aware components

## License

This project is provided as-is for educational purposes.

