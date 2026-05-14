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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devara_kaadu.ui.theme.*

private data class EcoTopic(val emoji: String, val title: String, val content: String)

private val ecoTopics = listOf(
    EcoTopic("🌳", "What are Sacred Groves?",
        "Sacred Groves (Devara Kaadu in Kannada) are forest patches traditionally protected by communities due to religious and cultural beliefs. Found across South Asia, Karnataka alone has 2,000+ such groves ranging from 0.5 to 100+ hectares.\n\nThey are perhaps the world's oldest conservation model — predating modern national parks by millennia. Communities voluntarily abstained from any resource extraction from these forests, guided purely by faith and tradition."),
    EcoTopic("🦋", "Biodiversity Hotspots",
        "Sacred groves harbour extraordinary biodiversity. Studies show they contain 3-10x more species per hectare than surrounding managed forests. Many rare endemic plants and animals exist only within these protected patches.\n\nIn Karnataka, sacred groves protect over 80 orchid species, 200+ medicinal plants, and dozens of bird species found nowhere else in the region. They function as genetic reservoirs for the entire landscape."),
    EcoTopic("💧", "Water Recharge Systems",
        "Sacred groves play a critical role in groundwater recharge. Their dense, undisturbed root systems and leaf litter absorb 70-90% of rainfall, allowing it to slowly percolate into aquifers.\n\nResearch in Uttara Kannada shows that villages with intact sacred groves near them have functioning wells even in droughts, while villages that lost their groves experience water shortages during dry months.\n\nThey are nature's water storage systems — free, self-maintaining, and irreplaceable."),
    EcoTopic("🌡️", "Micro-Climate Regulation",
        "A sacred grove acts as a natural air conditioner for its surroundings. Dense canopies block direct solar radiation, and leaf transpiration cools the air. Studies show groves reduce ambient temperature by 3-5°C within a 2km radius.\n\nIn Kodagu, coffee planters neighboring sacred groves report better yields and reduced frost damage — directly attributable to the grove's micro-climate regulation. They also suppress wind speed, preventing crop damage."),
    EcoTopic("🌱", "Carbon Storage",
        "Sacred groves are significant carbon sinks. Unlike regularly harvested forests, grove trees are allowed to grow for centuries — building up enormous carbon stores in wood, roots, and soil.\n\nA study of Karnataka sacred groves found carbon density of 150-300 tonnes per hectare — comparable to primary tropical rainforests. Their combined area represents a climate asset worth millions in carbon credits, maintained at zero cost by traditional communities."),
    EcoTopic("📚", "Traditional Ecological Knowledge",
        "Sacred grove communities possess extraordinary ecological knowledge passed orally across generations. They know which plants signal rain (phenological indicators), which indicate soil health, and how to read forest health from bird calls.\n\nThis Traditional Ecological Knowledge (TEK) is now being studied by conservation scientists. The Kani tribe of Kerala, for example, led scientists to a powerful anti-fatigue compound from a forest plant they had known for generations.\n\nProtecting sacred groves protects this irreplaceable knowledge system.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoEducationScreen(onBack: () -> Unit) {
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eco Education", color = SacredGold, fontWeight = FontWeight.Bold) },
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
            // Hero
            Box(
                Modifier.fillMaxWidth().height(140.dp)
                    .background(Brush.verticalGradient(listOf(ForestGreen900, ForestGreen700)))
                    .padding(24.dp)
            ) {
                Text("🌱", fontSize = 48.sp, modifier = Modifier.align(Alignment.CenterEnd))
                Column(Modifier.align(Alignment.CenterStart)) {
                    Text("Eco Education", style = MaterialTheme.typography.headlineSmall,
                        color = SacredGold, fontWeight = FontWeight.Bold)
                    Text("Learn why Sacred Groves matter", color = ForestGreen200,
                        style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(Modifier.height(12.dp))

            ecoTopics.forEachIndexed { index, topic ->
                EcoTopicCard(
                    topic = topic,
                    isExpanded = expandedIndex == index,
                    onClick = { expandedIndex = if (expandedIndex == index) null else index }
                )
            }

            // Key stats card
            Spacer(Modifier.height(8.dp))
            Card(
                Modifier.fillMaxWidth().padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = ForestGreen900),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("Karnataka Sacred Grove Facts",
                        style = MaterialTheme.typography.titleMedium,
                        color = SacredGold, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    listOf(
                        "2,000+ sacred groves identified in Karnataka",
                        "80+ wild orchid species protected exclusively by groves",
                        "60% of Western Ghats medicinal plants found in groves",
                        "3x higher bird diversity in grove areas vs farmland",
                        "Traditional protection > 1,500 years in some districts",
                        "Groves recharge 30-40% of village well water"
                    ).forEach { fact ->
                        Row(Modifier.padding(vertical = 3.dp)) {
                            Text("✓ ", color = SacredGold, fontWeight = FontWeight.Bold)
                            Text(fact, style = MaterialTheme.typography.bodySmall,
                                color = ForestGreen100, lineHeight = 18.sp)
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun EcoTopicCard(topic: EcoTopic, isExpanded: Boolean, onClick: () -> Unit) {
    Card(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) ForestGreen100 else MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(topic.emoji, fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                Text(topic.title, style = MaterialTheme.typography.titleSmall,
                    color = ForestGreen900, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                Icon(
                    if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    null, tint = ForestGreen700
                )
            }
            if (isExpanded) {
                HorizontalDivider(Modifier.padding(horizontal = 16.dp), color = ForestGreen200)
                Text(topic.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = ForestGreen900, lineHeight = 24.sp,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp))
            }
        }
    }
}
