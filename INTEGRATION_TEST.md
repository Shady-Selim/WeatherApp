# Integration Test Plan

## Test Steps

### 1. Build Flutter Module AAR
- Navigate to `~/weather_module/android`
- Run `./gradlew assembleRelease` (or use gradle if available)
- Verify AAR is created at `build/outputs/aar/build-release.aar`

### 2. Copy AAR to Android App
- Copy AAR to `~/AndroidStudioProjects/WeatherApp/app/libs/`
- Uncomment AAR dependency in `app/build.gradle`

### 3. Fix Plugin Registration
- Check MainActivity plugin registration code
- Verify FlutterPluginBinding implementation
- Fix any compilation errors

### 4. Build Android App
- Sync Gradle in Android Studio
- Check for compilation errors
- Fix any missing dependencies or imports

### 5. Runtime Test
- Run app on emulator/device
- Enter city name
- Verify API call works
- Verify temperature is displayed

## Current Issues to Check

1. **FlutterPluginBinding**: The anonymous object implementation may need adjustment
2. **MethodChannel**: Verify channel name matches between Android and plugin
3. **Coroutines**: Ensure coroutines are properly configured
4. **Dependencies**: Check Flutter embedding version compatibility

## Expected Behavior

- User enters city name → Android calls Flutter plugin → Plugin makes API call → Returns temperature → Android displays result

