# Using `flutter build aar` Command

## Official Flutter Command

The recommended way to build AARs from a Flutter module/plugin is:

```bash
cd ~/weather_module
flutter build aar
```

## What `flutter build aar` Does

This command:
- ✅ Builds **debug**, **profile**, and **release** variants
- ✅ Creates a **local Maven repository** structure
- ✅ Generates **POM files** for proper dependency management
- ✅ Handles **Flutter dependencies** automatically
- ✅ Outputs to: `build/host/outputs/repo`

## Build Options

### Build All Variants (default)
```bash
flutter build aar
```

### Build Only Release
```bash
flutter build aar --no-debug --no-profile
```

### Build Only Debug and Release
```bash
flutter build aar --no-profile
```

## Output Structure

After running `flutter build aar`, you'll find:

```
build/host/outputs/repo
└── com/weathermodule/weather_module/
    ├── flutter_debug/
    │   ├── 1.0/
    │   │   ├── flutter_debug-1.0.aar
    │   │   └── flutter_debug-1.0.pom
    ├── flutter_profile/
    │   └── 1.0/
    │       ├── flutter_profile-1.0.aar
    │       └── flutter_profile-1.0.pom
    └── flutter_release/
        └── 1.0/
            ├── flutter_release-1.0.aar
            └── flutter_release-1.0.pom
```

## Integration in Android App

### 1. Update `settings.gradle`

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        
        // Local Maven repository from Flutter module
        maven {
            url = uri('~/weather_module/build/host/outputs/repo')
        }
        
        // Flutter's Maven repository
        maven {
            url = uri('https://storage.googleapis.com/download.flutter.io')
        }
    }
}
```

### 2. Update `app/build.gradle`

```gradle
dependencies {
    // ... other dependencies ...
    
    // Flutter module from Maven repository
    debugImplementation 'com.weathermodule.weather_module:flutter_debug:1.0'
    profileImplementation 'com.weathermodule.weather_module:flutter_profile:1.0'
    releaseImplementation 'com.weathermodule.weather_module:flutter_release:1.0'
}
```

## Comparison: `flutter build aar` vs Direct Gradle

| Aspect | `flutter build aar` | Direct Gradle |
|--------|-------------------|---------------|
| **Variants** | debug, profile, release | debug, release only |
| **Output** | Maven repository | Direct AAR files |
| **Dependencies** | Auto-handled | Manual setup required |
| **POM Files** | Generated | Not generated |
| **Flutter Integration** | Full support | Manual configuration |
| **Use Case** | Official/Recommended | Quick build/test |

## Current Setup (Direct Gradle)

We're currently using direct Gradle builds which outputs:
- `build/outputs/aar/weather_module-debug.aar`
- `build/outputs/aar/weather_module-release.aar`

This works but requires:
- ✅ Manual Flutter embedding JAR copying
- ✅ Manual dependency configuration
- ✅ Manual AAR file copying to app/libs

## Recommendation

For production use, **prefer `flutter build aar`** because:
1. It's the official Flutter approach
2. Better dependency management via Maven
3. Includes profile variant
4. Automatic POM generation
5. Better integration with Android build system

## Running the Command

To rebuild using `flutter build aar`:

```bash
cd ~/weather_module
flutter build aar
```

Then update your Android app to use the Maven repository instead of flatDir.

