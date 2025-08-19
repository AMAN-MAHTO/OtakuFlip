package com.mahto.otakuflip.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.R
import com.mahto.otakuflip.data.AnimeTheme
import com.mahto.otakuflip.data.GAMEMODE
import com.mahto.otakuflip.presentation.ThemeSelector.AnimeThemeCardData
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.AnimatedIconButton

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahto.otakuflip.utils.ANimatedCardButton
import com.mahto.otakuflip.viewmodels.ThemeSelectorVM
import com.mahto.otakuflip.utils.GameModeBar

private val defaultShadow = Shadow(
    color = Color.Black.copy(alpha = 0.30f),
    offset = Offset(0f, 5f),
    blurRadius = 5f
)

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    textAlign: TextAlign = TextAlign.Center,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = style.copy(
            fontFamily = mochiyPopOne,
            shadow = defaultShadow,
        )
    )
}

@Preview
@Composable
fun HomeScreen2(
    modifier: Modifier = Modifier,
    viewModel: ThemeSelectorVM = hiltViewModel(),
    onClickSettingIcon: () -> Unit = {},
    onClick2Player: () -> Unit = {},
    onClickQuickMatch: () -> Unit = {},
) {
    val animeTheme = viewModel.animeTheme.collectAsState().value
    val selectedMode = viewModel.selectedMode.collectAsState().value

    val listAnimeTheme = listOf(
        AnimeThemeCardData(R.drawable.n28, Color(0xffFF9C9C), AnimeTheme.NARUTO_THEME),
        AnimeThemeCardData(R.drawable.op0, Color(0xff9CB0FF), AnimeTheme.ONE_PIECE_THEME),
        AnimeThemeCardData(R.drawable.ds1, Color(0xff9CFFAB), AnimeTheme.DEMON_SLAYER_THEME),
    )

    val listGameMode = listOf(GAMEMODE.EASY_MODE, GAMEMODE.MEDIUM_MODE, GAMEMODE.HARD_MODE)


    Box(Modifier.fillMaxSize()) {
        Crossfade(
            targetState = animeTheme.bgImgFull,
            animationSpec = tween(100)
        ) {
            Image(painter = painterResource(it), contentDescription = "bg", contentScale = ContentScale.FillBounds)

        }
        Box(
            Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .fillMaxSize()
        ) {
            Row(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedIconButton(
                    icon = Icons.Default.Settings,
                    backgroundColor = Color(0xFFf99d32),
                    borderColor = Color.White,
                    shape = RoundedCornerShape(50),
                    onClick = { onClickSettingIcon() }
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    AppText(
                        text = "Otaku Flip",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(Modifier.height(4.dp))
                    AppText(
                        text = "Uncover. Remember. Win!",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Column(
                Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemsIndexed(listAnimeTheme) { index, item ->
                        val animatedSize by animateDpAsState(
                            targetValue = if(animeTheme == item.animeTheme) 80.dp else 70.dp,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                        )

                        Column (
                        ){
                            Card(
                                border =
                                    BorderStroke(
                                        2.dp,
                                        Color(0xFFFAFAFA)
                                    ),
                                shape = MaterialTheme.shapes.medium,
                                elevation = CardDefaults.elevatedCardElevation(5.dp),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(animatedSize)
                                    .aspectRatio(0.95f)
                                    .graphicsLayer(
                                        rotationZ = if (index % 2 == 0) 2f else -2f,

//                                        scaleX = if (animeTheme == item.animeTheme) shineOffset else 1f,
//                                        scaleY =  if (animeTheme == item.animeTheme) shineOffset else 1f,
                                    )
                                    .shadow(
                                        elevation = if (animeTheme == item.animeTheme) 8.dp else 4.dp,
                                        shape = MaterialTheme.shapes.medium,
                                        clip = false
                                    )
                                    .clickable {
                                        viewModel.setAnimeTheme(item.animeTheme)
                                    },
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxSize()
                                        .background(item.bgColor),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(item.imageId),
                                        contentDescription = "",
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }

                        }

                    }
                }

                Spacer(Modifier.height(48.dp))

                GameModeBar(modifier = Modifier.padding(horizontal = 32.dp), currentMode =  selectedMode, onModeChange = {viewModel.setGameMode(it)})
                Spacer(Modifier.height(8.dp))

                AppText(
                    text = "HighScore: ${viewModel._highScore.collectAsState().value}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
                )

                Spacer(Modifier.height(32.dp))
                Column {
                    Spacer(Modifier.height(8.dp))
                    ANimatedCardButton (
                        onClick = onClickQuickMatch,
                        pressedScale = 0.95f
                    ) {
                        AppText(
                            "Quick Match",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp).fillMaxWidth()
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    ANimatedCardButton(onClick = onClick2Player, pressedScale = 0.95f) {
                        AppText(
                            "Friend",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp).fillMaxWidth()
                        )
                    }

                }
            }
        }
    }
}