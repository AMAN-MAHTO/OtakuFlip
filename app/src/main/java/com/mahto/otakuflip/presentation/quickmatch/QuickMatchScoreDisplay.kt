package com.mahto.otakuflip.presentation.quickmatch

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.ui.theme.CustomGrey
import com.mahto.otakuflip.ui.theme.CustomRed
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.AnimatedScoreText


@Composable
fun QuickMatchScoreDisplay(
    modifier: Modifier = Modifier,
    currentPlayer: Int,
    timeTaken: String,
    playerScore: Int?
) {
    Box(
        Modifier
            .fillMaxWidth()
    ) {
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
                    if (currentPlayer == 1) CustomRed else CustomGrey, shape = MaterialTheme.shapes.medium
                )

        ) {
            Text(
                text = "Time: $timeTaken",
                color = Color.White,
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 4.dp
                ),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = mochiyPopOne,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.25f), offset =
                            Offset(0f, 5f), blurRadius = 3f
                    )
                )
            )
            Text(
                text = "" + playerScore,
                color = Color.White,


                modifier = Modifier
                    .alpha(0f)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = mochiyPopOne,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.25f), offset =
                            Offset(0f, 5f), blurRadius = 3f
                    )
                )
            )
        }

            AnimatedScoreText(
                score = playerScore,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
            )



    }
}