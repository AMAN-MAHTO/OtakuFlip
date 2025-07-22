package com.mahto.otakuflip.presentation.ThemeSelector

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mahto.otakuflip.R
import com.mahto.otakuflip.data.AnimeTheme
import com.mahto.otakuflip.data.GAMEMODE
import com.mahto.otakuflip.data.GridSize
import com.mahto.otakuflip.presentation.ScreenHeader
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.AnimatedImageBackground

data class AnimeThemeCardData(
    val imageId: Int,
    val bgColor: Color,
    val animeTheme: AnimeTheme,
)

data class GridSizeData(
    val size: GridSize,
    val text: String,
)

@Composable
fun ThemeSelectorScreen(
    navHostController: NavHostController,
    viewModel: ThemeSelectorVM,
    onClickStartGame: () -> Unit,
    onClickBack: () -> Unit,
) {

    val animeTheme = viewModel.animeTheme.collectAsState().value
    val selectedMode = viewModel.selectedMode.collectAsState().value
    val listAnimeTheme = listOf(
        AnimeThemeCardData(R.drawable.n28, Color(0xffFF9C9C), AnimeTheme.NARUTO_THEME),
        AnimeThemeCardData(R.drawable.op0, Color(0xff9CB0FF), AnimeTheme.ONE_PIECE_THEME),
        AnimeThemeCardData(R.drawable.ds1, Color(0xff9CFFAB), AnimeTheme.DEMON_SLAYER_THEME),
        AnimeThemeCardData(R.drawable.w1, Color(0xffFFD19C), AnimeTheme.WIFU_THEME)
    )
    val listGameMode = listOf(GAMEMODE.EASY_MODE, GAMEMODE.MEDIUM_MODE, GAMEMODE.HARD_MODE)
    Box(

    ) {
//        IconPatternBackground(bgColor = Color(0xffCD5756), R.drawable.jjk0)
        AnimatedImageBackground(animeTheme.bgImgFull)
        Box(
            Modifier
                .padding(horizontal = 12.dp, vertical = 32.dp)
                .fillMaxSize()
        ) {
            ScreenHeader(
                modifier = Modifier.align(Alignment.TopCenter),
                onClickBack = onClickBack,
                enableBackButton = true
            )
            Column(
                Modifier
                    .padding(top = 60.dp)
                    .padding(vertical = 8.dp)
                    .fillMaxSize()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {


                    Spacer(Modifier.height(32.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Theme",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = mochiyPopOne,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 5f),
                                blurRadius = 3f
                            )
                        )
                    )
                    Spacer(Modifier.height(8.dp))
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
                    Spacer(Modifier.height(42.dp))
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Game Mode",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = mochiyPopOne,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f),
                                offset = Offset(0f, 5f),
                                blurRadius = 3f
                            )
                        )
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        itemsIndexed(listGameMode) { index, item ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedMode == item) Color(
                                        0xFF38B428
                                    ) else Color(0xffF99D32)
                                ),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .shadow(
                                        if (selectedMode == item) 10.dp else 5.dp,
                                        MaterialTheme.shapes.medium,
                                        false
                                    )
                                    .clickable {
                                        viewModel.setGameMode(item)
                                    },
                                border = BorderStroke(
                                    2.dp,
                                    if (selectedMode == item) Color(0xff7D84FF) else Color.White,
                                ),
                                elevation = CardDefaults.elevatedCardElevation(if (selectedMode == item) 10.dp else 5.dp),
                            ) {
                                Text(
                                    item.name.capitalize(),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontFamily = mochiyPopOne,
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.25f), offset =
                                                Offset(0f, 5f), blurRadius = 3f
                                        )
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }

                        }
                    }
                }
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12801C)),
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                        .shadow(10.dp, MaterialTheme.shapes.medium, false)
                        .clickable {
                            onClickStartGame()
                        },
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(
                        2.dp,
                        Color.White
                    ),
                    elevation = CardDefaults.elevatedCardElevation(5.dp),
                ) {
                    Text(
                        "Start Game",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = mochiyPopOne,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f), offset =
                                    Offset(0f, 5f), blurRadius = 3f
                            )
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}