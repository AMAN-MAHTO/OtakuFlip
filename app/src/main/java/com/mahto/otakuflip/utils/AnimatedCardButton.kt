
package com.mahto.otakuflip.utils

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun ANimatedCardButton(

    pressedScale: Float = 0.85f,
    onClick: () -> Unit,
    content: @Composable ()->Unit,
) {
    var isPressed by remember { mutableStateOf(false) }

    val animatedScale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow),
        label = "IconButtonPressAnim"
    )
    Box(Modifier.pointerInput(Unit) {
        detectTapGestures(
            onPress = {
                isPressed = true
                tryAwaitRelease()
                isPressed = false
            }
        )
    } .graphicsLayer(
        scaleX = animatedScale,
        scaleY = animatedScale,
        transformOrigin = androidx.compose.ui.graphics.TransformOrigin.Center
    )) {

        Card(
                shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = Color(0xffF99D32)),
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .shadow(10.dp, MaterialTheme.shapes.medium, false)
           .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false

                    },
                    onTap = {
                        onClick()
                    }
                )
            },
        border = BorderStroke(2.dp, Color.White),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        ){
            content()
        }
    }

}
