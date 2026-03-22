package com.example.myges.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myges.ui.agenda.AgendaScreen
import com.example.myges.ui.grades.GradesScreen
import com.example.myges.ui.login.LoginScreen
import com.example.myges.ui.main.MainScreen
import com.example.myges.ui.news.NewsScreen
import com.example.myges.ui.profile.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Routes.LOGIN
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.MAIN) {
            MainScreen(
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun MainNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.AGENDA,
        modifier = modifier
    ) {
        composable(Routes.AGENDA) { AgendaScreen() }
        composable(Routes.GRADES) { GradesScreen() }
        composable(Routes.NEWS) { NewsScreen() }
        composable(Routes.PROFILE) { ProfileScreen(onLogout = onLogout) }
    }
}
