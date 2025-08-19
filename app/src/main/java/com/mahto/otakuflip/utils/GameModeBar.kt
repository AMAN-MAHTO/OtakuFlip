package com.mahto.otakuflip.utils

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.mahto.otakuflip.data.GAMEMODE
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import kotlin.math.abs
import androidx.compose.foundation.layout.Box

import androidx.compose.ui.text.style.TextAlign
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun GameModeBar(
    currentMode: GAMEMODE,
    onModeChange: (GAMEMODE)-> Unit,
    modifier: Modifier  = Modifier,
) {
    val sliderState = remember{ mutableStateOf(when(currentMode){
        GAMEMODE.EASY_MODE -> 0.0f
        GAMEMODE.MEDIUM_MODE -> 0.5f
        GAMEMODE.HARD_MODE -> 1f
    }) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(){
            val r = 60f
            val progress1 = sliderState.value*2
            Text(
                modifier = Modifier.fillMaxWidth().graphicsLayer(
                    rotationX = 90f*progress1,
                    translationY = -r * sin(progress1* (Math.PI / 2)).toFloat(),
                ).alpha(1 - sliderState.value*2),
                text = "EASY", color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        fontFamily = mochiyPopOne,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f), offset =
                                Offset(0f, 5f), blurRadius = 3f
                        )
                    )
            )

            Text(
                modifier = Modifier.fillMaxWidth().graphicsLayer(
                    rotationX = (270f + 180f*sliderState.value)%360f,
                    translationY = if (sliderState.value < 0.5f) {
                        val t = sliderState.value * 2f
                        r * cos(t * (Math.PI / 2)).toFloat()
                    } else {
                        val t = (sliderState.value - 0.5f) * 2f
                        -r * sin(t * (Math.PI / 2)).toFloat()
                    },
                ).alpha(2 - sliderState.value*2),
                text = "MEDIUM", color = Color.White,
                textAlign = TextAlign.Center,

                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        fontFamily = mochiyPopOne,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f), offset =
                                Offset(0f, 5f), blurRadius = 3f
                        )
                    )
            )
            val progress = sliderState.value*2 -1
            Text(
                modifier = Modifier.fillMaxWidth().graphicsLayer(
                    rotationX = 270 + 90f* progress,
                    translationY = r * ( cos(progress * (Math.PI / 2))).toFloat() - 10f,
                ).alpha(abs((1 - sliderState.value*2).coerceIn(-1f,0f))*1f),
                text = "HARD", color = Color.White,
                textAlign = TextAlign.Center,

                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        fontFamily = mochiyPopOne,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f), offset =
                                Offset(0f, 5f), blurRadius = 3f
                        )
                    )
            )




        }

        Spacer(Modifier.height(32.dp))
        Box() {

            CustomSlider2(
                progress = sliderState.value,
                onChange = { value ->
                    sliderState.value = value
                    when (value) {
                        in 0f..0.4f -> onModeChange( GAMEMODE.EASY_MODE)
                        in 0.4f..0.6f -> onModeChange(GAMEMODE.MEDIUM_MODE)
                        else -> onModeChange(GAMEMODE.HARD_MODE)
                    }
                },
            )
        }

    }
}


@Composable
fun CustomSlider2(
    progress: Float, // 0f..1f
    onChange: (Float) -> Unit
) {
    val trackHeight = 32.dp
    val thumbRadius = 15.dp

    var isPressed by remember { mutableStateOf(false) }


    val infiniteTransition = rememberInfiniteTransition("infinite")
    val animatedThumbRadius = infiniteTransition.animateFloat(
        initialValue = thumbRadius.value*2.7f,
        targetValue = (thumbRadius.value+ thumbRadius.value*0.1f)*2.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse,
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        val newValue = (offset.x / size.width).coerceIn(0f, 1f)
                        onChange(newValue)
                    },
                    onDrag = { change, _ ->
                        val newValue = (change.position.x / size.width).coerceIn(0f, 1f)
                        onChange(newValue)
                    },

                )
                detectTapGestures(

                )

            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val borderWidth = 10f
            val recWidth = size.width
            // Track
            drawRoundRect(
//                topLeft = Offset(10f, 0f),

                color = Color.LightGray,
                size = Size(recWidth, trackHeight.toPx()),
                cornerRadius = CornerRadius(trackHeight.toPx() / 2)
            )

            // Progress
            drawRoundRect(
//                topLeft = Offset(10f, 0f),

                color = Color(
                    red = (if (progress < 0.5) 2 * progress else 1f).coerceIn(0f, 0.9f),
                    green = (if (progress < 0.5) 1f else 2 - 2 * progress).coerceIn(0f, 0.9f),
                    blue = 0f
                ),
                size = Size(recWidth * progress, trackHeight.toPx()),
                cornerRadius = CornerRadius(trackHeight.toPx() / 2)
            )
            drawRoundRect(
//                topLeft = Offset(10f, 0f),
                color = Color.White,
                size = Size(recWidth, trackHeight.toPx()),
                style = Stroke(width = 10f),
                cornerRadius = CornerRadius(trackHeight.toPx() / 2)
            )
            // Thumb
            drawCircle(
                color = Color(0xffF5B02F),
                radius = animatedThumbRadius.value,
                center = Offset(
                    (size.width * progress).coerceIn(
                        thumbRadius.toPx() + borderWidth,
                        size.width - thumbRadius.toPx() - borderWidth
                    ), trackHeight.toPx() / 2f
                )
            )
            drawCircle(
                style = Stroke(width = borderWidth),
                color = Color.White,
                radius = animatedThumbRadius.value,
                center = Offset(
                    (size.width * progress).coerceIn(
                        thumbRadius.toPx() + borderWidth,
                        size.width - thumbRadius.toPx() - borderWidth
                    ), trackHeight.toPx() / 2f
                )
            )
        }
    }
}

