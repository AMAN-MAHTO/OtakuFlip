package com.mahto.otakuflip.utils

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.data.AnimeTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun IconPatternBackground(bgColor: Color, imageId: Int ) {
    val allList = (AnimeTheme.NARUTO_THEME.images + AnimeTheme.DEMON_SLAYER_THEME.images).shuffled()
    val backgroundColors = listOf(
        Color(0xFFE55824),
        Color(0xFF553AAC),
        Color(0xFFECB42A),
        Color(0xFFF1E7E4),
        Color(0xFF9335AF),
        Color(0xFFFCE739),
        Color(0xFF4EC1E5),
        Color(0xFFED63AC),
        Color(0xFF2B95D6),
        Color(0xFFEF4B4E),
        Color(0xFF5EB853),
        Color(0xFFFC9823),
        Color(0xFF875243),
        Color(0xFF3BCBAF),
        Color(0xFF1E4997)
    )
    val pattern2 = listOf(
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
        1,0,1,0,1,0,1,0,0,1,0,1,0,1,0,1,
    )
    val pattern1 = listOf(
        1,0,1,0,1,0,0,1,0,1,0,1,
        1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,
        1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,)
    LazyVerticalGrid(columns = GridCells.Fixed(6), modifier = Modifier.background(bgColor), userScrollEnabled = false) {
        items(pattern1) { pattern->
            val imageId = allList.random()
            if(pattern == 1){
                Image(modifier = Modifier
                    .size(50.dp)
                    .alpha(0.8f)
                    , painter = painterResource(imageId),
                    contentDescription = "", )
            }else{
                Image(modifier = Modifier
                    .fillMaxSize()
                    .alpha(0f), painter = painterResource(imageId),
                    contentDescription = "", )
            }

        }
    }
}

@Composable
fun PatternBackground(
     tick:Int
){
    val allList = (AnimeTheme.NARUTO_THEME.images + AnimeTheme.DEMON_SLAYER_THEME.images+ AnimeTheme.DEMON_SLAYER_THEME.images).shuffled()

    LaunchedEffect(tick) {
        allList.shuffled()
    }
    val backgroundColors = listOf(
        Color(0xFFF1E7E4),
        Color(0xFF5EB853),
    )

    LazyVerticalGrid(columns = GridCells.Fixed(6)) {
        items(allList) {
            val bg  = backgroundColors.random()

            Column(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .border(0.5.dp, Color.White)
                    .background(bg),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(bg == Color(0xFFF1E7E4))
                    Image(modifier = Modifier
                        .alpha(0.4f)
                        .padding(8.dp),
                        painter = painterResource(it),
                        contentDescription = "", contentScale = ContentScale.Fit)


            }


        }

    }
}
@Composable
fun AnimatedImageBackground(imageId: Int) {
    Crossfade(targetState = imageId, label = "Background Image Crossfade") { targetImage ->
        Image(
            painter = painterResource(id = targetImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
//    Image(modifier = Modifier.fillMaxSize(), painter = painterResource(imageId), contentDescription = "", contentScale = ContentScale.FillBounds)
}
@Composable
fun ImageBackground(imageId: Int) {
//    Crossfade(targetState = imageId, label = "Background Image Crossfade") { targetImage ->
//        Image(
//            painter = painterResource(id = targetImage),
//            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
//            contentScale = ContentScale.Crop
//        )
//    }
    Image(modifier = Modifier.fillMaxSize(), painter = painterResource(imageId), contentDescription = "", contentScale = ContentScale.FillBounds)
}

@Preview
@Composable
fun PatternBg2(modifier: Modifier = Modifier.fillMaxSize()) {
    val imageList = AnimeTheme.DEMON_SLAYER_THEME.images
    Column (
        Modifier.fillMaxSize()

    ) {
        RotatingImageRowWithWiggle(images = AnimeTheme.DEMON_SLAYER_THEME.images)
        RotatingImageRowWithWiggle(images = AnimeTheme.NARUTO_THEME.images)
        RotatingImageRowWithWiggle(images = AnimeTheme.DEMON_SLAYER_THEME.images)
        RotatingImageRowWithWiggle(images = AnimeTheme.NARUTO_THEME.images)

    }
}

@Composable
fun RotatingImageRowWithWiggle(
    images: List<Int>,
    imageSize: Dp = 50.dp,
    scrollSpeed: Float = 1000f // pixels per second
) {
    val listState = rememberLazyListState()

    // Duplicate images for smooth infinite scroll
    val loopedImages = remember { images + images}

    // Auto-scroll coroutine
    LaunchedEffect(Unit) {
        while (true) {
            val currentOffset = listState.firstVisibleItemScrollOffset
            val currentItem = listState.firstVisibleItemIndex

            val frameTimeMillis = 16
            val pixelsToScroll = (scrollSpeed * frameTimeMillis / 1000).toInt()

            listState.scrollToItem(currentItem, currentOffset + pixelsToScroll)

            if (currentItem > images.size) {
                listState.scrollToItem(0, 0)
            }

            delay(frameTimeMillis.toLong())
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.White)
            .height(imageSize*2)

    ) {
        items(loopedImages.size) { index ->
            WiggleImage(
                imageRes = loopedImages[index],
                size = imageSize,
                phaseOffset = index%2 == 0
            )
        }
    }
}

@Composable
fun WiggleImage(
    imageRes: Int,
    size: Dp,
    phaseOffset: Boolean
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wiggle")
    val angle by
    if(phaseOffset){
         infiniteTransition.animateFloat(
            initialValue = -5f,
            targetValue = 5f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 300, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "wiggleRotation"
        )
    }else{
        infiniteTransition.animateFloat(
            initialValue = 5f,
            targetValue = -5f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "wiggleRotation"
        )
    }


    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .aspectRatio(1f)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .graphicsLayer {
                this.rotationZ = angle
            }
    )
}

