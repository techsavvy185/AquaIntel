package com.aquaintel.app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val primary: Color,
    val primaryVariant: Color,
    val secondary: Color,
    val secondaryVariant: Color,
    val background: Color,
    val surface: Color,
    val error: Color,
    val onPrimary: Color,
    val onSecondary: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onError: Color,
    val success: Color,
    val warning: Color,
    val editText: Color,
    val cardBackground: Color,
    val divider: Color
)

val LightAppColors = AppColors(
    primary = Saffron,
    primaryVariant = SaffronDark,
    secondary = AquaBlue,
    secondaryVariant = AquaBlueDark,
    background = Gray50,
    surface = White,
    error = AlertRed,
    onPrimary = White,
    onSecondary = White,
    onBackground = Gray900,
    onSurface = Gray900,
    onError = White,
    success = ForestGreen,
    warning = WarningOrange,
    editText = Gray700,
    cardBackground = White,
    divider = Gray300
)

val DarkAppColors = AppColors(
    primary = SaffronLight,
    primaryVariant = Saffron,
    secondary = AquaBlue,
    secondaryVariant = AquaBlueDark,
    background = DarkBackground,
    surface = DarkSurface,
    error = AlertRed,
    onPrimary = Black,
    onSecondary = White,
    onBackground = Gray100,
    onSurface = Gray100,
    onError = White,
    success = GreenLight,
    warning = WarningOrange,
    editText = Gray300,
    cardBackground = DarkSurfaceVariant,
    divider = Gray700
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }
