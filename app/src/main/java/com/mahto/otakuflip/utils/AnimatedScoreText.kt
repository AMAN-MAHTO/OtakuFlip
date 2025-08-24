package com.mahto.otakuflip.utils

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mahto.otakuflip.ui.theme.mochiyPopOne

@Preview
@Composable
fun AnimatedScoreText(modifier: Modifier = Modifier, score: Int? = 10) {
    val displayedScore = remember { mutableStateOf(0) }
    val transition = remember { androidx.compose.animation.core.Animatable(1f) }
    LaunchedEffect(score) {
        if (score != null && score != 0) {
            transition.snapTo(1f)
            transition.animateTo(0f, animationSpec = tween(durationMillis = 1000))
            displayedScore.value = score
        }


    }
    Column(modifier, horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        Text(
            text = "    ${displayedScore.value}",
            modifier = modifier,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = mochiyPopOne,
                fontSize = 24.sp,
                color = Color.White
            )
        )
        if(score != null && score > 0){
            Text(
                text = "+${score?.minus(displayedScore.value)}",
                modifier = modifier.graphicsLayer(
                    translationY = -100f * (1f - transition.value),
                    alpha = 1f * transition.value
                ),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = mochiyPopOne,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }


    }

}