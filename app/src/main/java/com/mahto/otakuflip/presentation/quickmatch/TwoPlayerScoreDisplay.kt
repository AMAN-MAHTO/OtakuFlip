package com.mahto.otakuflip.presentation.quickmatch

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.ui.theme.mochiyPopOne

@Composable
fun TwoPlayerScoreDisplay(
    modifier: Modifier = Modifier,
    currentPlayer: Int,
    playerScore1: Int?,
    playerScore2: Int?
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
                    text = "P1",
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
                    text = "" + playerScore1,
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
                    text = "P2",
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
                    text = "" + playerScore2,
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

}