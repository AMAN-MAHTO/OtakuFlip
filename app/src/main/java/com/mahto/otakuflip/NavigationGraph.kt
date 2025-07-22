package com.mahto.otakuflip

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mahto.otakuflip.presentation.FlipGameViewModel
import com.mahto.otakuflip.presentation.HomeScreen
import com.mahto.otakuflip.presentation.twoplayer.TwoPlayerFlipGameScreen
import com.mahto.otakuflip.presentation.ThemeSelector.ThemeSelectorScreen
import com.mahto.otakuflip.presentation.ThemeSelector.ThemeSelectorVM
import com.mahto.otakuflip.presentation.quickmatch.QuickMatchFlipGameScreen
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object OtakuFlipScreen : Screen("otaku_flip_screen")
    object QuickModeScreen : Screen("quick_mode_screen")
    object ThemeSelectorScreen : Screen("theme_selector_screen/{source}") {
        fun createRoute(source: String) = "theme_selector_screen/$source"
    }

}

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String,
    flipGameViewModel: FlipGameViewModel = hiltViewModel(),
    themeSelectorVM: ThemeSelectorVM = hiltViewModel()

) {
    val animeTheme = themeSelectorVM.animeTheme.collectAsState().value
    val animeTheme2 = flipGameViewModel._animeTheme.collectAsState().value
    LaunchedEffect(Unit) {
        flipGameViewModel.startGame()
    }
    LaunchedEffect(flipGameViewModel.gameMode.collectAsState().value) {
        flipGameViewModel.startGame()
    }
    NavHost(navHostController, startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) },
     ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(
                navHostController = navHostController,
                onClick2Player = {
                    navHostController.navigate(Screen.ThemeSelectorScreen.createRoute("2p"))
                },
                onClickQuickMatch = {
                    navHostController.navigate(Screen.ThemeSelectorScreen.createRoute("quick"))
                },
                onClickSettingIcon = {}
            )

        }

        composable(route = Screen.OtakuFlipScreen.route) {
            TwoPlayerFlipGameScreen(navHostController = navHostController,)
        }
        composable(route = Screen.QuickModeScreen.route) {
            QuickMatchFlipGameScreen(
                viewModel =flipGameViewModel,
                navHostController = navHostController,
                onClickBack = {
                    navHostController.navigate(Screen.HomeScreen.route){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(
            route = "theme_selector_screen/{source}",
            arguments = listOf(element = navArgument("source") { type = NavType.StringType })
        ) { backStackEntry ->
            val source = backStackEntry.arguments?.getString("source") ?: "quick"

            ThemeSelectorScreen(
                viewModel = themeSelectorVM,
                navHostController = navHostController,
                onClickStartGame = {
                    when (source) {
                        "2p" -> navHostController.navigate(Screen.OtakuFlipScreen.route)
                        "quick" -> navHostController.navigate(Screen.QuickModeScreen.route)
                        else -> navHostController.popBackStack() // or default screen
                    }
                },
                onClickBack = {
                    navHostController.popBackStack()

                }
            )
        }

    }
}