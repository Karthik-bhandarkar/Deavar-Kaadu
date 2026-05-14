package com.example.devara_kaadu.ui.screens

import android.Manifest
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.data.model.Grove
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.utils.LocationHelper
import com.example.devara_kaadu.viewmodel.GroveViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun NearbyGroveScreen(
    onBack: () -> Unit,
    onGroveClick: (Int) -> Unit,
    vm: GroveViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val allGroves by vm.groves.collectAsStateWithLifecycle()

    var userLat by remember { mutableStateOf<Double?>(null) }
    var userLng by remember { mutableStateOf<Double?>(null) }
    var isLocating by remember { mutableStateOf(false) }
    var nearbyGroves by remember { mutableStateOf<List<Pair<Grove, Float>>>(emptyList()) }

    val permState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    )

    fun findNearby() {
        scope.launch {
            isLocating = true
            val loc = LocationHelper.getCurrentLocation(context)
            if (loc != null) {
                userLat = loc.latitude; userLng = loc.longitude
                nearbyGroves = allGroves.map { grove ->
                    val dist = LocationHelper.distanceMeters(loc.latitude, loc.longitude, grove.latitude, grove.longitude)
                    grove to dist
                }.filter { it.second <= 50_000f }.sortedBy { it.second }
            } else {
                // Simulate with Bangalore coordinates for demo
                userLat = 12.9716; userLng = 77.5946
                nearbyGroves = allGroves.map { grove ->
                    val dist = LocationHelper.distanceMeters(12.9716, 77.5946, grove.latitude, grove.longitude)
                    grove to dist
                }.sortedBy { it.second }
            }
            isLocating = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nearby Groves", color = SacredGold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = ForestGreen200)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ForestGreen900)
            )
        },
        containerColor = Parchment
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // GPS radar visualization
            Spacer(Modifier.height(24.dp))
            RadarAnimation(isLocating = isLocating)
            Spacer(Modifier.height(16.dp))

            if (!permState.allPermissionsGranted) {
                PermissionRequest(onRequest = { permState.launchMultiplePermissionRequest() })
            } else {
                Button(
                    onClick = { findNearby() },
                    enabled = !isLocating,
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen800)
                ) {
                    Icon(Icons.Default.MyLocation, null, Modifier.padding(end = 6.dp))
                    Text(if (isLocating) "Locating..." else "Find Nearby Sacred Groves")
                }
            }

            userLat?.let { lat ->
                Spacer(Modifier.height(8.dp))
                Text("Your location: ${String.format("%.4f", lat)}°N, ${String.format("%.4f", userLng)}°E",
                    style = MaterialTheme.typography.labelSmall, color = TextMuted)
            }

            Spacer(Modifier.height(16.dp))

            if (nearbyGroves.isNotEmpty()) {
                Text("Groves Found Nearby", style = MaterialTheme.typography.titleMedium,
                    color = ForestGreen900, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp))

                nearbyGroves.take(5).forEach { (grove, distMeters) ->
                    NearbyGroveCard(
                        grove = grove, distanceMeters = distMeters,
                        onClick = { onGroveClick(grove.id) }
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun RadarAnimation(isLocating: Boolean) {
    val transition = rememberInfiniteTransition(label = "radar")
    val ring1 by transition.animateFloat(0f, 1f,
        infiniteRepeatable(tween(1500, easing = EaseOutCubic)), label = "r1")
    val ring2 by transition.animateFloat(0f, 1f,
        infiniteRepeatable(tween(1500, 500, EaseOutCubic)), label = "r2")

    Box(Modifier.size(180.dp), contentAlignment = Alignment.Center) {
        if (isLocating) {
            listOf(ring1, ring2).forEach { scale ->
                Box(
                    Modifier.size(180.dp).scale(scale).background(
                        ForestGreen400.copy(alpha = (1f - scale) * 0.3f), CircleShape
                    )
                )
            }
        }
        Box(
            Modifier.size(80.dp)
                .background(Brush.radialGradient(listOf(ForestGreen700, ForestGreen900)), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.MyLocation, null, tint = SacredGold, modifier = Modifier.size(36.dp))
        }
    }
}

@Composable
private fun PermissionRequest(onRequest: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = ForestGreen100),
        shape = MaterialTheme.shapes.large
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("📍", fontSize = 32.sp)
            Spacer(Modifier.height(8.dp))
            Text("Location Permission Needed", style = MaterialTheme.typography.titleSmall,
                color = ForestGreen900, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text("Allow location access to find sacred groves near you.",
                style = MaterialTheme.typography.bodySmall, color = TextMuted, textAlign = TextAlign.Center)
            Spacer(Modifier.height(12.dp))
            Button(onClick = onRequest, colors = ButtonDefaults.buttonColors(containerColor = ForestGreen800)) {
                Text("Allow Location")
            }
        }
    }
}

@Composable
private fun NearbyGroveCard(grove: Grove, distanceMeters: Float, onClick: () -> Unit) {
    val distKm = distanceMeters / 1000f
    val isVeryNear = distanceMeters <= 500f

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isVeryNear) ForestGreen100 else MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.large,
        border = if (isVeryNear) BorderStroke(2.dp, ForestGreen700) else null
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("🌳", fontSize = 32.sp)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                if (isVeryNear) {
                    Text("🌿 You are near a Sacred Grove!", style = MaterialTheme.typography.labelSmall,
                        color = AlertGreen)
                }
                Text(grove.name, style = MaterialTheme.typography.titleSmall,
                    color = ForestGreen900, fontWeight = FontWeight.Bold)
                Text("${grove.village}, ${grove.district}", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                Text("${String.format("%.1f", distKm)} km away",
                    style = MaterialTheme.typography.labelSmall, color = ForestGreen700)
            }
            Icon(Icons.Default.ChevronRight, null, tint = TextMuted)
        }
    }
}
