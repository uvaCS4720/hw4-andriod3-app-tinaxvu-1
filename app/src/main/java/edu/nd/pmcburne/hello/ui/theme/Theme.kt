package edu.nd.pmcburne.hello.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val UVANavy  = Color(0xFF232D4B)
private val UVAOrange = Color(0xFFE57200)
private val UVABlue   = Color(0xFF1C6EA4)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColors = lightColorScheme(
    primary         = UVANavy,
    onPrimary       = Color.White,
    secondary       = UVAOrange,
    onSecondary     = Color.White,
    tertiary        = UVABlue,
    background      = Color(0xFFF5F5F5),
    surface         = Color.White,
    onBackground    = Color(0xFF1A1A1A),
    onSurface       = Color(0xFF1A1A1A)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CampusMapTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}