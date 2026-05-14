package com.example.devara_kaadu.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onGroveClick: (Int) -> Unit,
    vm: SearchViewModel = viewModel()
) {
    val query by vm.query.collectAsStateWithLifecycle()
    val groveResults by vm.groveResults.collectAsStateWithLifecycle()
    val speciesResults by vm.speciesResults.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search", color = SacredGold, fontWeight = FontWeight.Bold) },
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
        Column(Modifier.fillMaxSize().padding(padding)) {
            OutlinedTextField(
                value = query,
                onValueChange = { vm.setQuery(it) },
                placeholder = { Text("Search groves, species, districts...") },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = ForestGreen700) },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { vm.clearQuery() }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ForestGreen700,
                    unfocusedBorderColor = ForestGreen200
                )
            )

            if (query.isBlank()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🔍", style = MaterialTheme.typography.displaySmall)
                        Spacer(Modifier.height(12.dp))
                        Text("Search across all groves and species",
                            style = MaterialTheme.typography.bodyMedium, color = TextMuted)
                    }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                    if (groveResults.isNotEmpty()) {
                        item {
                            Text("🌳 Sacred Groves (${groveResults.size})",
                                style = MaterialTheme.typography.titleSmall,
                                color = ForestGreen900, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp))
                        }
                        items(groveResults, key = { "g${it.id}" }) { grove ->
                            Card(
                                onClick = { onGroveClick(grove.id) },
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("🌳", style = MaterialTheme.typography.titleLarge)
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text(grove.name, style = MaterialTheme.typography.titleSmall,
                                            color = ForestGreen900, fontWeight = FontWeight.Bold)
                                        Text("${grove.village}, ${grove.district} · ${grove.type}",
                                            style = MaterialTheme.typography.bodySmall, color = TextMuted)
                                    }
                                    Spacer(Modifier.weight(1f))
                                    Icon(Icons.Default.ChevronRight, null, tint = TextMuted)
                                }
                            }
                        }
                    }

                    if (speciesResults.isNotEmpty()) {
                        item {
                            Text("🌿 Species (${speciesResults.size})",
                                style = MaterialTheme.typography.titleSmall,
                                color = ForestGreen900, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 8.dp))
                        }
                        items(speciesResults, key = { "s${it.id}" }) { species ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = ForestGreen100),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text("🌿", style = MaterialTheme.typography.titleLarge)
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text(species.name, style = MaterialTheme.typography.titleSmall,
                                            color = ForestGreen900, fontWeight = FontWeight.Bold)
                                        Text(species.scientificName,
                                            style = MaterialTheme.typography.bodySmall, color = TextMuted)
                                        Text(species.kannadaName,
                                            style = MaterialTheme.typography.labelSmall, color = ForestGreen700)
                                    }
                                }
                            }
                        }
                    }

                    if (groveResults.isEmpty() && speciesResults.isEmpty()) {
                        item {
                            Box(Modifier.fillMaxWidth().padding(48.dp), contentAlignment = Alignment.Center) {
                                Text("No results for \"$query\"", color = TextMuted)
                            }
                        }
                    }
                }
            }
        }
    }
}
