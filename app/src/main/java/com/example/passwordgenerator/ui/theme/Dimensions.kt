package com.example.passwordgenerator.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val wee: Dp,
    val tiny: Dp,
    val small: Dp,
    val smallMedium: Dp,
    val medium: Dp,
    val mediumLarge: Dp,
    val big: Dp,
    val large: Dp,
    val humongous: Dp
)

val smallDimensions = Dimensions(
    wee = 0.5.dp,
    tiny = 2.dp,
    small = 3.dp,
    smallMedium = 6.dp,
    medium = 12.dp,
    mediumLarge = 24.dp,
    big = 48.dp,
    large = 75.dp,
    humongous = 125.dp
)

val compactDimensions = Dimensions(
    wee = 1.dp,
    tiny = 3.dp,
    small = 4.dp,
    smallMedium = 8.dp,
    medium = 16.dp,
    mediumLarge = 32.dp,
    big = 64.dp,
    large = 100.dp,
    humongous = 200.dp
)

val mediumDimensions = Dimensions(
    wee = 2.dp,
    tiny = 4.dp,
    small = 6.dp,
    smallMedium = 12.dp,
    medium = 24.dp,
    mediumLarge = 48.dp,
    big = 90.dp,
    large = 150.dp,
    humongous = 250.dp
)

val largeDimensions = Dimensions(
    wee = 3.dp,
    tiny = 7.dp,
    small = 10.dp,
    smallMedium = 20.dp,
    medium = 40.dp,
    mediumLarge = 80.dp,
    big = 125.dp,
    large = 250.dp,
    humongous = 350.dp
)