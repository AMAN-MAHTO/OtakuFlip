package com.mahto.otakuflip.presentation.quickmatch

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.R
import com.mahto.otakuflip.Screen
import com.mahto.otakuflip.ui.theme.mochiyPopOne

@Composable
fun TwoPlayerScoreScreen(
    modifier: Modifier,
    playerScore1: Int?,
    playerScore2: Int?,
    onClickHome: () -> Unit,
    onCLickPlay: () -> Unit,
    onClickShop: () -> Unit,
    ) {

    Column(
    modifier.fillMaxSize(),
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
                    text = if (playerScore1!!.toInt() > playerScore2!!.toInt()) "Player1" else "Player2",
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
                    text = "Player1         " + playerScore1
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
                    text = "Player2         " + playerScore2
                )
            }


        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
            ,
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
                            onClickHome()
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
                        .clickable {onCLickPlay()
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
                            onClickShop()
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
    }
}