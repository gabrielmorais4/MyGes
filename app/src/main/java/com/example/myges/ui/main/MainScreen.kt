package com.example.myges.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myges.ui.agenda.AgendaScreen
import com.example.myges.ui.grades.GradesScreen
import com.example.myges.ui.navigation.BottomNavItem
import com.example.myges.ui.news.NewsScreen
import com.example.myges.ui.profile.ProfileScreen

@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomNavItem.all.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = stringResource(item.contentDescRes)
                            )
                        },
                        label = { Text(stringResource(item.labelRes)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Agenda.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Agenda.route) { AgendaScreen() }
            composable(BottomNavItem.Grades.route) { GradesScreen() }
            composable(BottomNavItem.News.route) { NewsScreen() }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen(onLogout = onLogout)
            }
        }
    }
}
