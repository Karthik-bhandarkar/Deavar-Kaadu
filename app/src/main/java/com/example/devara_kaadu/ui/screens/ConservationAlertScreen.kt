package com.example.devara_kaadu.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.data.model.Alert
import com.example.devara_kaadu.data.model.AlertType
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.viewmodel.AlertViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConservationAlertScreen(
    onBack: () -> Unit,
    vm: AlertViewModel = viewModel()
) {
    val alerts by vm.alerts.collectAsStateWithLifecycle()
    val isSubmitting by vm.isSubmitting.collectAsStateWithLifecycle()
    val submitSuccess by vm.submitSuccess.collectAsStateWithLifecycle()

    var selectedType by remember { mutableStateOf(AlertType.TREE_CUTTING) }
    var description by remember { mutableStateOf("") }
    var showHistory by remember { mutableStateOf(false) }

    LaunchedEffect(submitSuccess) {
        if (submitSuccess) {
            description = ""
            vm.resetSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conservation Alert", color = SacredGold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = ForestGreen200)
                    }
                },
                actions = {
                    TextButton(onClick = { showHistory = !showHistory }) {
                        Text(if (showHistory) "Report" else "History", color = SacredGold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ForestGreen900)
            )
        },
        containerColor = Parchment
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())) {
            if (!showHistory) {
                // Report Form
                Spacer(Modifier.height(16.dp))

                Card(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3F3)),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("⚠️", fontSize = 24.sp)
                            Spacer(Modifier.width(8.dp))
                            Text("Report a Conservation Issue",
                                style = MaterialTheme.typography.titleMedium,
                                color = AlertRed, fontWeight = FontWeight.Bold)
                        }
                        Text("Help protect sacred groves by reporting threats.",
                            style = MaterialTheme.typography.bodySmall, color = TextMuted)
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Alert type selector
                Text("Issue Type", style = MaterialTheme.typography.titleSmall,
                    color = ForestGreen900, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(AlertType.entries) { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { selectedType = type },
                            label = { Text("${type.icon} ${type.displayName}", fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(type.color),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Describe the issue") },
                    placeholder = { Text("Provide details about the threat you observed...") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(140.dp),
                    maxLines = 6,
                    shape = MaterialTheme.shapes.large,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AlertRed, unfocusedBorderColor = ForestGreen200
                    )
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        vm.submitAlert(
                            type = selectedType,
                            description = description.ifBlank { "Issue reported via Devara-Kaadu app" }
                        )
                    },
                    enabled = !isSubmitting,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AlertRed)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Icon(Icons.Default.Send, null, Modifier.padding(end = 6.dp))
                        Text("Submit Alert")
                    }
                }

                if (submitSuccess) {
                    Spacer(Modifier.height(12.dp))
                    Card(
                        Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                    ) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, null, tint = AlertGreen)
                            Spacer(Modifier.width(8.dp))
                            Text("Alert submitted successfully! Saved locally.",
                                color = AlertGreen, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            } else {
                // History list
                Spacer(Modifier.height(12.dp))
                if (alerts.isEmpty()) {
                    Box(Modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) {
                        Text("No alerts reported yet.", color = TextMuted)
                    }
                } else {
                    alerts.forEach { alert ->
                        AlertHistoryCard(alert = alert, onDelete = { vm.deleteAlert(alert) })
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun AlertHistoryCard(alert: Alert, onDelete: () -> Unit) {
    val type = AlertType.entries.find { it.name == alert.type } ?: AlertType.OTHER
    val dateStr = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(alert.timestamp))

    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.large
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
            Text(type.icon, fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp, top = 2.dp))
            Column(Modifier.weight(1f)) {
                Text(type.displayName, style = MaterialTheme.typography.titleSmall,
                    color = Color(type.color), fontWeight = FontWeight.Bold)
                Text(alert.description, style = MaterialTheme.typography.bodySmall,
                    color = ForestGreen900, lineHeight = 18.sp)
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(onClick = {}, label = { Text(alert.status, fontSize = 11.sp) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = ForestGreen100, labelColor = ForestGreen800))
                    Text(dateStr, style = MaterialTheme.typography.labelSmall, color = TextMuted)
                }
            }
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Delete, null, tint = TextMuted, modifier = Modifier.size(18.dp))
            }
        }
    }
}
