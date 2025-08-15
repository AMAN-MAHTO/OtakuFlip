package com.mahto.otakuflip.presentation.home

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahto.otakuflip.R
import com.mahto.otakuflip.Screen
import com.mahto.otakuflip.data.AnimeTheme
import com.mahto.otakuflip.data.GAMEMODE
import com.mahto.otakuflip.presentation.ThemeSelector.AnimeThemeCardData
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.AnimatedIconButton
import com.mahto.otakuflip.utils.IconPatternBackground

@Preview
@Composable
fun HomeScreen2(modifier: Modifier = Modifier) {
    val animeTheme = remember { mutableStateOf(AnimeTheme.NARUTO_THEME) }
    val selectedMode = remember { mutableStateOf(GAMEMODE.EASY_MODE) }

    val listAnimeTheme = listOf(
        AnimeThemeCardData(R.drawable.n28, Color(0xffFF9C9C), AnimeTheme.NARUTO_THEME),
        AnimeThemeCardData(R.drawable.op0, Color(0xff9CB0FF), AnimeTheme.ONE_PIECE_THEME),
        AnimeThemeCardData(R.drawable.ds1, Color(0xff9CFFAB), AnimeTheme.DEMON_SLAYER_THEME),
//        AnimeThemeCardData(R.drawable.w1, Color(0xffFFD19C), AnimeTheme.WIFU_THEME)
    )
    val setAnimeTheme: (AnimeTheme) -> Unit = {}
    val listGameMode = listOf(GAMEMODE.EASY_MODE, GAMEMODE.MEDIUM_MODE, GAMEMODE.HARD_MODE)
    Box(Modifier.fillMaxSize()) {
        Image(painter = painterResource(R.drawable.bgdsf), contentDescription = "bg")
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
                    shape = _root_ide_package_.androidx.compose.foundation.shape.CircleShape,
                    onClick = {
                    }
                )
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Otaku Flip",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge
                            .copy(
                                fontFamily = mochiyPopOne,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.25f), offset =
                                        Offset(0f, 5f), blurRadius = 3f
                                )
                            )
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Uncover. Remember. Win!",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                            .copy(
                                fontFamily = mochiyPopOne,
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.25f), offset =
                                        Offset(0f, 5f), blurRadius = 3f
                                )
                            )
                    )
                }


            }

            Column(
                Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                LazyRow(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemsIndexed(listAnimeTheme) { index, item ->
                        Card(
                            border = BorderStroke(
                                2.dp,
                                if (animeTheme == item.animeTheme) Color(0xff7D84FF) else Color.White
                            ),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.elevatedCardElevation(5.dp),
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (animeTheme == item.animeTheme) 70.dp else 60.dp)
                                .aspectRatio(0.9f)
                                .graphicsLayer(
                                    rotationZ = if (index % 2 == 0) 2f else -2f
                                )
                                .shadow(
                                    elevation = if (animeTheme == item.animeTheme) 8.dp else 4.dp,
                                    shape = MaterialTheme.shapes.medium,
                                    clip = false
                                )
                                .clickable(
                                ) {
                                    setAnimeTheme(item.animeTheme)
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

                Text(
                    selectedMode.value.name.toUpperCase(), color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                        .copy(
                            fontFamily = mochiyPopOne,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f), offset =
                                    Offset(0f, 5f), blurRadius = 3f
                            )
                        )
                )


            }
        }

    }
}