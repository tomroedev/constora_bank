package com.example.constorabank.core.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary600,
    secondary = Secondary500,
    tertiary = Tertiary0,
    background = Primary100,
    surface = Secondary0,
    onPrimary = Secondary0,
    onSecondary = Secondary0,
    onBackground = Secondary700,
    onSurface = Secondary700,
    onTertiary = Secondary700,
    primaryContainer = Tertiary0
)

@Composable
fun ConstoraBankTheme(
    // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}