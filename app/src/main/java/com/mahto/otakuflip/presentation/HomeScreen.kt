package com.mahto.otakuflip.presentation

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mahto.otakuflip.R
import com.mahto.otakuflip.Screen
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.IconPatternBackground

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navHostController: NavHostController) {
    Box(
    ){
        IconPatternBackground(Color(0xff309664),R.drawable.o1)
        Box(
            Modifier.padding(16.dp).padding(vertical = 32.dp)
        ) {


            Text(
                "Otaku Flip",
                style = MaterialTheme.typography.bodyLarge.copy(fontFamily = mochiyPopOne),
                modifier = Modifier.align(
                    Alignment.TopEnd
                )
            )

            Column(
                modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xffF99D32)),
                    modifier = Modifier.padding(32.dp).fillMaxWidth().clickable {
                        navHostController.navigate(Screen.QuickModeScreen.route)
                    },
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(
                        2.dp,
                        Color.White
                    ),
                    elevation = CardDefaults.elevatedCardElevation(5.dp),
                ) {
                    Text(
                        "Ouick Match ",
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xffF99D32)),
                    modifier = Modifier.padding(32.dp).fillMaxWidth().clickable {
                        navHostController.navigate(Screen.OtakuFlipScreen.route)
                    },
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(
                        2.dp,
                        Color.White
                    ),
                    elevation = CardDefaults.elevatedCardElevation(5.dp),
//                    onClick = {navHostController.navigate(route = Screen.OtakuFlipScreen.route)}

                ) {
                    Text(
                        "2 Player",
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        style = MaterialTheme.typography.titleLarge,
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
                    .aspectRatio(1f)
                    .border(2.dp, Color.White, shape = CircleShape), onClick = {}) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "",)
            }
        }

    }

}