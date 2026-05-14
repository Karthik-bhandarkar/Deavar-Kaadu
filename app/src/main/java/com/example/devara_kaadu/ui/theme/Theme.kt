package com.example.devara_kaadu.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ── Light Color Scheme (Parchment + Forest Green) ──────────────────────────
private val LightColorScheme = lightColorScheme(
    primary = ForestGreen800,
    onPrimary = OnForest,
    primaryContainer = ForestGreen100,
    onPrimaryContainer = ForestGreen900,

    secondary = EarthBrown700,
    onSecondary = OnForest,
    secondaryContainer = EarthBrown100,
    onSecondaryContainer = EarthBrown900,

    tertiary = SacredGold,
    onTertiary = EarthBrown900,
    tertiaryContainer = SacredGoldLight,
    onTertiaryContainer = EarthBrown900,

    background = Parchment,
    onBackground = OnParchment,

    surface = Parchment,
    onSurface = OnParchment,
    surfaceVariant = ParchmentDark,
    onSurfaceVariant = TextMuted,

    outline = ForestGreen700,
    outlineVariant = ForestGreen200,

    error = AlertRed,
    onError = OnForest,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002)
)

// ── Dark Color Scheme (Deep Forest Night) ──────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary = ForestGreen400,
    onPrimary = ForestGreen900,
    primaryContainer = ForestGreen800,
    onPrimaryContainer = ForestGreen100,

    secondary = EarthBrown300,
    onSecondary = EarthBrown900,
    secondaryContainer = EarthBrown700,
    onSecondaryContainer = EarthBrown100,

    tertiary = SacredGoldLight,
    onTertiary = EarthBrown900,
    tertiaryContainer = SacredGoldDark,
    onTertiaryContainer = SacredGoldLight,

    background = DarkSurface,
    onBackground = ForestGreen100,

    surface = DarkSurface2,
    onSurface = ForestGreen100,
    surfaceVariant = DarkSurface3,
    onSurfaceVariant = ForestGreen200,

    outline = DarkOutline,
    outlineVariant = ForestGreen800,

    error = AlertRed,
    onError = OnForest,
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun DevaraKaaduTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DevaraKaaduTypography,
        shapes = DevaraKaaduShapes,
        content = content
    )
}
