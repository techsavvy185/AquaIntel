package com.aquaintel.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Saffron,
    onPrimary = White,
    primaryContainer = SaffronLight,
    onPrimaryContainer = Gray900,
    secondary = AquaBlue,
    onSecondary = White,
    secondaryContainer = AquaBlueDark,
    onSecondaryContainer = White,
    tertiary = ForestGreen,
    onTertiary = White,
    error = AlertRed,
    onError = White,
    background = Gray50,
    onBackground = Gray900,
    surface = White,
    onSurface = Gray900,
    surfaceVariant = Gray100,
    onSurfaceVariant = Gray700
)

private val DarkColorScheme = darkColorScheme(
    primary = SaffronLight,
    onPrimary = Black,
    primaryContainer = Saffron,
    onPrimaryContainer = White,
    secondary = AquaBlue,
    onSecondary = White,
    secondaryContainer = AquaBlueDark,
    onSecondaryContainer = White,
    tertiary = GreenLight,
    onTertiary = Black,
    error = AlertRed,
    onError = White,
    background = DarkBackground,
    onBackground = Gray100,
    surface = DarkSurface,
    onSurface = Gray100,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Gray300
)

@Composable
fun AquaIntelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val appColors = if (darkTheme) DarkAppColors else LightAppColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
