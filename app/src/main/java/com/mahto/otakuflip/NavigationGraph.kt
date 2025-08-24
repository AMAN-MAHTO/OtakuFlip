package com.mahto.otakuflip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mahto.otakuflip.viewmodels.FlipGameViewModel
import com.mahto.otakuflip.presentation.twoplayer.TwoPlayerFlipGameScreen
import com.mahto.otakuflip.presentation.ThemeSelector.ThemeSelectorScreen
import com.mahto.otakuflip.viewmodels.ThemeSelectorVM
import com.mahto.otakuflip.presentation.home.HomeScreen2
import com.mahto.otakuflip.presentation.offline.OfflineFlipGameScreen
import com.mahto.otakuflip.presentation.quickmatch.QuickMatchFlipGameScreen
import com.mahto.otakuflip.presentation.setting.SettingScreen
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object OtakuFlipScreen : Screen("otaku_flip_screen")
    object QuickModeScreen : Screen("quick_mode_screen")
    object ThemeSelectorScreen : Screen("theme_selector_screen/{source}") {
        fun createRoute(source: String) = "theme_selector_screen/$source"
    }
    object SettingScreen: Screen("setting_screen")

}

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: String,
    flipGameViewModel: FlipGameViewModel = hiltViewModel(),
    themeSelectorVM: ThemeSelectorVM = hiltViewModel()

) {
//    val animeTheme = themeSelectorVM.animeTheme.collectAsState().value
//    val selectedGameMode = themeSelectorVM.selectedMode.collectAsState().value
//    LaunchedEffect(selectedGameMode, animeTheme) {
//        flipGameViewModel.configureGame(animeTheme, selectedGameMode)
//    }

    NavHost(navHostController,
        startDestination = startDestination,
     ) {

        composable(route  = Screen.SettingScreen.route) {
            SettingScreen(
                onClickBack = {
                    navHostController.navigate(Screen.HomeScreen.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen2(
//                navHostController = navHostController,
                onClick2Player = {
                    navHostController.navigate(Screen.OtakuFlipScreen.route)
                },
                onClickQuickMatch = {
                    navHostController.navigate(Screen.QuickModeScreen.route)
                },
                onClickSettingIcon = {
                    navHostController.navigate(Screen.SettingScreen.route)
                }
            )

        }

        composable(route = Screen.OtakuFlipScreen.route) {
//            TwoPlayerFlipGameScreen(navHostController = navHostController,
////                viewModel = flipGameViewModel,
//                onCLickBack = {
//                    navHostController.navigate(Screen.HomeScreen.route) {
//                        popUpTo(0) {
//                            inclusive = true
//                        }
//                    }
//
//
//                }
//            )
            OfflineFlipGameScreen(
                navHostController = navHostController,
                onClickBack = {
                    navHostController.navigate(Screen.HomeScreen.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }


                },
                numberOfPlayer = 2
            )
        }
        composable(route = Screen.QuickModeScreen.route) {
//            QuickMatchFlipGameScreen(
////                viewModel = flipGameViewModel,
//                navHostController = navHostController,
//                onClickBack = {
//
//                    navHostController.navigate(Screen.HomeScreen.route){
//                        popUpTo(0){
//                            inclusive = true
//                        }
//                    }
//
//                },
//            )

            OfflineFlipGameScreen(
                navHostController = navHostController,
                onClickBack = {
                    navHostController.navigate(Screen.HomeScreen.route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }


                },
                numberOfPlayer = 1
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