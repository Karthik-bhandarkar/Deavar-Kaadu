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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devara_kaadu.ui.theme.*

private data class Legend(val deity: String, val grove: String, val district: String, val story: String, val traditions: String)

private val legends = listOf(
    Legend("Igguthappa", "Kodagu Devara Kaadu", "Kodagu",
        """Long ago, before maps were drawn and roads were paved through the hills of Kodagu, the great deity Igguthappa descended from the heavens in the form of a glowing peacock. He rested upon the ancient Peepal tree at the heart of what is now called Devara Kaadu — the Sacred Forest.

The Kodava people say that where Igguthappa walked, flowers bloomed in the red laterite soil and streams sprung from the earth. He told the first Kodava chieftain: 'Guard this forest as you guard your own children, and the rains shall never forsake these hills.'

For four centuries, no Kodava family has taken a single branch from this grove. When the British tried to survey and log it in the 1890s, the colonial officers reportedly fell ill with mysterious fevers and retreated. The grove remains untouched to this day.

Every year during Kail Podh, the harvest festival, families bring the first cut of their paddy to this forest, laying it at the roots of the ancient Peepal tree, offering back to the land what the land gave them.

The grove today harbors over 200 plant species and 85 bird species — a scientific testament to what faith protected.""",
        "Annual Kail Podh offering, community patrol, no tools or footwear permitted inside the grove boundary."
    ),
    Legend("Marikamba", "Sirsi Bana", "Uttara Kannada",
        """Marikamba, the fierce and loving mother goddess, is said to have once walked through the forests of Uttara Kannada disguised as an old woman. She came to test the faith of the forest dwellers.

At the edge of what is now the Sirsi Bana, a greedy merchant who had already cleared acres of forest for profit saw her and attempted to cut down an ancient fig tree for timber. The old woman raised her hand and the merchant was transformed into a granite stone — a warning carved forever into the landscape.

Word spread through all the villages: the forest belongs to Marikamba. No axe may be raised here.

Every two years, over 500,000 devotees travel to Sirsi for the Marikamba Jatra. During the festival, an oath of forest protection is collectively renewed by all households. Children are brought to touch the bark of the sacred trees and are taught their names — botanical and Kannada both.

Scientists who have studied the Sirsi Bana have found 8 IUCN Red Listed plant species within its borders — species preserved by faith alone for three centuries.""",
        "Biennial Jatra festival, sacred thread tied to boundary trees, no non-vegetarian food within 1km."
    ),
    Legend("Vasuki (Naga Devata)", "Nagabana of Sullia", "Dakshina Kannada",
        """When the great drought came to Dakshina Kannada three hundred years ago, the wells dried and the crops turned to dust. The village council sat beneath the stars and prayed for guidance.

On the seventh night, a vision appeared to the village elder — Vasuki, the cosmic serpent who encircles Lord Shiva's neck, rose from the earth and spoke:

'I shall bring the rains. But you must protect this patch of forest for all generations. Let no human hand disturb it. Let the serpents live in peace, for they are my children and the guardians of your water.'

The rains returned on the eighth morning. And the Nagabana was born — a sacred covenant between humans and serpents.

Since that day, king cobras and Indian pythons have nested undisturbed in this forest. Villagers pour milk at the sacred anthills during Naga Panchami. Children are taught from birth: the serpent is not to be feared — it is to be revered.

Ecologists have confirmed that the Nagabana's king cobra population controls the rodent population across 50 surrounding farms, protecting crops without a drop of pesticide.""",
        "Naga Panchami milk offering, silent entry required, clay serpent idols at boundary."
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MythLegendScreen(onBack: () -> Unit) {
    var selectedLegend by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Myth & Legend", color = SacredGold, fontWeight = FontWeight.Bold) },
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
            // Legend selector tabs
            ScrollableTabRow(
                selectedTabIndex = selectedLegend,
                containerColor = EarthBrown100,
                contentColor = EarthBrown900,
                edgePadding = 16.dp
            ) {
                legends.forEachIndexed { index, legend ->
                    Tab(
                        selected = selectedLegend == index,
                        onClick = { selectedLegend = index },
                        text = { Text(legend.deity, style = MaterialTheme.typography.labelMedium) }
                    )
                }
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val legend = legends[selectedLegend]

                // Hero
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(Brush.verticalGradient(listOf(EarthBrown900, EarthBrown700)))
                        .padding(24.dp)
                ) {
                    Text("📜", fontSize = 56.sp, modifier = Modifier.align(Alignment.CenterEnd))
                    Column(Modifier.align(Alignment.BottomStart)) {
                        Text(legend.deity, style = MaterialTheme.typography.headlineSmall,
                            color = SacredGold, fontWeight = FontWeight.Bold)
                        Text(legend.grove, style = MaterialTheme.typography.bodyMedium, color = EarthBrown100)
                        Text(legend.district, style = MaterialTheme.typography.bodySmall, color = EarthBrown300)
                    }
                }

                // Story text — oral history style
                Card(
                    Modifier.fillMaxWidth().padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = ParchmentDark),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("📖", fontSize = 20.sp)
                            Spacer(Modifier.width(8.dp))
                            Text("Sacred Legend", style = MaterialTheme.typography.titleMedium,
                                color = EarthBrown900, fontWeight = FontWeight.Bold)
                        }
                        Text("As told through generations", style = MaterialTheme.typography.labelSmall, color = TextMuted)
                        HorizontalDivider(Modifier.padding(vertical = 12.dp), color = EarthBrown300)
                        Text(
                            legend.story,
                            style = MaterialTheme.typography.bodyMedium,
                            color = EarthBrown900,
                            lineHeight = 26.sp,
                            textAlign = TextAlign.Justify
                        )
                    }
                }

                // Traditions card
                Card(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = EarthBrown100),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🪔", fontSize = 20.sp)
                            Spacer(Modifier.width(8.dp))
                            Text("Living Traditions", style = MaterialTheme.typography.titleSmall,
                                color = EarthBrown900, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(legend.traditions, style = MaterialTheme.typography.bodyMedium,
                            color = EarthBrown900, lineHeight = 22.sp)
                    }
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
