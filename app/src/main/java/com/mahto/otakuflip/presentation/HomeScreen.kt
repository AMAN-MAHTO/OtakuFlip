package com.mahto.otakuflip.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mahto.otakuflip.R
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.AnimatedImageBackground

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navHostController: NavHostController,
               onClickSettingIcon:()->Unit,
               onClick2Player:()->Unit,
               onClickQuickMatch:()->Unit,
               ) {
    Box(
    ) {
//        IconPatternBackground(Color(0xFFFF99AE), R.drawable.op1)
        AnimatedImageBackground( R.drawable.bgopf)
//        var tick by remember { mutableStateOf(0) }

//        LaunchedEffect(Unit) {
//            while (true) {
//                delay(100) // 1 second delay
//                tick++       // trigger recomposition
//            }
//        }
//
//        // Every time tick changes, PatternBackground is recomposed
//        PatternBackground(tick)
        Box(
            Modifier
                .padding(16.dp)
                .padding(vertical = 32.dp)
        ) {


            Row(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .align(Alignment.TopEnd),
                horizontalArrangement = Arrangement.End
            ) {

                Text(
                    text = "Otaku Flip",
                    style = MaterialTheme.typography.bodyLarge
                        .copy(
                            fontFamily = mochiyPopOne,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.25f), offset =
                                    Offset(0f, 5f), blurRadius = 3f
                            )
                        )
                )
            }

            Column(
                modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xffF99D32)),
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                        .shadow(10.dp, MaterialTheme.shapes.medium, false)
                        .clickable{onClickQuickMatch()} ,
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(
                        2.dp,
                        Color.White
                    ),
                    elevation = CardDefaults.elevatedCardElevation(5.dp),
                ) {
                    Text(
                        "Ouick Match ",
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

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xffF99D32)),
                    modifier = Modifier
                        .padding(32.dp)
                        .fillMaxWidth()
                        .shadow(10.dp, MaterialTheme.shapes.medium, false)
                        .clickable{onClick2Player()} ,
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(
                        2.dp,
                        Color.White
                    ),

                    ) {
                    Text(
                        "2 Player",
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

            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xffF99D32)
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.BottomStart)
                    .size(48.dp)
                    .shadow(10.dp, MaterialTheme.shapes.medium, false)
                    .aspectRatio(1f)
                    .border(2.dp, Color.White, shape = CircleShape),
                onClick = onClickSettingIcon) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "")
            }
        }

    }

}