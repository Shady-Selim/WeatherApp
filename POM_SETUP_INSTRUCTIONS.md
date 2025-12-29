# POM-Based Dependency Setup

## Overview

The Android app is now configured to use POM-based dependencies from `flutter build aar` instead of direct AAR files via `flatDir`.

## Step 1: Generate POM Files

Run the Flutter build command to generate the Maven repository with POM files:

```bash
cd ~/weather_module
flutter build aar
```

This will create:
```
~/weather_module/build/host/outputs/repo/
└── com/weathermodule/weather_module/
    ├── flutter_debug/1.0.0/
    │   ├── flutter_debug-1.0.0.aar
    │   └── flutter_debug-1.0.0.pom
    ├── flutter_profile/1.0.0/
    │   ├── flutter_profile-1.0.0.aar
    │   └── flutter_profile-1.0.0.pom
    └── flutter_release/1.0.0/
        ├── flutter_release-1.0.0.aar
        └── flutter_release-1.0.0.pom
```

## Step 2: Verify Configuration

### settings.gradle

The Maven repository is configured:
```gradle
maven {
    url = uri("${System.getProperty('user.home')}/weather_module/build/host/outputs/repo")
}
```

### app/build.gradle

POM-based dependencies are configured:
```gradle
// Debug variant
debugImplementation 'com.weathermodule.weather_module:flutter_debug:1.0.0'

// Profile variant
profileImplementation 'com.weathermodule.weather_module:flutter_profile:1.0.0'

// Release variant
releaseImplementation 'com.weathermodule.weather_module:flutter_release:1.0.0'
```

## Dependency Information

- **Group ID**: `com.weathermodule.weather_module`
- **Artifact IDs**: 
  - `flutter_debug` (debug builds)
  - `flutter_profile` (profile builds)
  - `flutter_release` (release builds)
- **Version**: `1.0.0` (from `pubspec.yaml`)

## Benefits of POM-Based Approach

✅ **Proper Dependency Management**: POM files contain dependency information  
✅ **Automatic Resolution**: Gradle resolves all transitive dependencies  
✅ **Version Management**: Better version conflict resolution  
✅ **Maven Standard**: Follows Maven repository conventions  
✅ **Profile Support**: Includes profile variant for performance testing  

## Fallback Configuration

The `flatDir` repository is still configured as a fallback:
```gradle
flatDir {
    dirs 'app/libs'
}
```

You can remove this after confirming POM-based dependencies work correctly.

## Verification

After running `flutter build aar`, verify the repository exists:

```bash
ls -la ~/weather_module/build/host/outputs/repo/com/weathermodule/weather_module/
```

Then sync Gradle in Android Studio - it should resolve the dependencies from the Maven repository.

## Troubleshooting

If dependencies fail to resolve:

1. **Check repository path**: Ensure `settings.gradle` points to the correct path
2. **Verify POM files exist**: Check that `.pom` files were generated
3. **Check version**: Ensure version in `build.gradle` matches `pubspec.yaml`
4. **Invalidate caches**: File → Invalidate Caches / Restart in Android Studio

## Next Steps

1. Run `flutter build aar` in the Flutter module
2. Sync Gradle in Android Studio
3. Verify dependencies resolve correctly
4. Build and test the app
5. (Optional) Remove `flatDir` fallback if POM approach works

