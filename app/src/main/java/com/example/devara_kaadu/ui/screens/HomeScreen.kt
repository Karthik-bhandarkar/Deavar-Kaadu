package com.example.devara_kaadu.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.navigation.Screen
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.viewmodel.SettingsViewModel

private data class DashboardItem(
    val emoji: String, val title: String, val subtitle: String,
    val route: String, val gradientColors: List<Color>
)

private fun getDashboardItems(isKannada: Boolean) = listOf(
    DashboardItem("🌳", if (isKannada) "ದೇವರಕಾಡು ಪಟ್ಟಿ" else "Grove Directory", if (isKannada) "ಪವಿತ್ರ ತಾಣಗಳು" else "Explore Sacred Sites", Screen.GroveDirectory.route, listOf(ForestGreen800, ForestGreen600)),
    DashboardItem("🔬", if (isKannada) "ಸಸ್ಯ ಸ್ಕ್ಯಾನ್" else "Species Scan", if (isKannada) "ಸಸ್ಯ ಗುರುತಿಸುವಿಕೆ" else "Identify Native Plants", Screen.SpeciesScan.route, listOf(EarthBrown700, EarthBrown500)),
    DashboardItem("📜", if (isKannada) "ಪುರಾಣ ಮತ್ತು ಕಥೆಗಳು" else "Myth & Legend", if (isKannada) "ಪವಿತ್ರ ಕಥೆಗಳು" else "Sacred Stories", Screen.MythLegend.route, listOf(SacredGoldDark, EarthBrown700)),
    DashboardItem("⚠️", if (isKannada) "ಸಂರಕ್ಷಣೆ ಎಚ್ಚರಿಕೆ" else "Conservation Alert", if (isKannada) "ಸಮಸ್ಯೆ ವರದಿ" else "Report Issues", Screen.ConservationAlert.route, listOf(Color(0xFFD62828), Color(0xFF9B2335))),
    DashboardItem("📍", if (isKannada) "ಹತ್ತಿರದ ಕಾಡು" else "Nearby Grove", if (isKannada) "ಜಿಪಿಎಸ್ ಪತ್ತೆ" else "GPS Detection", Screen.NearbyGrove.route, listOf(ForestGreen700, ForestGreen900)),
    DashboardItem("🌱", if (isKannada) "ಪರಿಸರ ಶಿಕ್ಷಣ" else "Eco Education", if (isKannada) "ಕಲಿಯಿರಿ & ರಕ್ಷಿಸಿ" else "Learn & Protect", Screen.EcoEducation.route, listOf(EarthBrown500, EarthBrown700)),
    DashboardItem("🛡️", if (isKannada) "ರಕ್ಷಕ ಮೋಡ್" else "Guardian Mode", if (isKannada) "ಬ್ಯಾಡ್ಜ್‌ಗಳು" else "Earn Badges", Screen.GuardianMode.route, listOf(SacredGold, SacredGoldDark)),
    DashboardItem("🔍", if (isKannada) "ಜಾಗತಿಕ ಹುಡುಕಾಟ" else "Global Search", if (isKannada) "ಎಲ್ಲವನ್ನೂ ಹುಡುಕಿ" else "Find Anything", Screen.Search.route, listOf(ForestGreen600, ForestGreen800)),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    vm: SettingsViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val isKannada by vm.isKannada.collectAsStateWithLifecycle()
    val items = remember(isKannada) { getDashboardItems(isKannada) }

    val titleExplore = if (isKannada) "ಅನ್ವೇಷಿಸಿ" else "Explore"
    val subtitleExplore = if (isKannada) "ಪವಿತ್ರ ಕಾಡುಗಳ ವೈಶಿಷ್ಟ್ಯಗಳು" else "Sacred Grove Features"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Devara-Kaadu", style = MaterialTheme.typography.titleLarge,
                            color = SacredGold, fontWeight = FontWeight.Bold)
                        Text(if (isKannada) "ಪವಿತ್ರ ವನ ರಕ್ಷಕ" else "Sacred Grove Sentinel", style = MaterialTheme.typography.labelSmall,
                            color = ForestGreen200)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ForestGreen900),
                actions = {
                    IconButton(onClick = { onNavigate(Screen.Settings.route) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = ForestGreen200)
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(paddingValues)
        ) {
            // Hero banner
            HeroBanner()

            Spacer(Modifier.height(20.dp))

            // Quick stats row
            QuickStatsRow()

            Spacer(Modifier.height(20.dp))

            Text(
                titleExplore, style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                subtitleExplore, style = MaterialTheme.typography.bodyMedium,
                color = TextMuted, modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Dashboard grid (non-scrollable inside ScrollColumn)
            val rows = items.chunked(2)
            rows.forEach { rowItems ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { item ->
                        DashboardCard(item = item, onClick = { onNavigate(item.route) },
                            modifier = Modifier.weight(1f))
                    }
                    if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                }
            }

            Spacer(Modifier.height(24.dp))

            // Eco fact card
            EcoFactCard()

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun HeroBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .background(Brush.verticalGradient(listOf(ForestGreen900, ForestGreen700)))
            .padding(24.dp)
    ) {
        Column {
            Text("ನಮಸ್ಕಾರ 🙏", color = SacredGold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                "Karnataka's Sacred Groves\nNeed Your Protection",
                color = OnForest, style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold, lineHeight = 32.sp
            )
            Spacer(Modifier.height(8.dp))
            Text("2,000+ groves across the state", color = ForestGreen200,
                style = MaterialTheme.typography.bodySmall)
        }
        Text("🌿", fontSize = 80.sp, modifier = Modifier.align(Alignment.CenterEnd).alpha(0.3f))
    }
}

