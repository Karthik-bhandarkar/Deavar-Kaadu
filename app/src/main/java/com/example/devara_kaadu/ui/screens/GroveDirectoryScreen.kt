package com.example.devara_kaadu.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devara_kaadu.data.model.Grove
import com.example.devara_kaadu.data.model.GroveType
import com.example.devara_kaadu.ui.theme.*
import com.example.devara_kaadu.viewmodel.GroveViewModel
import com.example.devara_kaadu.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroveDirectoryScreen(
    onGroveClick: (Int) -> Unit,
    onBack: () -> Unit,
    vm: GroveViewModel = viewModel(),
    settingsVm: SettingsViewModel = viewModel()
) {
    val groves by vm.groves.collectAsStateWithLifecycle()
    val searchQuery by vm.searchQuery.collectAsStateWithLifecycle()
    val selectedType by vm.selectedType.collectAsStateWithLifecycle()
    val isGridView by vm.isGridView.collectAsStateWithLifecycle()
    val isKannada by settingsVm.isKannada.collectAsStateWithLifecycle()

    val screenTitle = if (isKannada) "ದೇವರಕಾಡು ಪಟ್ಟಿ" else "Grove Directory"
    val searchPlaceholder = if (isKannada) "ಹುಡುಕಿ (Search groves, districts)..." else "Search groves, districts..."

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle, color = SacredGold, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = ForestGreen200)
                    }
                },
                actions = {
                    IconButton(onClick = { vm.toggleViewMode() }) {
                        Icon(
                            if (isGridView) Icons.Default.List else Icons.Default.GridView,
                            contentDescription = "Toggle view", tint = ForestGreen200
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ForestGreen900)
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { vm.setSearchQuery(it) },
                placeholder = { Text(searchPlaceholder) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = ForestGreen700) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { vm.setSearchQuery("") }) {
                            Icon(Icons.Default.Clear, null)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ForestGreen700,
                    unfocusedBorderColor = ForestGreen200
                )
            )

            // Type filter chips
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(GroveType.entries) { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { vm.setSelectedType(type) },
                        label = { Text(type.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ForestGreen700,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            // Results count
            Text(
                "${groves.size} groves found",
                style = MaterialTheme.typography.labelMedium,
                color = TextMuted,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // Grid or List
            if (isGridView) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(groves, key = { it.id }) { grove ->
                        GroveGridCard(grove = grove, onClick = { onGroveClick(grove.id) })
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(groves, key = { it.id }) { grove ->
                        GroveListCard(grove = grove, onClick = { onGroveClick(grove.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun GroveGridCard(grove: Grove, onClick: () -> Unit) {
    val gradients = listOf(
        listOf(ForestGreen800, ForestGreen600),
        listOf(EarthBrown700, EarthBrown500),
        listOf(ForestGreen700, SacredGoldDark),
        listOf(EarthBrown900, EarthBrown700)
    )
    val gradient = gradients[grove.id % gradients.size]

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(160.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(gradient))
                .padding(14.dp)
        ) {
            if (grove.isVisited) {
                Icon(Icons.Default.CheckCircle, null, tint = SacredGold,
                    modifier = Modifier.align(Alignment.TopEnd).size(20.dp))
            }
            Column(Modifier.align(Alignment.BottomStart)) {
                Text(grove.type, style = MaterialTheme.typography.labelSmall,
                    color = SacredGold)
                Text(grove.name, style = MaterialTheme.typography.titleSmall,
                    color = Color.White, fontWeight = FontWeight.Bold,
                    maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text("${grove.village} · ${grove.district}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.75f))
            }
        }
    }
}

@Composable
private fun GroveListCard(grove: Grove, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier
                    .size(56.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Brush.linearGradient(listOf(ForestGreen800, ForestGreen600))),
                contentAlignment = Alignment.Center
            ) {
                Text("🌳", fontSize = 28.sp)
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(grove.name, style = MaterialTheme.typography.titleSmall,
                        color = ForestGreen900, fontWeight = FontWeight.Bold)
                    if (grove.isVisited) {
                        Spacer(Modifier.width(4.dp))
                        Icon(Icons.Default.CheckCircle, null,
                            tint = AlertGreen, modifier = Modifier.size(16.dp))
                    }
                }
                Text("${grove.village}, ${grove.district}",
                    style = MaterialTheme.typography.bodySmall, color = TextMuted)
                Spacer(Modifier.height(4.dp))
                AssistChip(
                    onClick = {}, label = { Text(grove.type, fontSize = 11.sp) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = ForestGreen100, labelColor = ForestGreen800
                    ),
                    modifier = Modifier.height(24.dp)
                )
            }
            Icon(Icons.Default.ChevronRight, null, tint = TextMuted)
        }
    }
}
