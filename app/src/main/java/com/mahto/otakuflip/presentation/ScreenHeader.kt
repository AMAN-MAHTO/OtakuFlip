package com.mahto.otakuflip.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mahto.otakuflip.ui.theme.mochiyPopOne


@Composable
fun ScreenHeader(modifier: Modifier = Modifier, onClickBack: ()-> Unit, enableBackButton: Boolean = false ) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (enableBackButton){
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .aspectRatio(1f)
                    .shadow(5.dp, CircleShape, false)
                    .border(2.dp, Color.White, CircleShape)
                    .background(
                        Color(0xffE4393C), CircleShape
                    )
                    .clickable {
                        onClickBack()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "X",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = mochiyPopOne,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.25f), offset =
                                Offset(0f, 5f), blurRadius = 3f
                        )
                    ),
                    fontSize = 16.sp
                )
            }
        }else{
            Spacer(Modifier.width(1.dp))
        }

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
}