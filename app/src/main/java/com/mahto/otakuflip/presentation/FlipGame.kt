package com.mahto.otakuflip.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahto.otakuflip.data.GridSize
import kotlinx.coroutines.delay

@Composable
fun FlipGame(modifier: Modifier = Modifier, viewModel: FlipGameViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    val cards = state.cards
    val gridColumn = when (viewModel.state.collectAsState().value.uniqueCards) {
        GridSize.SMALL.uniqueCardsNumber -> GridSize.SMALL.column
        GridSize.MEDIUM.uniqueCardsNumber -> GridSize.MEDIUM.column
        GridSize.LARGE.uniqueCardsNumber-> GridSize.LARGE.column
        else -> GridSize.SMALL.column // default value or handle appropriately00000000000
    }
    val pattern = listOf(
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
        1,
        0,
        1,
        0,
        0,
        1,
        0,
        1,
    )
    LazyVerticalGrid(
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = if(gridColumn == 4) 8.dp else 1.dp),
        columns = GridCells.Fixed(gridColumn),
        modifier = modifier.padding(8.dp)
    ) {
        itemsIndexed(cards) {index, card ->


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

                Card(
                border = BorderStroke(2.dp, Color.White),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.elevatedCardElevation(5.dp),
                modifier = Modifier
                    .padding(if(gridColumn < 6) 4.dp else 2.dp)
                    .aspectRatio(1f)
                    .alpha(visibility.value)
                    .graphicsLayer(
                        rotationY = rotation.value,
                        rotationZ = if (pattern[card.id] == 0) 2f else -2f
                    ).shadow(
                        elevation = 4.dp,
                        shape = MaterialTheme.shapes.medium,
                        clip = false
                    )
                    .clickable(
                        enabled = rotation.value % 180f == 0f
                    ) {
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
                        Image(
                            painter = painterResource(card.imageId),
                            contentDescription = "",
                            modifier = Modifier.padding(4.dp)
                        )

                    } else {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.linearGradient(
                                        listOf(
                                            Color(
                                                0xffFFDB2E
                                            ), Color(0xffF99D32)
                                        )
                                    )
                                )
                        )
                    }
                }


            }


        }
    }
}

