package com.example.myges.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myges.R

sealed class BottomNavItem(
    val route: String,
    @param:StringRes val labelRes: Int,
    val icon: ImageVector,
    @param:StringRes val contentDescRes: Int
) {
    data object Agenda : BottomNavItem(
        route = Routes.AGENDA,
        labelRes = R.string.nav_agenda,
        icon = Icons.Default.CalendarMonth,
        contentDescRes = R.string.nav_agenda
    )

    data object Grades : BottomNavItem(
        route = Routes.GRADES,
        labelRes = R.string.nav_grades,
        icon = Icons.Default.School,
        contentDescRes = R.string.nav_grades
    )

    data object News : BottomNavItem(
        route = Routes.NEWS,
        labelRes = R.string.nav_news,
        icon = Icons.Default.Newspaper,
        contentDescRes = R.string.nav_news
    )

    data object Profile : BottomNavItem(
        route = Routes.PROFILE,
        labelRes = R.string.nav_profile,
        icon = Icons.Default.Person,
        contentDescRes = R.string.nav_profile
    )

    companion object {
        val all = listOf(Agenda, Grades, News, Profile)
    }
}
