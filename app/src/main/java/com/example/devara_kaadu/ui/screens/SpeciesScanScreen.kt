package com.example.devara_kaadu.ui.screens

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.data.model.Species
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.viewmodel.SpeciesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeciesScanScreen(
    onBack: () -> Unit,
    vm: SpeciesViewModel = viewModel()
) {
    val isScanning by vm.isScanning.collectAsStateWithLifecycle()
    val scanProgress by vm.scanProgress.collectAsStateWithLifecycle()
    val scanResult by vm.scanResult.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Species Scan", color = SacredGold, fontWeight = FontWeight.Bold) },
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
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            // Scanner viewfinder
            ScannerViewfinder(isScanning = isScanning, progress = scanProgress)

            Spacer(Modifier.height(24.dp))

            Text(
                "Simulated AI Species Scanner",
                style = MaterialTheme.typography.titleMedium,
                color = ForestGreen900, fontWeight = FontWeight.Bold
            )
            Text(
                "Powered by local botanical knowledge base",
                style = MaterialTheme.typography.bodySmall,
                color = TextMuted, textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(Modifier.height(24.dp))

            // Scan buttons
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { vm.simulateScan() },
                    enabled = !isScanning,
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen800)
                ) {
                    Icon(Icons.Default.CameraAlt, null, modifier = Modifier.padding(end = 6.dp))
                    Text(if (isScanning) "Scanning..." else "Scan Species")
                }
                if (scanResult != null) {
                    OutlinedButton(onClick = { vm.clearScan() }) {
                        Text("Clear")
                    }
                }
            }

            // Progress bar during scan
            if (isScanning) {
                Spacer(Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { scanProgress },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
                    color = ForestGreen700, trackColor = ForestGreen100
                )
                Text("Analyzing botanical features...", style = MaterialTheme.typography.bodySmall,
                    color = TextMuted, modifier = Modifier.padding(top = 6.dp))
            }

            // Scan result
            scanResult?.let { species ->
                Spacer(Modifier.height(24.dp))
                SpeciesResultCard(species = species)
            }

            // Instructions when idle
            if (!isScanning && scanResult == null) {
                Spacer(Modifier.height(32.dp))
                InstructionsCard()
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ScannerViewfinder(isScanning: Boolean, progress: Float) {
    val scanLineAnim = rememberInfiniteTransition(label = "scan")
    val scanLineY by scanLineAnim.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing), RepeatMode.Reverse),
        label = "scanLine"
    )
    val pulseScale by scanLineAnim.animateFloat(
        initialValue = 0.95f, targetValue = 1.05f,
        animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .size(220.dp)
            .scale(if (isScanning) pulseScale else 1f)
    ) {
        // Viewfinder background
        Box(
            Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.extraLarge)
                .background(ForestGreen900)
                .border(2.dp, if (isScanning) SacredGold else ForestGreen600, MaterialTheme.shapes.extraLarge)
        ) {
            // Center icon
            Text("🌿", fontSize = 64.sp, modifier = Modifier.align(Alignment.Center))

            // Corner brackets
            listOf(
                Alignment.TopStart, Alignment.TopEnd,
                Alignment.BottomStart, Alignment.BottomEnd
            ).forEach { alignment ->
                Box(
                    Modifier
                        .size(24.dp)
                        .padding(4.dp)
                        .align(alignment)
                        .background(SacredGold, CircleShape)
                )
            }

            // Scan line animation
            if (isScanning) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .padding(horizontal = 8.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = (scanLineY * 200).dp)
                        .background(SacredGold.copy(alpha = 0.8f))
                )
            }
        }
    }
}

@Composable
private fun SpeciesResultCard(species: Species) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(56.dp).clip(CircleShape)
                        .background(Brush.linearGradient(listOf(ForestGreen800, ForestGreen600))),
                    contentAlignment = Alignment.Center
                ) { Text("🌿", fontSize = 28.sp) }
                Spacer(Modifier.width(12.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, null, tint = AlertGreen, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Species Identified!", style = MaterialTheme.typography.labelMedium, color = AlertGreen)
                    }
                    Text(species.name, style = MaterialTheme.typography.titleMedium,
                        color = ForestGreen900, fontWeight = FontWeight.Bold)
                    Text(species.scientificName, style = MaterialTheme.typography.bodySmall,
                        color = TextMuted)
                }
            }

            HorizontalDivider(Modifier.padding(vertical = 12.dp), color = ForestGreen100)

            // Kannada name
            InfoRow("🇮🇳 Kannada Name", species.kannadaName)
            InfoRow("🌿 Type", species.type)
            InfoRow("📊 Conservation", species.conservationStatus)
            InfoRow("💊 Medicinal Use", species.medicinalUse)
            InfoRow("🌍 Ecological Role", species.ecologicalRole)
            InfoRow("🕉️ Sacred Association", species.sacredAssociation)
            InfoRow("⭐ Fun Fact", species.funFact)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted)
        Text(value, style = MaterialTheme.typography.bodySmall, color = ForestGreen900, lineHeight = 18.sp)
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun InstructionsCard() {
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = ForestGreen100),
        shape = MaterialTheme.shapes.large
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("How to Use", style = MaterialTheme.typography.titleSmall,
                color = ForestGreen900, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            listOf(
                "📷 Point your camera at any plant, tree, or herb",
                "🔬 Tap 'Scan Species' to identify",
                "📚 Get scientific name, medicinal use & sacred significance",
                "🌿 Learn about ecological role in Sacred Groves"
            ).forEach { step ->
                Text(step, style = MaterialTheme.typography.bodySmall,
                    color = ForestGreen800, modifier = Modifier.padding(vertical = 3.dp), lineHeight = 18.sp)
            }
        }
    }
}
