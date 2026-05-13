package com.shejan.easyread.ui.theme

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Animate a single color between light/dark targets
@Composable
private fun animThemeColor(dark: Boolean, darkColor: Color, lightColor: Color): Color {
    val color by animateColorAsState(
        targetValue   = if (dark) darkColor else lightColor,
        animationSpec = tween(durationMillis = 150),
        label         = "theme_color"
    )
    return color
}

@Composable
fun EasyReadTheme(
    themePreference: ThemePreference = ThemePreference.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themePreference) {
        ThemePreference.LIGHT  -> false
        ThemePreference.DARK   -> true
        ThemePreference.SYSTEM -> isSystemInDarkTheme()
    }

    // ── Animate every color token so theme changes are smooth, not flashy ──
    val animatedColorScheme = lightColorScheme(
        primary            = animThemeColor(darkTheme, PrimaryDark,       Primary),
        onPrimary          = animThemeColor(darkTheme, OnSurfaceDark,      Surface),
        primaryContainer   = animThemeColor(darkTheme, PrimaryLightDark,   PrimaryLight),
        onPrimaryContainer = animThemeColor(darkTheme, OnSurfaceDark,      OnSurface),
        background         = animThemeColor(darkTheme, BackgroundDark,     Background),
        onBackground       = animThemeColor(darkTheme, OnSurfaceDark,      OnSurface),
        surface            = animThemeColor(darkTheme, SurfaceDark,        Surface),
        onSurface          = animThemeColor(darkTheme, OnSurfaceDark,      OnSurface),
        surfaceVariant     = animThemeColor(darkTheme, SurfaceVarDark,     SurfaceVar),
        onSurfaceVariant   = animThemeColor(darkTheme, OnSurfaceVarDark,   OnSurfaceVar),
        outline            = animThemeColor(darkTheme, OutlineDark,        Outline),
        error              = animThemeColor(darkTheme, ErrorDark,          Error)
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars     = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = animatedColorScheme,
        typography  = EasyReadTypography,
        shapes      = EasyReadShapes,
        content     = content
    )
}