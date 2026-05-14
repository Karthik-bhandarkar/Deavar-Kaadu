package com.example.devara_kaadu.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devara_kaadu.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    // Animate alpha and scale in
    val alpha = remember { Animatable(0f) }
    val scale = remember { Animatable(0.7f) }
    val subtitleAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Logo appears
        launch { alpha.animateTo(1f, animationSpec = tween(900, easing = EaseOutCubic)) }
        launch { scale.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)) }
        delay(700)
        // Subtitle fades in
        subtitleAlpha.animateTo(1f, animationSpec = tween(600))
        delay(1200)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(ForestGreen900, ForestGreen800, ForestGreen700)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative circle rings behind logo
        repeat(3) { i ->
            val ringAnim = rememberInfiniteTransition(label = "ring$i")
            val ringScale by ringAnim.animateFloat(
                initialValue = 0.85f + i * 0.1f,
                targetValue = 1.15f + i * 0.1f,
                animationSpec = infiniteRepeatable(
                    tween(2000 + i * 400, easing = EaseInOutSine),
                    RepeatMode.Reverse
                ),
                label = "ringScale$i"
            )
            Box(
                modifier = Modifier
                    .size((180 + i * 60).dp)
                    .scale(ringScale)
                    .alpha(0.07f - i * 0.015f)
                    .background(ForestGreen400, shape = androidx.compose.foundation.shape.CircleShape)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App icon / logo
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .scale(scale.value)
                    .alpha(alpha.value)
                    .background(
                        Brush.radialGradient(listOf(SacredGold, EarthBrown700)),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("🌿", fontSize = 56.sp)
            }

            Spacer(Modifier.height(28.dp))

            Text(
                text = "Devara-Kaadu",
                style = MaterialTheme.typography.displaySmall,
                color = SacredGold,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.alpha(alpha.value),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Sacred Grove Sentinel",
                style = MaterialTheme.typography.titleMedium,
                color = ForestGreen200,
                letterSpacing = 3.sp,
                modifier = Modifier.alpha(subtitleAlpha.value),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "ದೇವರಕಾಡು",
                style = MaterialTheme.typography.titleSmall,
                color = ForestGreen400,
                modifier = Modifier.alpha(subtitleAlpha.value),
                textAlign = TextAlign.Center
            )
        }

        // Bottom tagline
        Text(
            text = "Preserving Karnataka's Sacred Forests",
            style = MaterialTheme.typography.bodySmall,
            color = ForestGreen200.copy(alpha = 0.7f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .alpha(subtitleAlpha.value)
        )
    }
}
