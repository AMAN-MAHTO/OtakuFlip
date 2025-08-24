package com.mahto.otakuflip.presentation

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.data.GridSize
import com.mahto.otakuflip.viewmodels.FlipGameViewModel

@Composable
fun FlipGame(modifier: Modifier = Modifier, viewModel: FlipGameViewModel) {
    Log.d("jjk", "FlipGame: started")
    val cards = viewModel.cards.collectAsState().value
    val gridColumn = when (viewModel.uniqueCards.collectAsState().value) {
        GridSize.SMALL.uniqueCardsNumber -> GridSize.SMALL.column
        GridSize.MEDIUM.uniqueCardsNumber -> GridSize.MEDIUM.column
        GridSize.LARGE.uniqueCardsNumber -> GridSize.LARGE.column
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
    )
    val backBrush = remember {
        Brush.linearGradient(listOf(Color(0xffFFDB2E), Color(0xffF99D32)))
    }
//    LazyVerticalGrid(columns = GridCells.Fixed(6)) {
//        itemsIndexed(cards) { index, card ->
//            Card {
//                Text(card.toString())
////                Image(
////                    painter = painterResource(card.imageId),
////                    contentDescription = "",
////                    modifier = Modifier.padding(4.dp)
////                )
//
//            }
//
//        }
//    }
    LazyVerticalGrid(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = if (gridColumn == 4) 8.dp else 1.dp
        ),
        columns = GridCells.Fixed(gridColumn),
        modifier = modifier.padding(8.dp)
    ) {
        itemsIndexed(cards, key = { _, card -> card.id }) { index, card ->
            val rotation by animateFloatAsState(
                targetValue = if (card.isFlipped) 180f else 0f,
                animationSpec = tween(400),
                label = "card-rotation"
            )

            val visibility by animateFloatAsState(
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
                    .padding(if (gridColumn < 6) 4.dp else 2.dp)
                    .aspectRatio(1f)
                    .alpha(visibility)
                    .graphicsLayer(
                        rotationY = rotation,
                        rotationZ = if (pattern[card.id % pattern.size] == 0) 2f else -2f
                    )
                    .clickable(
                        enabled = rotation % 180f == 0f
                    ) {
                        viewModel.onClickCard(card)
                    },
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (
                        rotation >= 90f
                        ||
                        card.isMatched
                    ) {
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
                                    brush = backBrush
                                )
                        )
                    }
                }
            }
        }
    }
}

