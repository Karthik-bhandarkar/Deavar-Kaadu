package com.example.devara_kaadu.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    vm: SettingsViewModel = viewModel()
) {
    val isDarkMode by vm.isDarkMode.collectAsStateWithLifecycle()
    val isKannada by vm.isKannada.collectAsStateWithLifecycle()
    val natureSounds by vm.natureSounds.collectAsStateWithLifecycle()
    val notifications by vm.notifications.collectAsStateWithLifecycle()

    // Dynamic localized strings based on state
    val screenTitle = if (isKannada) "ಸೆಟ್ಟಿಂಗ್‌ಗಳು (Settings)" else "Settings"
    val sectionAppearance = if (isKannada) "ಗೋಚರತೆ (Appearance)" else "Appearance"
    val sectionExperience = if (isKannada) "ಅನುಭವ (Experience)" else "Experience"
    val sectionInfo = if (isKannada) "ಮಾಹಿತಿ (Information)" else "Information"

    val titleDarkMode = if (isKannada) "ಡಾರ್ಕ್ ಮೋಡ್ (Dark Mode)" else "Dark Mode"
    val titleKannada = if (isKannada) "ಕನ್ನಡ ಭಾಷೆ (Kannada Language)" else "Kannada Language"
    val titleSounds = if (isKannada) "ಪ್ರಕೃತಿ ಧ್ವನಿಗಳು (Nature Sounds)" else "Nature Sounds"
    val titleNotifs = if (isKannada) "ಸಂರಕ್ಷಣೆ ಜ್ಞಾಪನೆಗಳು (Reminders)" else "Conservation Reminders"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle, color = SacredGold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = ForestGreen200)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ForestGreen900)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
            Spacer(Modifier.height(16.dp))

            // App info card
            Card(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = ForestGreen900),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🌿", fontSize = 40.sp)
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text("Devara-Kaadu", style = MaterialTheme.typography.titleLarge,
                            color = SacredGold, fontWeight = FontWeight.Bold)
                        Text("Sacred Grove Sentinel v1.0", color = ForestGreen200,
                            style = MaterialTheme.typography.bodySmall)
                        Text("Offline · Karnataka · Conservation", color = ForestGreen400,
                            style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            SettingsSection(sectionAppearance) {
                SettingsToggle(titleDarkMode, "Switch to forest night theme", Icons.Default.DarkMode,
                    isDarkMode) { vm.setDarkMode(it) }
                SettingsToggle(titleKannada, "ಕನ್ನಡ UI toggle bindings", Icons.Default.Translate,
                    isKannada) { vm.setKannada(it) }
            }

            SettingsSection(sectionExperience) {
                SettingsToggle(titleSounds, "Looping ambient forest hum", Icons.Default.VolumeUp,
                    natureSounds) { vm.setNatureSounds(it) }
                SettingsToggle(titleNotifs, "Daily grove protection notifications", Icons.Default.Notifications,
                    notifications) { vm.setNotifications(it) }
            }

            SettingsSection(sectionInfo) {
                SettingsItem("About App", "Learn about Devara-Kaadu project", Icons.Default.Info) {}
                SettingsItem("Privacy Policy", "How we store your data locally", Icons.Default.Security) {}
                SettingsItem("Data Storage", "All data stays on your device", Icons.Default.Storage) {}
                SettingsItem("Open Source Credits", "Libraries and resources used", Icons.Default.Code) {}
            }

            Spacer(Modifier.height(20.dp))

            // Conservation pledge
            Card(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = MaterialTheme.shapes.large
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("🕉️ Guardian's Pledge", style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "\"I pledge to protect Karnataka's Sacred Groves, honor the traditions that preserved them, and ensure these forests stand for the generations who will inherit them.\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f), lineHeight = 20.sp
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Text(title, style = MaterialTheme.typography.labelLarge,
        color = TextMuted, fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.large
    ) {
        Column { content() }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun SettingsToggle(
    title: String, subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    checked: Boolean, onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = ForestGreen700, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = ForestGreen900)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = ForestGreen700, checkedTrackColor = ForestGreen200))
    }
}

@Composable
private fun SettingsItem(
    title: String, subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = ForestGreen700, modifier = Modifier.size(22.dp))
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, color = ForestGreen900)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = TextMuted)
        }
        Icon(Icons.Default.ChevronRight, null, tint = TextMuted, modifier = Modifier.size(18.dp))
    }
}
