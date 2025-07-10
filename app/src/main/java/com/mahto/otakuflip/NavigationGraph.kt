package com.mahto.otakuflip

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mahto.otakuflip.presentation.HomeScreen
import com.mahto.otakuflip.presentation.OtakuFlipGameScreen
import com.mahto.otakuflip.presentation.QuickModeScreen

sealed class Screen(val route: String){
    object HomeScreen: Screen("home_screen")
    object OtakuFlipScreen: Screen("otaku_flip_screen")
    object QuickModeScreen: Screen("quick_mode_screen")
}

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navHostController: NavHostController, startDestination: String) {
    NavHost(navHostController, startDestination = startDestination){
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navHostController = navHostController)
        }

        composable(route = Screen.OtakuFlipScreen.route) {
            OtakuFlipGameScreen()
        }
        composable(route = Screen.QuickModeScreen.route) {
            QuickModeScreen()
        }
    }
}