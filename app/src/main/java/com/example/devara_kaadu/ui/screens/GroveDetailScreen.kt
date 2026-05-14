package com.example.devara_kaadu.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.viewmodel.GroveViewModel
import com.example.devara_kaadu.ui.theme.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroveDetailScreen(
    groveId: Int,
    onBack: () -> Unit,
    onExploreSpecies: () -> Unit,
    onReportIssue: () -> Unit,
    vm: GroveViewModel = viewModel()
) {
    val grove by vm.selectedGrove.collectAsStateWithLifecycle()

    LaunchedEffect(groveId) { vm.loadGroveById(groveId) }

    if (grove == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = ForestGreen700)
        }
        return
    }

    val g = grove!!
    val nativeSpecies = remember(g) {
        try { Gson().fromJson<List<String>>(g.nativeSpeciesJson, object : TypeToken<List<String>>() {}.type) ?: emptyList() }
        catch (e: Exception) { emptyList() }
    }
    val birdSpecies = remember(g) {
        try { Gson().fromJson<List<String>>(g.birdSpeciesJson, object : TypeToken<List<String>>() {}.type) ?: emptyList() }
        catch (e: Exception) { emptyList() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(g.name, color = SacredGold, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = ForestGreen200)
                    }
                },
                actions = {
                    IconButton(onClick = { vm.markAsVisited(g.id) }) {
                        Icon(
                            if (g.isVisited) Icons.Default.CheckCircle else Icons.Default.AddCircleOutline,
                            contentDescription = "Mark visited",
                            tint = if (g.isVisited) AlertGreen else ForestGreen200
                        )
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
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {
            // Hero image/cover
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Brush.verticalGradient(listOf(ForestGreen900, ForestGreen700)))
            ) {
                Text("🌳", fontSize = 80.sp, modifier = Modifier.align(Alignment.Center))
                Column(
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    AssistChip(
                        onClick = {}, label = { Text(g.type) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = SacredGold.copy(alpha = 0.9f), labelColor = EarthBrown900
                        )
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(g.name, style = MaterialTheme.typography.headlineSmall,
                        color = Color.White, fontWeight = FontWeight.Bold)
                    Text("${g.village}, ${g.district}", style = MaterialTheme.typography.bodyMedium,
                        color = ForestGreen200)
                    Text("Deity: ${g.deity}", style = MaterialTheme.typography.bodySmall,
                        color = SacredGold)
                }
            }

            // Quick facts row
            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    Triple("📍", "GPS", "${String.format("%.4f", g.latitude)}°N"),
                    Triple("🌳", "Trees", "${g.treeCount}+"),
                    Triple("🌿", "Area", "${g.areaHectares} ha")
                ).forEach { (emoji, label, value) ->
                    Card(Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = ForestGreen100)) {
                        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(emoji, fontSize = 18.sp)
                            Text(value, style = MaterialTheme.typography.labelLarge, color = ForestGreen900, fontWeight = FontWeight.Bold)
                            Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        }
                    }
                }
            }

            // === MYTHOLOGY CARD ===
            SectionCard(
                title = "🕉️ Sacred Legend & Tradition",
                subtitle = "Traditional Beliefs",
                backgroundColor = EarthBrown100
            ) {
                Text(g.legendStory, style = MaterialTheme.typography.bodyMedium,
                    color = EarthBrown900, lineHeight = 24.sp)
                Spacer(Modifier.height(12.dp))
                DetailRow("Traditions", g.traditions)
                DetailRow("Ritual Practices", g.ritualPractices)
            }

            // === ECOLOGY CARD ===
            SectionCard(
                title = "🔬 Scientific & Ecological Facts",
                subtitle = "Science-Based Information",
                backgroundColor = ForestGreen100
            ) {
                Text(g.scientificInfo, style = MaterialTheme.typography.bodyMedium,
                    color = ForestGreen900, lineHeight = 24.sp)
                Spacer(Modifier.height(12.dp))
                DetailRow("Biodiversity", g.biodiversityInfo)
                DetailRow("Climate Role", g.climateRole)
                DetailRow("Water Conservation", g.waterRole)
                DetailRow("Water Sources", g.waterSources)
                DetailRow("Ecological Importance", g.ecologicalImportance)
            }

            // Native species chips
            if (nativeSpecies.isNotEmpty()) {
                SectionCard("🌿 Native Species", "Flora Found Here", ForestGreen100) {
                    com.example.devara_kaadu.ui.components.ChipRow(nativeSpecies, ForestGreen700)
                }
            }

            // Bird species chips
            if (birdSpecies.isNotEmpty()) {
                SectionCard("🦜 Bird Species", "Avifauna", ForestGreen100) {
                    com.example.devara_kaadu.ui.components.ChipRow(birdSpecies, EarthBrown700)
                }
            }

            // Rare medicinal plants
            if (g.rareMedicinalPlants.isNotBlank()) {
                SectionCard("💊 Rare Medicinal Plants", "Traditional Medicine", EarthBrown100) {
                    Text(g.rareMedicinalPlants, style = MaterialTheme.typography.bodyMedium,
                        color = EarthBrown900)
                }
            }

            // Action buttons
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { vm.markAsVisited(g.id) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = ForestGreen800)
                ) {
                    Icon(Icons.Default.Shield, null, modifier = Modifier.padding(end = 8.dp))
                    Text("Become Guardian of This Grove")
                }
                OutlinedButton(
                    onClick = onExploreSpecies,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, ForestGreen700)
                ) {
                    Text("🔬 Explore Species", color = ForestGreen800)
                }
                OutlinedButton(
                    onClick = onReportIssue,
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, AlertRed)
                ) {
                    Text("⚠️ Report an Issue", color = AlertRed)
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionCard(
    title: String, subtitle: String,
    backgroundColor: androidx.compose.ui.graphics.Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = MaterialTheme.shapes.large
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium,
                color = ForestGreen900, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.labelSmall, color = TextMuted)
            HorizontalDivider(Modifier.padding(vertical = 10.dp), color = ForestGreen200)
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    if (value.isBlank()) return
    Column(Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium,
            color = TextMuted, fontWeight = FontWeight.SemiBold)
        Text(value, style = MaterialTheme.typography.bodySmall,
            color = ForestGreen900, lineHeight = 20.sp)
    }
}
