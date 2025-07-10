package com.mahto.otakuflip.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun IconPatternBackground(bgColor: Color, imageId: Int ) {

    val pattern1 = listOf(
        1,0,1,0,1,0,0,1,0,1,0,1,
        1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,
        1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,1,0,1,0,1,0,0,1,0,1,0,1,)
    LazyVerticalGrid(columns = GridCells.Fixed(6), modifier = Modifier.background(bgColor), userScrollEnabled = false) {
        items(pattern1) { pattern->
            if(pattern == 1){
                Image(modifier = Modifier
                    .fillMaxSize(0.5f)
                    .alpha(0.3f), painter = painterResource(imageId), contentDescription = "")
            }else{
                Image(modifier = Modifier
                    .fillMaxSize()
                    .alpha(0f), painter = painterResource(imageId), contentDescription = "", )
            }

        }
    }
}
