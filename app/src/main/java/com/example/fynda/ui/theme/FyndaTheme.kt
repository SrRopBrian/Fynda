package com.example.fynda.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fynda.R

val LexendFontFamily = FontFamily(
    Font(R.font.lexend_black, FontWeight.Black),
    Font(R.font.lexend_bold, FontWeight.Bold),
    Font(R.font.lexend_extrabold, FontWeight.ExtraBold),
    Font(R.font.lexend_extralight, FontWeight.ExtraLight),
    Font(R.font.lexend_light, FontWeight.Light),
    Font(R.font.lexend_medium, FontWeight.Medium),
    Font(R.font.lexend_regular, FontWeight.Normal)
)

val AcmeFontFamily = FontFamily(
    Font(R.font.acme_regular, FontWeight.Normal)
)

val FyndaTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = LexendFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = LexendFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = LexendFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AcmeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = AcmeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = LexendFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = LexendFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
)

val FyndaColorScheme = lightColorScheme(
    primary = Color(0xFF289FDA),
    secondary = Color(0xFF242424),
    tertiary = Color(0xFF8AD2FF)
)

val FyndaShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

@Composable
fun FyndaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = FyndaTypography,
        colorScheme = FyndaColorScheme,
        shapes = FyndaShapes,
        content = content
    )
}