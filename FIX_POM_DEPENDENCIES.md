# Fix: POM Dependencies Can't Be Found

## Problem

The dependency `com.weathermodule.weather_module:flutter_debug:1.0.0` can't be found because:

1. **Maven repository doesn't exist yet** - `flutter build aar` hasn't been run
2. **POM files not generated** - Need to build the AARs using Flutter's command

## Current Solution (Temporary)

The app has been reverted to use `flatDir` with direct AAR files:
```gradle
debugImplementation(name: 'weather_module-debug', ext: 'aar')
releaseImplementation(name: 'weather_module-release', ext: 'aar')
```

This works immediately with the AAR files in `app/libs/`.

## Proper Solution: Use POM Files

### Step 1: Generate POM Files

Run from your local machine (Flutter SDK required):
```bash
cd ~/weather_module
flutter build aar
```

This creates the Maven repository at:
```
~/weather_module/build/host/outputs/repo/
```

### Step 2: Verify Generated Structure

After running `flutter build aar`, check the structure:
```bash
ls -la ~/weather_module/build/host/outputs/repo/com/weathermodule/weather_module/
```

You should see:
```
flutter_debug/
flutter_release/
flutter_profile/
```

### Step 3: Check Actual Group ID and Artifact Names

The actual group ID and artifact names depend on your Flutter module configuration. Check the generated POM files:

```bash
cat ~/weather_module/build/host/outputs/repo/com/weathermodule/weather_module/flutter_debug/1.0.0/flutter_debug-1.0.0.pom
```

Look for:
- `<groupId>` - This is the group ID to use
- `<artifactId>` - This is the artifact ID to use
- `<version>` - This is the version to use

### Step 4: Update app/build.gradle

Based on the actual POM file, update your dependencies:

```gradle
// Replace with actual values from POM file
debugImplementation 'GROUP_ID:ARTIFACT_ID:VERSION'
releaseImplementation 'GROUP_ID:ARTIFACT_ID:VERSION'
```

**Common formats:**
- Group ID might be: `com.weathermodule` or `com.weathermodule.weather_module`
- Artifact ID is typically: `flutter_debug`, `flutter_release`, `flutter_profile`
- Version: `1.0.0` (from pubspec.yaml)

### Step 5: Verify Repository Path

Ensure `settings.gradle` points to the correct repository:

```gradle
maven {
    url = uri("${System.getProperty('user.home')}/weather_module/build/host/outputs/repo")
}
```

Or use an absolute path:
```gradle
maven {
    url = uri("/Users/shady/weather_module/build/host/outputs/repo")
}
```

### Step 6: Sync Gradle

In Android Studio:
1. File → Sync Project with Gradle Files
2. Check if dependencies resolve
3. Build the project

## Alternative: Keep Using flatDir

If you prefer to keep using direct AAR files (simpler but less standard):

1. Build AARs using Gradle:
   ```bash
   cd ~/weather_module/android
   ./gradlew assembleDebug assembleRelease
   ```

2. Copy AARs to app/libs:
   ```bash
   cp ~/weather_module/android/build/outputs/aar/*.aar ~/AndroidStudioProjects/WeatherApp/app/libs/
   ```

3. Use flatDir dependencies (already configured):
   ```gradle
   debugImplementation(name: 'weather_module-debug', ext: 'aar')
   releaseImplementation(name: 'weather_module-release', ext: 'aar')
   ```

## Troubleshooting

### Issue: Still can't find dependency after flutter build aar

1. **Check repository path**: Verify the path in `settings.gradle` matches actual location
2. **Check POM file**: Verify group ID, artifact ID, and version match your dependency declaration
3. **Check file permissions**: Ensure Gradle can read the Maven repository directory
4. **Invalidate caches**: File → Invalidate Caches / Restart in Android Studio

### Issue: Wrong group ID or artifact ID

The group ID format can vary. Common patterns:
- Based on package: `com.weathermodule.weather_module`
- Based on group in build.gradle: `com.weathermodule`
- Check the actual POM file to be sure

### Issue: Version mismatch

Ensure version in `build.gradle` matches version in:
- `pubspec.yaml` (for Flutter module)
- `build.gradle` (for Android module)
- Generated POM files

## Current Status

✅ **Working**: Using flatDir with direct AAR files  
⏳ **Pending**: Need to run `flutter build aar` to generate POM files  
📋 **Next**: After generating POM, update dependencies and verify group/artifact IDs

