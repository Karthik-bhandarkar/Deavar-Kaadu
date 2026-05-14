package com.example.devara_kaadu.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devara_kaadu.ui.theme.*
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val emoji: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val gradientStart: androidx.compose.ui.graphics.Color,
    val gradientEnd: androidx.compose.ui.graphics.Color
)

private val pages = listOf(
    OnboardingPage(
        "🌳", "Protect Sacred Groves",
        "ದೇವರಕಾಡು ಉಳಿಸಿ",
        "Karnataka's Sacred Groves (Devara Kaadu, Kaavu, Bana) are nature's temples — protecting ancient biodiversity, clean water, and the breath of our land. Over 2,000 sacred groves exist, each a living library.",
        ForestGreen900, ForestGreen700
    ),
    OnboardingPage(
        "🦋", "Learn Biodiversity",
        "ಜೀವ ವೈವಿಧ್ಯ ಅರಿಯಿರಿ",
        "Every grove is a universe. Explore endemic orchids, ancient fig trees, rare medicinal herbs, and sacred birds. Discover how traditional knowledge and modern science together protect these irreplaceable ecosystems.",
        EarthBrown900, EarthBrown700
    ),
    OnboardingPage(
        "🛡️", "Become Guardian of Nature",
        "ಪ್ರಕೃತಿಯ ರಕ್ಷಕ ಆಗಿರಿ",
        "Join thousands of Sacred Grove Sentinels. Report threats, document species, visit sacred sites, earn Guardian badges. Your actions today ensure these groves stand for the next thousand years.",
        ForestGreen800, SacredGoldDark
    )
)

@Composable
fun OnboardingScreen(onFinished: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { pageIndex ->
            OnboardingPage(page = pages[pageIndex])
        }

        // Page indicators
        Row(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(pages.size) { i ->
                val selected = pagerState.currentPage == i
                val width by animateDpAsState(if (selected) 28.dp else 8.dp, label = "dotWidth")
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(width)
                        .background(
                            if (selected) SacredGold else ForestGreen200.copy(alpha = 0.5f),
                            CircleShape
                        )
                )
            }
        }

        // Navigation buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (pagerState.currentPage < pages.lastIndex) {
                TextButton(onClick = onFinished) {
                    Text("Skip", color = ForestGreen200.copy(alpha = 0.7f))
                }
            } else {
                Spacer(Modifier.weight(1f))
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < pages.lastIndex) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        onFinished()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = SacredGold)
            ) {
                Text(
                    if (pagerState.currentPage < pages.lastIndex) "Next →" else "Begin Journey 🌿",
                    color = EarthBrown900
                )
            }
        }
    }
}

@Composable
private fun OnboardingPage(page: OnboardingPage) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(page) { visible = true }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(page.gradientStart, page.gradientEnd))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(initialOffsetY = { -60 }) + fadeIn(tween(600))
            ) {
                Text(page.emoji, fontSize = 80.sp)
            }

            Spacer(Modifier.height(32.dp))

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(initialOffsetY = { 40 }) + fadeIn(tween(700, 200))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        page.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = SacredGold,
                        textAlign = TextAlign.Center,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        page.subtitle,
                        style = MaterialTheme.typography.titleSmall,
                        color = ForestGreen200,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800, 400))
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.12f)
                    ),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        page.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = ForestGreen100,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(20.dp),
                        lineHeight = 26.sp
                    )
                }
            }
        }
    }
}
