package com.mahto.otakuflip.presentation.twoplayer

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
fun TwoPlayerFlipGameScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: FlipGameViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    val currentPlayer = state.currentPlayer
    val playerScore = state.playerScore
    val matchedCards = state.matchedCards
    val animeTheme = viewModel._animeTheme.collectAsState().value
    viewModel.numberOfPlayer(2)
    LaunchedEffect(viewModel.gameMode.collectAsState().value) {
        delay(300)
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


            if (
                matchedCards == viewModel.state.collectAsState().value.uniqueCards
            ) {

                ScreenHeader(
                    modifier = Modifier.align(Alignment.TopCenter),
                    onClickBack = { navHostController.popBackStack() },
                    enableBackButton = true
                )
                Column(
                    Modifier.align(Alignment.Center),
                    verticalArrangement = Arrangement.SpaceBetween
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
                                text = "Winner"
                            )
                        }
                        Row(

                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            Color(0xffFEB56A),
                                            Color(0xffFF9D38)
                                        )
                                    ), shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Text(
                                text = if (playerScore[1]!!.toInt() > playerScore[2]!!.toInt()) "Player1" else "Player2",
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
                        Spacer(Modifier.height(16.dp))
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
                                text = "Player1         " + playerScore[1]
                            )
                        }
                        Spacer(Modifier.height(12.dp))
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
                                text = "Player2         " + playerScore[2]
                            )
                        }


                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .align(Alignment.BottomCenter),
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
                                .graphicsLayer {
                                    rotationZ = 2f
                                }
                                .clickable {
                                    navHostController.navigate(Screen.HomeScreen.route){
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xffFF2E2E))
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.home),
                                    "",
                                    Modifier
                                        .size(36.dp)
                                        .graphicsLayer { rotationZ = 2f },
                                    tint = Color.White
                                )
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
                                .graphicsLayer {
                                    rotationZ = -2f
                                }
                                .clickable {
                                    viewModel.startGame()
                                },
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xff25C247))
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.play),
                                    "",
                                    Modifier
                                        .size(36.dp)
                                        .graphicsLayer { rotationZ = -2f },
                                    tint = Color.White
                                )
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
                                .graphicsLayer {
                                    rotationZ = 2f
                                }
                                .clickable {
                                    viewModel.onClickShop()
                                },
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color(0xffFFB62E))
                            ) {

                                Icon(
                                    painter = painterResource(R.drawable.shoping_bag),
                                    "",
                                    Modifier
                                        .size(36.dp)
                                        .graphicsLayer { rotationZ = 2f },
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }


            } else {

                ScreenHeader(
                    modifier = Modifier.align(Alignment.TopCenter),
                    onClickBack = { navHostController.popBackStack() },
                    enableBackButton = true
                )


                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 60.dp)
                        .align(Alignment.Center)
                ) {
                    Row {
                        Row(

                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = MaterialTheme.shapes.medium,
                                    clip = false
                                )
                                .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
                                .background(
                                    brush =
                                        if (currentPlayer == 1) Brush.linearGradient(
                                            listOf(
                                                Color(
                                                    0xFF6BCE5A
                                                ), Color(0xFF38B428)
                                            )
                                        ) else
                                            Brush.linearGradient(
                                                listOf(
                                                    Color(0xffD4D4D4),
                                                    Color(0xff9D9D9C)
                                                )
                                            ), shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Text(
                                    text = "Player1",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyMedium.copy(
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
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontFamily = mochiyPopOne,
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.25f), offset =
                                                Offset(0f, 5f), blurRadius = 3f
                                        )
                                    )
                                )
                            }


                        }
                        Row(

                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = MaterialTheme.shapes.medium,
                                    clip = false
                                )
                                .border(2.dp, Color.White, shape = MaterialTheme.shapes.medium)
                                .background(
                                    brush =
                                        if (currentPlayer == 2) Brush.linearGradient(
                                            listOf(
                                                Color(
                                                    0xFF6BCE5A
                                                ), Color(0xFF38B428)
                                            )
                                        ) else
                                            Brush.linearGradient(
                                                listOf(
                                                    Color(0xffD4D4D4),
                                                    Color(0xff9D9D9C)
                                                )
                                            ), shape = MaterialTheme.shapes.medium
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Text(
                                    text = "Player2",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontFamily = mochiyPopOne,
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.25f), offset =
                                                Offset(0f, 5f), blurRadius = 3f
                                        )
                                    )
                                )
                                Text(
                                    text = "" + playerScore[2],
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontFamily = mochiyPopOne,
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.25f), offset =
                                                Offset(0f, 5f), blurRadius = 3f
                                        )
                                    )
                                )
                            }


                        }

                    }
                    FlipGame()


                }
            }


        }
    }


}