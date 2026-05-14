package com.example.devara_kaadu.ui.screens

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.data.model.Badge
import com.example.devara_kaadu.data.model.UserProgress
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.viewmodel.GuardianViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuardianModeScreen(onBack: () -> Unit, vm: GuardianViewModel = viewModel()) {
    val progress by vm.progress.collectAsStateWithLifecycle()
    val earnedBadges by vm.earnedBadges.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Guardian Mode", color = SacredGold, fontWeight = FontWeight.Bold) },
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
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
            // Guardian hero
            Box(
                Modifier.fillMaxWidth().height(160.dp)
                    .background(Brush.verticalGradient(listOf(SacredGoldDark, EarthBrown700))),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🛡️", fontSize = 48.sp)
                    Text("Sacred Grove Guardian", style = MaterialTheme.typography.headlineSmall,
                        color = SacredGold, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text("Protect. Learn. Preserve.", color = EarthBrown100,
                        style = MaterialTheme.typography.bodyMedium)
                }
            }

            progress?.let { p ->
                // Stats cards
                Spacer(Modifier.height(16.dp))
                Text("Your Impact", style = MaterialTheme.typography.titleMedium,
                    color = ForestGreen900, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(8.dp))

                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatCard("🌳", "${p.grovesVisited}", "Groves Visited", Modifier.weight(1f))
                    StatCard("🔬", "${p.speciesScanned}", "Species Scanned", Modifier.weight(1f))
                    StatCard("⚠️", "${p.alertsReported}", "Alerts Filed", Modifier.weight(1f))
                }

                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatCard("📜", "${p.legendsRead}", "Legends Read", Modifier.weight(1f))
                    StatCard("⭐", "${p.totalPoints}", "Total Points", Modifier.weight(1f))
                    Spacer(Modifier.weight(1f))
                }

                // Points progress
                Spacer(Modifier.height(16.dp))
                ProgressCard(progress = p)
            }

            // Badges section
            Spacer(Modifier.height(20.dp))
            Text("Badges", style = MaterialTheme.typography.titleMedium,
                color = ForestGreen900, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp))
            Text("${earnedBadges.size} of ${vm.allBadges.size} earned",
                style = MaterialTheme.typography.bodySmall, color = TextMuted,
                modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(Modifier.height(12.dp))

            vm.allBadges.forEach { badge ->
                val isEarned = badge in earnedBadges
                BadgeCard(badge = badge, isEarned = isEarned)
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun StatCard(emoji: String, value: String, label: String, modifier: Modifier = Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = ForestGreen100),
        shape = MaterialTheme.shapes.medium) {
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 20.sp)
            Text(value, style = MaterialTheme.typography.titleLarge,
                color = ForestGreen900, fontWeight = FontWeight.Bold)
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextMuted,
                textAlign = TextAlign.Center, lineHeight = 14.sp)
        }
    }
}

@Composable
private fun ProgressCard(progress: UserProgress) {
    val nextMilestone = when {
        progress.totalPoints < 50 -> 50
        progress.totalPoints < 150 -> 150
        progress.totalPoints < 300 -> 300
        else -> 1000
    }
    val progressFraction = (progress.totalPoints.toFloat() / nextMilestone).coerceIn(0f, 1f)

    Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = SacredGoldLight.copy(alpha = 0.3f)),
        shape = MaterialTheme.shapes.large) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Points Progress", style = MaterialTheme.typography.titleSmall,
                    color = EarthBrown900, fontWeight = FontWeight.Bold)
                Text("${progress.totalPoints} / $nextMilestone pts",
                    style = MaterialTheme.typography.labelMedium, color = SacredGoldDark)
            }
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progressFraction },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(MaterialTheme.shapes.small),
                color = SacredGold, trackColor = ParchmentDeep
            )
            Spacer(Modifier.height(4.dp))
            Text("Earn points by visiting groves, scanning species, and filing alerts.",
                style = MaterialTheme.typography.labelSmall, color = TextMuted)
        }
    }
}

@Composable
private fun BadgeCard(badge: Badge, isEarned: Boolean) {
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isEarned) SacredGoldLight.copy(alpha = 0.4f) else Color.White.copy(alpha = 0.5f)
        ),
        shape = MaterialTheme.shapes.large,
        border = if (isEarned) BorderStroke(1.5.dp, SacredGold) else null
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(52.dp).clip(CircleShape)
                    .background(if (isEarned) SacredGold else ParchmentDeep),
                contentAlignment = Alignment.Center
            ) {
                Text(badge.icon, fontSize = 24.sp)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(badge.title, style = MaterialTheme.typography.titleSmall,
                    color = if (isEarned) EarthBrown900 else TextMuted,
                    fontWeight = FontWeight.Bold)
                Text(badge.description, style = MaterialTheme.typography.bodySmall,
                    color = if (isEarned) EarthBrown700 else TextMuted)
                Text("${badge.requiredPoints} points", style = MaterialTheme.typography.labelSmall,
                    color = if (isEarned) SacredGoldDark else TextMuted)
            }
            if (isEarned) {
                Icon(Icons.Default.EmojiEvents, null, tint = SacredGold, modifier = Modifier.size(28.dp))
            } else {
                Icon(Icons.Default.Lock, null, tint = TextMuted.copy(alpha = 0.5f), modifier = Modifier.size(24.dp))
            }
        }
    }
}
