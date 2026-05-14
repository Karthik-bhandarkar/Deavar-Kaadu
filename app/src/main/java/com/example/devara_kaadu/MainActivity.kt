package com.example.devara_kaadu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.devara_kaadu.navigation.NavGraph
import com.example.devara_kaadu.ui.theme.DevaraKaaduTheme
import com.example.devara_kaadu.utils.SoundManager

/**
 * Single Activity entry point for the Devara-Kaadu app.
 * Uses Jetpack Compose for all UI and Navigation Compose for routing.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before calling super
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val settingsManager = (application as DevaraKaaduApp).settingsManager

        setContent {
            val isDarkMode by settingsManager.isDarkModeFlow.collectAsStateWithLifecycle(initialValue = false)
            val natureSounds by settingsManager.natureSoundsFlow.collectAsStateWithLifecycle(initialValue = false)

            LaunchedEffect(natureSounds) {
                if (natureSounds) {
                    SoundManager.startNatureSounds()
                } else {
                    SoundManager.stopNatureSounds()
                }
            }

            DevaraKaaduTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }
}
