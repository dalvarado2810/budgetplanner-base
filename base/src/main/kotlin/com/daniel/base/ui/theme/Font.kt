package com.daniel.base.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.daniel.budgetplanner.base.R

val fonts = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_bold, weight = FontWeight.Bold),
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.poppins_medium, weight = FontWeight.Medium)
)
