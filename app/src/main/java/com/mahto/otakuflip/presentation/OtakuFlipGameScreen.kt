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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahto.otakuflip.R
import com.mahto.otakuflip.ui.theme.mochiyPopOne

@Composable
fun IconPatternBackground(bgColor: Color, imageId: Int ) {

    val pattern1 = listOf(
        1,0,1,0,1,0,0,1,0,1,0,1,
        1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,
        1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,)
    LazyVerticalGrid(columns = GridCells.Fixed(6), modifier = Modifier.background(bgColor)) {
        items(pattern1) { pattern->
            if(pattern == 1){
                Image(modifier = Modifier.fillMaxSize(0.5f).alpha(0.3f), painter = painterResource(imageId), contentDescription = "")
            }else{
                Image(modifier = Modifier.fillMaxSize().alpha(0f), painter = painterResource(imageId), contentDescription = "", )
            }

        }
    }
}



@Composable
fun FlipGame(modifier: Modifier = Modifier, viewModel: FlipGameViewModel) {
    val cards = viewModel.cards
    Box(

    ){
        IconPatternBackground(bgColor = Color(0xFF3D5AC0), R.drawable.akatsuki_logo)
        Column (
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center

        ) {
            Row(
                modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text("Otaku Flip", style = MaterialTheme.typography.bodyLarge.copy(fontFamily = mochiyPopOne))
            }

            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(8.dp).background(if(viewModel.curentPlayer == 1) Color(0xfff24841  )else Color(0xffD4D4D4), shape = MaterialTheme.shapes.medium)
            ) {
                Text(text = "Player 1" , modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = Color.Black)
                Text(text =  ""+viewModel.playerScore[1], modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = Color.Black)
            }
            Spacer(Modifier.height(16.dp))
            val pattern = listOf(1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,1,0,1,0,0,1,0,1,)
            LazyVerticalGrid(columns = GridCells.Fixed(4), modifier = Modifier.padding(horizontal = 16.dp)) {
                items(cards) { card ->
                    val rotation = animateFloatAsState(
                        targetValue = if (card.isFliped) 180f else 0f,
                        animationSpec = tween(1000),
                        label = "card-rotation"
                    )
                    val offsetX = animateDpAsState(
                        targetValue = if (card.beginCut) 50.dp else 0.dp,
                        animationSpec = tween(durationMillis = 600),
                        label = "cutOffset"
                    )
                    Card (
                        border = BorderStroke(2.dp,Color.White),
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.elevatedCardElevation(5.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .aspectRatio(1f)
                            .alpha(if(card.isGone) 0f else 1f)
                            .graphicsLayer(
//                                rotationY = 30f,
                                rotationY = rotation.value,
                                rotationZ = if(pattern[card.id] == 0) 2f else -2f
                            )
                            .clickable(enabled = rotation.value % 180f == 0f) { viewModel.onClickCard(card) },
                    ) {
                        Column(
                            Modifier.fillMaxSize().background(Color.White),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (rotation.value >= 90f || card.isMatched) {
//                            Text("🔥" + card.imageId)
                                if(!card.beginCut){
                                Image(painter = painterResource(card.imageId), contentDescription = "", modifier = Modifier.padding(4.dp))
                                }else{
                                    Row(modifier = Modifier.fillMaxSize()) {
                                        Image(
                                            painter = painterResource(id = card.imageId),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .weight(1f)
                                                .graphicsLayer {
                                                    translationX = -offsetX.value.toPx()
                                                    rotationZ = -5f
                                                }
                                        )
                                        Image(
                                            painter = painterResource(id = card.imageId),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .weight(1f)
                                                .graphicsLayer {
                                                    translationX = offsetX.value.toPx()
                                                    rotationZ = 5f
                                                }
                                        )
                                    }
                                }


                            } else {
//                                Image(painter = painterResource(card.imageId), contentDescription = "", modifier = Modifier.padding(4.dp))

                            Box(Modifier
                                .fillMaxSize()
                                .background(brush = Brush.linearGradient(listOf(Color(0xffFFDB2E),Color(0xffF99D32)))))
                            }
                        }


                    }

                }
            }
            Spacer(Modifier.height(16.dp))
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(8.dp).background(if(viewModel.curentPlayer == 2) Color(0xfff24841  ) else Color(0xffD4D4D4), shape = MaterialTheme.shapes.medium)
            ) {
                Text(text = "Player 2" , modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = Color.Black)
                Text(text =  ""+viewModel.playerScore[2], modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = Color.Black)
            }

        }
    }


}

@Composable
fun OtakuFlipGameScreen(modifier: Modifier = Modifier) {
    val viewModel = FlipGameViewModel()
    FlipGame(viewModel = viewModel)

}