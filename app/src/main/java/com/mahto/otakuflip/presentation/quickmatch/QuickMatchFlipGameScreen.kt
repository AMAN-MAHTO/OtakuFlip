package com.mahto.otakuflip.presentation.quickmatch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mahto.otakuflip.R
import com.mahto.otakuflip.Screen
import com.mahto.otakuflip.presentation.FlipGame
import com.mahto.otakuflip.presentation.FlipGameViewModel
import com.mahto.otakuflip.presentation.ScreenHeader
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.AnimatedImageBackground
import com.mahto.otakuflip.utils.ImageBackground
import kotlinx.coroutines.delay

@Composable
fun QuickMatchFlipGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: FlipGameViewModel = hiltViewModel(),
    onClickBack: ()->Unit = {}
) {
    val currentPlayer = viewModel.state.collectAsState().value.currentPlayer
    val playerScore = viewModel.state.collectAsState().value.playerScore
    val matchedCards = viewModel.state.collectAsState().value.matchedCards
    val animeTheme = viewModel._animeTheme.collectAsState().value
    var cardsVisible by
    remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(50)
        cardsVisible = true
    }




    Box {
//        IconPatternBackground(animeTheme.bgColor, animeTheme.bgImg)
        ImageBackground(animeTheme.bgImgFull)
        Box(
            Modifier.padding(horizontal = 12.dp, vertical = 12.dp).padding(top = 20.dp).fillMaxSize()
        ) {
            ScreenHeader(
                modifier = Modifier.align(Alignment.TopCenter),
                onClickBack = onClickBack,
                enableBackButton = true
            )

            if (
                matchedCards == viewModel.state.collectAsState().value.uniqueCards
            ) {
                LaunchedEffect(Unit) {
                    viewModel.onGameEnd()

                }
                Column(
                    Modifier
                        .padding(top = 60.dp)
                        .fillMaxSize()
                            .align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Spacer(Modifier.height(1.dp))
                    Column {
                        Row(
                            modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = mochiyPopOne, shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.25f), offset =
                                            Offset(0f, 5f), blurRadius = 3f
                                    )
                                ),
                                text = "Time Taken: ${formatTime(viewModel.timeTaken.value)}"
                            )
                        }

                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .padding(top = 4.dp)
                                .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
                                .background(
                                    brush = Brush.linearGradient(listOf(Color(0xffFEB56A), Color(0xffFF9D38))), shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Text(
                                text = "Score   " + playerScore[1],
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp, vertical = 16.dp)
                                    .padding(bottom = 4.dp),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontFamily = mochiyPopOne,
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.25f), offset =
                                            Offset(0f, 5f), blurRadius = 3f
                                    )
                                )
                            )

                        }
                        Row(
                            modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = mochiyPopOne, shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.25f), offset =
                                            Offset(0f, 5f), blurRadius = 3f
                                    )
                                ),
                                text = "High Score "+viewModel._highScore.collectAsState().value
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {


                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Card(
                                border = BorderStroke(2.dp, Color.White),
                                shape = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.elevatedCardElevation(5.dp),
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(4.dp)
                                    .aspectRatio(1f)
                                    .graphicsLayer{
                                        rotationZ = 2f
                                    }
                                    .clickable{
                                        navHostController.navigate(Screen.HomeScreen.route){
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                            )  {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().background(Color(0xffFF2E2E))){

                                    Icon(painter = painterResource(R.drawable.home),"",Modifier.size(36.dp).graphicsLayer{rotationZ = 2f}, tint = Color.White)
                                }
                            }

                            Card(
                                border = BorderStroke(2.dp, Color.White),
                                shape = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.elevatedCardElevation(5.dp),
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(4.dp)
                                    .aspectRatio(1f)
                                    .graphicsLayer{
                                        rotationZ = -2f
                                    }
                                    .clickable{
                                        viewModel.startGame()
                                    },
                            )  {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().background(Color(0xff25C247))){

                                    Icon(painter = painterResource(R.drawable.play),"",Modifier.size(36.dp).graphicsLayer{rotationZ = -2f}, tint = Color.White)
                                }
                            }

                            Card(
                                border = BorderStroke(2.dp, Color.White),
                                shape = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.elevatedCardElevation(5.dp),
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(4.dp)
                                    .aspectRatio(1f)
                                    .graphicsLayer{
                                        rotationZ = 2f
                                    }
                                    .clickable{
                                        viewModel.onClickShop()
                                    },
                            )  {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().background(Color(0xffFFB62E))){

                                    Icon(painter = painterResource(R.drawable.shoping_bag),"",Modifier.size(36.dp).graphicsLayer{rotationZ = 2f}, tint = Color.White)
                                }
                            }
                        }
                    }

                }




            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp)
                        .align(Alignment.Center)
                ) {

                        Text(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                            textAlign = TextAlign.End,
                            text = "High Score "+viewModel._highScore.collectAsState().value, style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = mochiyPopOne,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f), offset =
                                    Offset(0f, 5f), blurRadius = 3f
                            )
                        ))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = MaterialTheme.shapes.medium,
                                    clip = false
                                )
                                .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
                                .background(
                                    if (currentPlayer == 1) Color(0xfff24841) else Color(
                                        0xffD4D4D4
                                    ), shape = MaterialTheme.shapes.medium
                                )

                        ) {
                            Text(
                                text = "Time: ${formatTime(viewModel.state.collectAsState().value.timeElapsed)}",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontFamily = mochiyPopOne,
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.25f), offset =
                                            Offset(0f, 5f), blurRadius = 3f
                                    )
                                )
                            )
                            Text(
                                text = "" + playerScore[1],
                                color = Color.White,

                                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontFamily = mochiyPopOne,
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.25f), offset =
                                            Offset(0f, 5f), blurRadius = 3f
                                    )
                                )
                            )
                        }

                    Spacer(Modifier.height(16.dp))
                    AnimatedVisibility(cardsVisible) {
                        FlipGame(viewModel = viewModel)

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
