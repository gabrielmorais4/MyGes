package com.example.myges.ui.theme

import androidx.compose.ui.graphics.Color

// ===== Dark color scheme — Ocean Deep =====
val BackgroundDark = Color(0xFF08090F)
val SurfaceDark = Color(0xFF0D1120)
val SurfaceVariantDark = Color(0xFF13192E)
val OnSurfaceDark = Color(0xFFE2EEFF)
val OnSurfaceVariantDark = Color(0xFF8AA4C8)
val OnBackgroundDark = Color(0xFFE2EEFF)

val PrimaryDark = Color(0xFF60A5FA)           // blue-400 — vibrant sky blue
val OnPrimaryDark = Color(0xFF00244F)
val PrimaryContainerDark = Color(0xFF0A3065)
val OnPrimaryContainerDark = Color(0xFFBFDBFE)

val SecondaryDark = Color(0xFF7BA4C4)
val OnSecondaryDark = Color(0xFF0D1E30)
val SecondaryContainerDark = Color(0xFF122040)
val OnSecondaryContainerDark = Color(0xFFB8D4EE)

val TertiaryDark = Color(0xFF34D399)          // emerald — success/good grades
val OnTertiaryDark = Color(0xFF00251A)
val TertiaryContainerDark = Color(0xFF004030)
val OnTertiaryContainerDark = Color(0xFFA7F3D0)

val ErrorDark = Color(0xFFF87171)
val OnErrorDark = Color(0xFF3B0000)
val ErrorContainerDark = Color(0xFF3B1515)
val OnErrorContainerDark = Color(0xFFFECACA)

val OutlineDark = Color(0xFF1E2E4A)
val OutlineVariantDark = Color(0xFF131E32)

// ===== Light color scheme =====
val BackgroundLight = Color(0xFFEFF6FF)
val SurfaceLight = Color(0xFFFFFFFF)
val SurfaceVariantLight = Color(0xFFDBEAFE)
val OnSurfaceLight = Color(0xFF0A1628)
val OnSurfaceVariantLight = Color(0xFF2D4E6E)
val OnBackgroundLight = Color(0xFF0A1628)

val PrimaryLight = Color(0xFF1D4ED8)          // blue-700
val OnPrimaryLight = Color(0xFFFFFFFF)
val PrimaryContainerLight = Color(0xFFBFDBFE)
val OnPrimaryContainerLight = Color(0xFF0A2050)

val SecondaryLight = Color(0xFF3B6EA8)
val OnSecondaryLight = Color(0xFFFFFFFF)
val SecondaryContainerLight = Color(0xFFC8E0F8)
val OnSecondaryContainerLight = Color(0xFF0D2840)

val TertiaryLight = Color(0xFF059669)
val OnTertiaryLight = Color(0xFFFFFFFF)
val TertiaryContainerLight = Color(0xFFD1FAE5)
val OnTertiaryContainerLight = Color(0xFF003320)

val ErrorLight = Color(0xFFDC2626)
val OnErrorLight = Color(0xFFFFFFFF)
val ErrorContainerLight = Color(0xFFFEE2E2)
val OnErrorContainerLight = Color(0xFF7F1D1D)

val OutlineLight = Color(0xFF9AB4D4)
val OutlineVariantLight = Color(0xFFC8DDEF)

// ===== Extended semantic colors =====
val SuccessLight = TertiaryLight
val OnSuccessLight = OnTertiaryLight
val SuccessDark = TertiaryDark
val OnSuccessDark = OnTertiaryDark

val WarningLight = Color(0xFFD97706)
val OnWarningLight = Color(0xFFFFFFFF)
val WarningDark = Color(0xFFFBBF24)
val OnWarningDark = Color(0xFF3B2000)

// ===== MaterialTheme extensions for semantic colors =====
// Access via MaterialTheme.extendedColors.success / .warning defined in Theme.kt
