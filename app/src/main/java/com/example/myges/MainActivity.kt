package com.example.myges

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.myges.ui.navigation.NavGraph
import com.example.myges.ui.navigation.Routes
import com.example.myges.ui.theme.MyGesTheme
import com.example.myges.util.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideActivityTransition(
            OVERRIDE_TRANSITION_OPEN,
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        enableEdgeToEdge()
        setContent {
            MyGesTheme {
                val navController = rememberNavController()
                val startDestination = if (tokenManager.isLoggedIn()) Routes.MAIN else Routes.LOGIN
                NavGraph(navController = navController, startDestination = startDestination)
            }
        }
    }
}
