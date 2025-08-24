package com.mahto.otakuflip.presentation.twoplayer

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mahto.otakuflip.Screen
import com.mahto.otakuflip.presentation.FlipGame
import com.mahto.otakuflip.viewmodels.FlipGameViewModel
import com.mahto.otakuflip.presentation.ScreenHeader
import com.mahto.otakuflip.presentation.quickmatch.TwoPlayerScoreDisplay
import com.mahto.otakuflip.presentation.quickmatch.TwoPlayerScoreScreen

import com.mahto.otakuflip.utils.ImageBackground
import kotlinx.coroutines.delay


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TwoPlayerFlipGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: FlipGameViewModel = hiltViewModel(),
    onCLickBack: () -> Unit = {}
) {
//    val state = viewModel.state.collectAsState().value
    val currentPlayer = viewModel.currentPlayer.collectAsState().value
    val playerScore = viewModel.playerScore.collectAsState().value
    val matchedCards = viewModel.matchedCards.collectAsState().value
    val uniqueCards = viewModel.uniqueCards.collectAsState().value
    val animeTheme = viewModel._animeTheme.collectAsState().value
    viewModel.numberOfPlayer(2)

    LaunchedEffect(Unit) {
        delay(16)
        viewModel.startGame()
    }

    Box {
//        IconPatternBackground(bgColor = Color(0xff3D5AC0), R.drawable.akatsuki_logo)
        ImageBackground(animeTheme.bgImgFull)
        Box(
            Modifier
                .padding(horizontal = 12.dp, vertical = 32.dp)
                .fillMaxSize()

        ) {
            ScreenHeader(
                modifier = Modifier.align(Alignment.TopCenter),
                onClickBack = { navHostController.popBackStack() },
                enableBackButton = true
            )
//
                if (
                    matchedCards == uniqueCards
                ) {

                    TwoPlayerScoreScreen(
                        modifier = Modifier.align(Alignment.Center),
                        playerScore1 = playerScore[1],
                        playerScore2 = playerScore[2],
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


                }
                else {

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(top = 60.dp)
                            .align(Alignment.Center)
                    ) {
                        TwoPlayerScoreDisplay(
                            modifier = Modifier,
                            currentPlayer = currentPlayer,
                            playerScore1 = playerScore[1],
                            playerScore2 = playerScore[2]
                        )
                        if(viewModel._isloading.value) {
                            FlipGame(viewModel = viewModel)
                        }else{
                            CircularProgressIndicator()
                        }

                    }
                }



        }
    }


}