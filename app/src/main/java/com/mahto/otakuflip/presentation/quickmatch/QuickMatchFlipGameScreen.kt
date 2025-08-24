package com.mahto.otakuflip.presentation.quickmatch

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mahto.otakuflip.Screen
import com.mahto.otakuflip.presentation.FlipGame
import com.mahto.otakuflip.viewmodels.FlipGameViewModel
import com.mahto.otakuflip.presentation.ScreenHeader
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.ImageBackground
import kotlinx.coroutines.delay

val TAG = "debug"
@Composable
fun QuickMatchFlipGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: FlipGameViewModel = hiltViewModel(),
    onClickBack: () -> Unit = {}
) {
    Log.d("jjk", "QuickMatchFlipGameScreen: start")
    val currentPlayer = viewModel.currentPlayer.collectAsState().value
    val playerScore = viewModel.playerScore.collectAsState().value
    val animeTheme = viewModel._animeTheme.collectAsState().value
    val gameMode = viewModel._gameMode.collectAsState().value
    val matchedCards = viewModel.matchedCards.collectAsState().value
    val uniqueCards = viewModel.uniqueCards.collectAsState().value

    LaunchedEffect(Unit) {
        delay(16)
        viewModel.startGame()
    }



    Box {
//        IconPatternBackground(animeTheme.bgColor, animeTheme.bgImg)
        ImageBackground(animeTheme.bgImgFull)
        Box(
            Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .padding(top = 20.dp)
                .fillMaxSize()
        ) {
            ScreenHeader(
                modifier = Modifier.align(Alignment.TopCenter),
                onClickBack = onClickBack,
                enableBackButton = true
            )

            if (
                matchedCards != uniqueCards
            ) {
                LaunchedEffect(Unit) {
                    viewModel.onGameEnd()
                }
                QuickMatchScoreScreen(
                    modifier = Modifier.align(Alignment.Center),
                    timeTaken = formatTime(viewModel.timeTaken.value),
                    score = playerScore[1],
                    highScore = viewModel._highScore.collectAsState().value,
                    onClickHome = {
                        navHostController.navigate(Screen.HomeScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onCLickPlay = {
                        viewModel.startGame()
                    },
                    onClickShop = {
                        viewModel.onClickShop()
                    }

                )

            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp)
                        .align(Alignment.Center)
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        textAlign = TextAlign.End,
                        text = "High Score " + viewModel._highScore.collectAsState().value,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = mochiyPopOne,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f), offset =
                                    Offset(0f, 5f), blurRadius = 3f
                            )
                        )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        QuickMatchScoreDisplay(
                            modifier = Modifier,
                            currentPlayer = currentPlayer,
                            timeTaken = formatTime(viewModel.timeElapsed.collectAsState().value),
                            playerScore = playerScore[1]
                        )
                        Column(
                            Modifier
                                .fillMaxSize()
                                .align(Alignment.Center),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if(viewModel._isloading.value) {
                                FlipGame(viewModel  = viewModel)
                            }
                            //                            else{
//                                LinearProgressIndicator()
//
//                            }
                        }

                    }


                }

            }


        }

    }

}

private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}