@Composable
private fun QuickStatsRow() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(
            Triple("🌳", "2,000+", "Sacred Groves"),
            Triple("🌿", "5,000+", "Species"),
            Triple("💧", "40%", "Water Recharge")
        ).forEach { (emoji, value, label) ->
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = ForestGreen100),
                shape = MaterialTheme.shapes.medium
            ) {
                Column(
                    Modifier.padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(emoji, fontSize = 20.sp)
                    Text(value, style = MaterialTheme.typography.titleMedium,
                        color = ForestGreen900, fontWeight = FontWeight.Bold)
                    Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted,
                        lineHeight = 14.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardCard(item: DashboardItem, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "cardScale"
    )

    Card(
        onClick = onClick,
        modifier = modifier
            .height(130.dp)
            .scale(scale),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(item.gradientColors))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
                Text(item.emoji, fontSize = 32.sp)
                Column {
                    Text(item.title, style = MaterialTheme.typography.titleSmall,
                        color = Color.White, fontWeight = FontWeight.Bold)
                    Text(item.subtitle, style = MaterialTheme.typography.labelSmall,
                        color = Color.White.copy(alpha = 0.75f))
                }
            }
        }
    }
}

@Composable
private fun EcoFactCard() {
    val facts = listOf(
        "Sacred groves harbour 10x more biodiversity per hectare than surrounding farmland.",
        "A healthy sacred grove can recharge groundwater at 3,000 litres per hectare per day.",
        "Over 80% of Karnataka's rare orchid species exist only in sacred groves.",
        "Sacred groves have protected forests for 1,500+ years before modern conservation existed.",
        "The traditional belief system protecting sacred groves is now studied by conservation scientists worldwide."
    )
    val fact = remember { facts.random() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = ForestGreen100),
        shape = MaterialTheme.shapes.large
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
            Text("💡", fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp, top = 2.dp))
            Column {
                Text("Eco Fact of the Day", style = MaterialTheme.typography.labelLarge,
                    color = ForestGreen800, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(fact, style = MaterialTheme.typography.bodyMedium, color = ForestGreen900,
                    lineHeight = 22.sp)
            }
        }
    }
}


