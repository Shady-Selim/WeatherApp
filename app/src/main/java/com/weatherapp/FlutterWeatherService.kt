package com.weatherapp

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Service that communicates with the Flutter weather module
 * to get temperature data. The Flutter module handles all
 * API calls and network requests.
 */
class FlutterWeatherService(private val flutterEngine: FlutterEngine) {
    
    private val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "weather_module")
    
    /**
     * Gets today's temperature for the given city name.
     * This calls the Flutter module's Dart code which handles
     * the API request to wttr.in
     * 
     * @param cityName The name of the city
     * @return The current temperature in Celsius
     * @throws WeatherException if an error occurs
     */
    suspend fun getTodayTemperature(cityName: String): Double = suspendCancellableCoroutine { continuation ->
        val arguments = mapOf("cityName" to cityName)
        
        channel.invokeMethod("getTodayTemperature", arguments, object : MethodChannel.Result {
            override fun success(result: Any?) {
                when (result) {
                    is Number -> continuation.resume(result.toDouble())
                    is String -> {
                        val temp = result.toDoubleOrNull()
                        if (temp != null) {
                            continuation.resume(temp)
                        } else {
                            continuation.resumeWithException(
                                WeatherException("Invalid temperature format: $result")
                            )
                        }
                    }
                    null -> continuation.resumeWithException(
                        WeatherException("No temperature data received")
                    )
                    else -> continuation.resumeWithException(
                        WeatherException("Unexpected result type: ${result::class.simpleName}")
                    )
                }
            }
            
            override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
                val message = errorMessage ?: "Unknown error (code: $errorCode)"
                continuation.resumeWithException(WeatherException(message))
            }
            
            override fun notImplemented() {
                continuation.resumeWithException(
                    WeatherException("Weather module method not implemented. Make sure Flutter module is properly integrated.")
                )
            }
        })
    }
}

/**
 * Custom exception class for weather-related errors
 */
class WeatherException(message: String) : Exception(message)

