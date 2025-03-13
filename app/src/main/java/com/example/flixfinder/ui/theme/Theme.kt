package com.example.flixfinder.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NetflixRed,
    secondary = DarkGray,
    tertiary = LightGray,
    background = Black,
    surface = Black,
    onBackground = White,
    onSurface = White
)

@Composable
fun FlixFinderTheme(
    // Removed darkTheme parameter as it's not being used
    content: @Composable () -> Unit
) {
    // Always use dark theme for Netflix-like appearance
    val colorScheme = DarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}