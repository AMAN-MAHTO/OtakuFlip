package com.mahto.otakuflip.presentation.quickmatch

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.R
import com.mahto.otakuflip.ui.theme.mochiyPopOne
enum class MenuItem {
    HOME, PLAY, SHOP
}

data class MenuCardData(
    val iconId: Int,
    val bgColor: Color,
    val menuItem: MenuItem,
    val rotation: Float
)

@Composable
fun QuickMatchScoreScreen(
    modifier: Modifier,
    timeTaken: String,
    score: Int?,
    highScore: Int,
    onClickHome: () -> Unit,
    onCLickPlay: () -> Unit,
    onClickShop: () -> Unit,
    ) {
    val menuList = listOf(
        MenuCardData(R.drawable.home, Color(0xffFF2E2E), MenuItem.HOME, 2f),
        MenuCardData(R.drawable.play, Color(0xff25C247), MenuItem.PLAY, -2f),
        MenuCardData(R.drawable.shoping_bag, Color(0xffFFB62E), MenuItem.SHOP, 2f)
    )

    Column(
        modifier
            .padding(top = 60.dp)
            .fillMaxSize()
            ,
        verticalArrangement = Arrangement.SpaceBetween,
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
                    text = "Time Taken: ${timeTaken}"
                )
            }

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 4.dp)
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
                    text = "Score   "+score,
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
                    text = "High Score " + highScore
                )
            }
            Spacer(Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            var selectedMenu by remember { mutableStateOf(MenuItem.PLAY) }
            val selectedScale = 1.15f

            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(menuList) { index, item ->
                    var isPressed  by remember { mutableStateOf(false) }
                    val animatedScale by animateFloatAsState(
                        targetValue = if (isPressed) selectedScale else 1f,
                        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow),
                        label = "IconButtonPressAnim"
                    )
                    Box(Modifier.pointerInput(Unit){
                        detectTapGestures(
                            onPress = {
                                isPressed = true
                                tryAwaitRelease()
                                isPressed = false
                                selectedMenu = item.menuItem
                                when (item.menuItem) {
                                    MenuItem.HOME -> onClickHome()
                                    MenuItem.PLAY -> onCLickPlay()
                                    MenuItem.SHOP -> onClickShop()
                                }
                            }
                        )
                    }
//                        .graphicsLayer(
//                        scaleX = animatedScale,
//                        scaleY = animatedScale,
//                        transformOrigin = androidx.compose.ui.graphics.TransformOrigin.Center
//                    )
                    ){
                        Card(
                            border = BorderStroke(2.dp, Color.White),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.elevatedCardElevation(5.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .size(60.dp*animatedScale)
                                .aspectRatio(1f)
                                .graphicsLayer { rotationZ = item.rotation }
                                .shadow(
                                    elevation = if (selectedMenu == item.menuItem) 8.dp else 4.dp,
                                    shape = MaterialTheme.shapes.medium,
                                    clip = false
                                )
//                                .clickable {
//                                    selectedMenu = item.menuItem
//                                    when (item.menuItem) {
//                                        MenuItem.HOME -> onClickHome()
//                                        MenuItem.PLAY -> onCLickPlay()
//                                        MenuItem.SHOP -> onClickShop()
//                                    }
//                                }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(item.bgColor)
                            ) {
                                Icon(
                                    painter = painterResource(item.iconId),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(30.dp*animatedScale)
                                        .graphicsLayer { rotationZ = item.rotation },
                                    tint = Color.White
                                )
                            }
                        }
                    }

                }
            }

        }

    }

}