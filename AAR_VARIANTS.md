# AAR Variants Configuration

## Generated AAR Files

Both debug and release variants of the weather module AAR have been generated:

### Debug AAR
- **File**: `app/libs/weather_module-debug.aar`
- **Location**: `~/weather_module/android/build/outputs/aar/weather_module-debug.aar`
- **Size**: 14KB
- **Use**: Development and debugging
- **Includes**: Debug symbols, debuggable code

### Release AAR
- **File**: `app/libs/weather_module-release.aar`
- **Location**: `~/weather_module/android/build/outputs/aar/weather_module-release.aar`
- **Size**: 14KB
- **Use**: Production builds
- **Includes**: Optimized code, no debug symbols

## Build Configuration

The `app/build.gradle` is configured to use the appropriate variant:

```gradle
// Debug variant - includes debug symbols and allows debugging
debugImplementation(name: 'weather_module-debug', ext: 'aar')

// Release variant - optimized for production
releaseImplementation(name: 'weather_module-release', ext: 'aar')
```

## How It Works

- **Debug Build**: When you build a debug APK, Gradle automatically uses `weather_module-debug.aar`
- **Release Build**: When you build a release APK, Gradle automatically uses `weather_module-release.aar`

## Building Variants

### Build Debug AAR
```bash
cd ~/weather_module/android
./gradlew assembleDebug
```

### Build Release AAR
```bash
cd ~/weather_module/android
./gradlew assembleRelease
```

### Build Both
```bash
cd ~/weather_module/android
./gradlew assemble
```

## Contents

Both AARs contain:
- `classes.jar` - Compiled Kotlin classes (WeatherModulePlugin)
- `AndroidManifest.xml` - Manifest with INTERNET permission
- `R.txt` - Resource references
- Metadata files

The main difference is in optimization levels and debug information.

## Integration Status

✅ Both AAR files copied to `app/libs/`  
✅ build.gradle configured with debugImplementation and releaseImplementation  
✅ Ready to use in Android app  

## Notes

- Debug AAR includes additional debugging information
- Release AAR is optimized for production performance
- Gradle automatically selects the correct variant based on build type
- Both are built with Kotlin 2.0.0

