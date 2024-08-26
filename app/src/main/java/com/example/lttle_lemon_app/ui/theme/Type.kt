package com.example.lttle_lemon_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lttle_lemon_app.R

val Karla = FontFamily(
    Font(R.font.karla)
)
val Markazi = FontFamily(
    Font(R.font.markazitext_regular)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = Markazi,
        fontWeight = FontWeight.Medium,
        fontSize = 58.sp,
        color = LLColor.yellow,
    ),
    headlineMedium = TextStyle(
        fontFamily = Markazi,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        color = Color.White,
    ),
    bodyMedium = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.White,
    ),
    bodyLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Karla,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )

)