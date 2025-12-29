# Quick Start Guide

## Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK (API 24+)
- JDK 17 or higher

## Setup Steps

1. **Open the project in Android Studio:**
   ```bash
   cd ~/AndroidStudioProjects/WeatherApp
   ```
   Then open the folder in Android Studio

2. **Sync Gradle:**
   - Android Studio will automatically sync Gradle files
   - If not, click "Sync Now" when prompted
   - Or go to: File → Sync Project with Gradle Files

3. **Run the app:**
   - Click the "Run" button (green play icon)
   - Select an emulator or connected device
   - The app will build and launch

## Using the App

1. Enter a city name in the text field (e.g., "London", "New York", "Tokyo")
2. Tap the "Get Temperature" button
3. View the temperature result displayed in Celsius

## Project Location

The project is located at:
```
~/AndroidStudioProjects/WeatherApp
```

## Features Implemented

✅ Compose TextField for city name input  
✅ Button to trigger weather fetch  
✅ Temperature display in Compose Text/Card  
✅ Loading state indicator  
✅ Error handling and display  
✅ Modern Material Design 3 UI  

## Building from Command Line

```bash
cd ~/AndroidStudioProjects/WeatherApp
./gradlew assembleDebug
```

The APK will be at: `app/build/outputs/apk/debug/app-debug.apk`

## Notes

- The app uses a Kotlin implementation of the weather service (mirrors the Dart implementation)
- Internet permission is included in the manifest
- The app uses the free wttr.in API (no API key required)

