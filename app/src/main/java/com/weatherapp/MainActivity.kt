package com.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var weatherService: DirectWeatherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize direct weather service (no FlutterEngine needed)
        // This avoids the libapp.so error by calling the API directly
        weatherService = DirectWeatherService()
        
        setContent {
            WeatherAppTheme {
                WeatherScreen(weatherService = weatherService)
            }
        }
    }
}

@Composable
fun WeatherScreen(weatherService: DirectWeatherService) {
    var cityName by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf<Double?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Weather App",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // City name input field
            OutlinedTextField(
                value = cityName,
                onValueChange = {
                    cityName = it
                    errorMessage = null // Clear error when user types
                },
                label = { Text("City Name") },
                placeholder = { Text("Enter city name (e.g., London, New York)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                enabled = !isLoading,
                singleLine = true
            )

            // Get Temperature button
            Button(
                onClick = {
                    if (cityName.trim().isNotEmpty()) {
                        isLoading = true
                        errorMessage = null
                        temperature = null
                        
                        coroutineScope.launch {
                            try {
                                // Call Flutter module to get temperature
                                // The Flutter module handles all API calls
                                val temp = weatherService.getTodayTemperature(cityName.trim())
                                temperature = temp
                                errorMessage = null
                            } catch (e: WeatherException) {
                                errorMessage = e.message
                                temperature = null
                            } catch (e: Exception) {
                                errorMessage = "Unexpected error: ${e.message}"
                                temperature = null
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        errorMessage = "Please enter a city name"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(if (isLoading) "Loading..." else "Get Temperature")
            }

            // Temperature display
            temperature?.let { temp ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Temperature",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${String.format("%.1f", temp)}°C",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = cityName,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            // Error message display
            errorMessage?.let { error ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF2196F3),
            primaryContainer = androidx.compose.ui.graphics.Color(0xFFBBDEFB),
            onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF1976D2),
            errorContainer = androidx.compose.ui.graphics.Color(0xFFFFCDD2),
            onErrorContainer = androidx.compose.ui.graphics.Color(0xFFC62828)
        ),
        content = content
    )
}
