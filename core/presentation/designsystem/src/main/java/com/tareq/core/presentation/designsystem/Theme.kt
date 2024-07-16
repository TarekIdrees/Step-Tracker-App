package com.tareq.core.presentation.designsystem

import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = StepTrackerYellow,
    background = StepTrackerBlack,
    surface = StepTrackerDarkGray,
    secondary = StepTrackerWhite,
    tertiary = StepTrackerWhite,
    primaryContainer = StepTrackerYellow30,
    onPrimary = StepTrackerBlack,
    onBackground = StepTrackerWhite,
    onSurface = StepTrackerWhite,
    onSurfaceVariant = StepTrackerGray,
    error = StepTrackerDarkRed
)

@Composable
fun StepTrackerTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}