package com.mahto.otakuflip.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahto.otakuflip.R
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.IconPatternBackground


@Composable
fun FlipGame(modifier: Modifier = Modifier, viewModel: FlipGameViewModel = hiltViewModel()) {
    val gridColumn = when (viewModel.uniqueCards) {
        12 -> 4
        24 -> 6
        32 -> 8
        else -> 4 // default value or handle appropriately00000000000
    }
    val cards = viewModel.cards.collectAsState().value
    val pattern = listOf(1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,
        1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,
        1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,)

    LazyVerticalGrid(columns = GridCells.Fixed(gridColumn), modifier = Modifier.padding(horizontal = 16.dp)) {
        items(cards, key = {it.id}) { card ->
            val rotation = animateFloatAsState(
                targetValue = if (card.isFlipped) 180f else 0f,
                animationSpec = tween(400),
                label = "card-rotation"
            )
            val visibility = animateFloatAsState(
                targetValue = if (card.isGone) 0f else 1f,
                animationSpec = tween(durationMillis = 600),
                label = "cutOffset"
            )
            val density = LocalDensity.current.density
            Card (
                border = BorderStroke(2.dp,Color.White),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.elevatedCardElevation(5.dp),
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(0.8f)
                    .alpha(visibility.value)
                    .graphicsLayer(
//                                rotationY = 30f,
                        rotationY = rotation.value,
                        cameraDistance = 12 * density,
                        rotationZ = if (pattern[card.id] == 0) 2f else -2f
                    )
                    .clickable(enabled = rotation.value % 180f == 0f) {
                        viewModel.onClickCard(
                            card
                        )
                    },
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (rotation.value >= 90f || card.isMatched) {
                        Image(painter = painterResource(card.imageId), contentDescription = "", modifier = Modifier.padding(4.dp))


                    } else {
                        Box(Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color(
                                            0xffFFDB2E
                                        ), Color(0xffF99D32)
                                    )
                                )
                            ))
                    }
                }


            }

        }
    }


}

@Composable
fun OtakuFlipGameScreen(modifier: Modifier = Modifier, viewModel: FlipGameViewModel = hiltViewModel()) {
    val matchedCards = viewModel.matchedCardsCount.collectAsState().value
    val currentPlayer = viewModel.currentPlayer.collectAsState().value
    val playerScore = viewModel.playerScore.collectAsState().value

    Box{
        IconPatternBackground(bgColor = Color(0xFF3D5AC0), R.drawable.akatsuki_logo)
//        Image(modifier = Modifier.fillMaxSize(),painter = painterResource(R.drawable.bg), contentDescription =  "")
        Column (
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center

        ) {
            Row(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Otaku Flip", style = MaterialTheme.typography.bodyLarge.copy(fontFamily = mochiyPopOne))
            }
            Spacer(Modifier.height(16.dp))

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        if (currentPlayer == 1) Color(0xfff24841) else Color(
                            0xffD4D4D4
                        ), shape = MaterialTheme.shapes.medium
                    )
            ) {
                Text(text = "Player 1" , modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp), color = Color.Black)
                Text(text =  ""+playerScore[1], modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp), color = Color.Black, style = MaterialTheme.typography.titleLarge)
            }
            Spacer(Modifier.height(16.dp))

            if(matchedCards == viewModel.uniqueCards){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("game End", style = MaterialTheme.typography.headlineLarge.copy(fontFamily = mochiyPopOne))
                    val score1 = playerScore[1] ?: 0
                    val score2 = playerScore[2] ?: 0

                    val winner = when {
                        score1 > score2 -> "Player 1"
                        score2 > score1 -> "Player 2"
                        else -> "Draw"
                    }

                    Text("Winner: $winner", style = MaterialTheme.typography.titleLarge)
                    Button(onClick = { viewModel.startGame() }) {
                        Text("Restart", style = MaterialTheme.typography.bodyLarge)

                    }
                }


            }else{
                FlipGame()
            }


            Spacer(Modifier.height(16.dp))
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        if (currentPlayer == 2) Color(0xfff24841) else Color(
                            0xffD4D4D4
                        ), shape = MaterialTheme.shapes.medium
                    )
            ) {
                Text(text = "Player 2" , modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp), color = Color.Black)
                Text(text =  ""+playerScore[2], modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    color = Color.Black, style = MaterialTheme.typography.titleLarge)
            }

        }
    }


}