# Build Flutter AAR with POM Files

## Quick Start

Run the provided script to build the AAR with POM files:

```bash
bash ~/AndroidStudioProjects/WeatherApp/RUN_FLUTTER_BUILD_AAR.sh
```

Or manually:

```bash
cd ~/weather_module
flutter build aar
```

## Configuration Status

✅ **POM dependencies configured** in `app/build.gradle`:
- `debugImplementation 'com.weathermodule.weather_module:flutter_debug:1.0.0'`
- `releaseImplementation 'com.weathermodule.weather_module:flutter_release:1.0.0'`

✅ **Maven repository configured** in `settings.gradle`:
- Points to: `~/weather_module/build/host/outputs/repo`

## After Running `flutter build aar`

### 1. Verify Generated Files

Check that the Maven repository was created:

```bash
ls -la ~/weather_module/build/host/outputs/repo/com/weathermodule/weather_module/
```

You should see:
- `flutter_debug/1.0.0/`
- `flutter_release/1.0.0/`
- `flutter_profile/1.0.0/` (optional)

### 2. Check POM Files

Verify the group ID and artifact ID in the generated POM:

```bash
cat ~/weather_module/build/host/outputs/repo/com/weathermodule/weather_module/flutter_debug/1.0.0/flutter_debug-1.0.0.pom
```

Look for:
```xml
<groupId>com.weathermodule.weather_module</groupId>
<artifactId>flutter_debug</artifactId>
<version>1.0.0</version>
```

### 3. Sync Gradle

In Android Studio:
1. **File → Sync Project with Gradle Files**
2. Check the Build output for any errors
3. If successful, the dependencies should resolve from the Maven repository

### 4. Build and Test

```bash
cd ~/AndroidStudioProjects/WeatherApp
./gradlew build
```

## Troubleshooting

### Issue: Dependencies still can't be found

1. **Verify repository path** in `settings.gradle`:
   ```groovy
   maven {
       url = uri("${System.getProperty('user.home')}/weather_module/build/host/outputs/repo")
   }
   ```
   
2. **Check absolute path**: Try using absolute path if relative doesn't work:
   ```groovy
   maven {
       url = uri("/Users/shady/weather_module/build/host/outputs/repo")
   }
   ```

3. **Verify POM file exists**:
   ```bash
   test -f ~/weather_module/build/host/outputs/repo/com/weathermodule/weather_module/flutter_debug/1.0.0/flutter_debug-1.0.0.pom && echo "POM exists" || echo "POM missing"
   ```

4. **Invalidate caches**: File → Invalidate Caches / Restart in Android Studio

### Issue: Wrong group ID or artifact ID

If the group ID in the POM differs from what's in `build.gradle`, update `build.gradle` to match.

Common variations:
- Group ID might be: `com.weathermodule` (without `.weather_module`)
- Check the actual POM file to be certain

### Issue: Version mismatch

Ensure version matches in:
- `pubspec.yaml`: `version: 1.0.0`
- Generated POM: `<version>1.0.0</version>`
- `app/build.gradle`: `:1.0.0`

## Expected Output Structure

After `flutter build aar`, you should see:

```
~/weather_module/build/host/outputs/repo/
└── com/
    └── weathermodule/
        └── weather_module/
            ├── flutter_debug/
            │   └── 1.0.0/
            │       ├── flutter_debug-1.0.0.aar
            │       ├── flutter_debug-1.0.0.pom
            │       └── maven-metadata.xml
            ├── flutter_release/
            │   └── 1.0.0/
            │       ├── flutter_release-1.0.0.aar
            │       ├── flutter_release-1.0.0.pom
            │       └── maven-metadata.xml
            └── flutter_profile/
                └── 1.0.0/
                    ├── flutter_profile-1.0.0.aar
                    ├── flutter_profile-1.0.0.pom
                    └── maven-metadata.xml
```

## Status

- ✅ POM dependencies configured in `app/build.gradle`
- ✅ Maven repository path configured in `settings.gradle`
- ⏳ **Pending**: Run `flutter build aar` to generate Maven repository
- ⏳ **Pending**: Sync Gradle after building

Once you run `flutter build aar`, the dependencies should resolve automatically!

