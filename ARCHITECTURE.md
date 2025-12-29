# Architecture Overview

## Design Principle

**The Android app does NOT make API calls. The Flutter module handles all network requests.**

## Flow Diagram

```
┌─────────────────────────────────────────┐
│      Android Compose UI                 │
│  - TextField (city input)               │
│  - Button (trigger fetch)               │
│  - Text (display temperature)           │
└──────────────┬──────────────────────────┘
               │
               │ User enters city name
               │
               ▼
┌─────────────────────────────────────────┐
│      FlutterWeatherService              │
│  - Calls Flutter module via             │
│    MethodChannel                        │
│  - No network code                      │
└──────────────┬──────────────────────────┘
               │
               │ MethodChannel: "getTodayTemperature"
               │
               ▼
┌─────────────────────────────────────────┐
│      Flutter Engine                     │
│  - Runs Dart code                       │
└──────────────┬──────────────────────────┘
               │
               │
               ▼
┌─────────────────────────────────────────┐
│      Flutter Weather Module             │
│  (Dart Code)                            │
│  - WeatherService.getTodayTemperature() │
│  - Makes HTTP request to wttr.in        │
│  - Handles errors                       │
│  - Returns temperature                  │
└──────────────┬──────────────────────────┘
               │
               │ HTTP GET
               │
               ▼
┌─────────────────────────────────────────┐
│      wttr.in API                        │
│  - Returns JSON with temperature        │
└─────────────────────────────────────────┘
```

## Key Points

1. **Android App Responsibilities:**
   - UI only (Compose components)
   - User input handling
   - Calling Flutter module via MethodChannel
   - Displaying results

2. **Flutter Module Responsibilities:**
   - All network requests
   - API communication
   - Error handling
   - Data parsing
   - Returning results to Android

3. **Permissions:**
   - Only INTERNET permission needed (already in manifest)
   - No other permissions required

4. **Dependencies:**
   - Flutter embedding (to run Dart code)
   - Weather module AAR (the Flutter plugin)
   - No HTTP client libraries needed in Android

## Separation of Concerns

| Component | Network Logic | API Calls | Error Handling |
|-----------|--------------|-----------|----------------|
| Android App | ❌ | ❌ | ❌ |
| Flutter Module | ✅ | ✅ | ✅ |

This architecture ensures:
- ✅ Single responsibility: Flutter module owns all API logic
- ✅ Code reuse: Same Dart code can be used in Flutter apps
- ✅ Maintainability: API changes only affect Flutter module
- ✅ Testability: Flutter module can be tested independently

