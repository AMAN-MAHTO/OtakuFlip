package com.mahto.otakuflip.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
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
import com.mahto.otakuflip.presentation.home.AppText
import com.mahto.otakuflip.ui.theme.CustomRed
import com.mahto.otakuflip.ui.theme.mochiyPopOne
import com.mahto.otakuflip.utils.AnimatedIconButton


@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    enableBackButton: Boolean = false
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (enableBackButton) {
            AnimatedIconButton(
                icon = Icons.Default.Close,
                size = 42.dp,
                backgroundColor = CustomRed,
                borderColor = Color.White,
                shape = RoundedCornerShape(50),
                onClick = { onClickBack() }
            )
        } else {
            Spacer(Modifier.width(1.dp))
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            AppText(
                text = "Otaku Flip",
                style = MaterialTheme.typography.headlineSmall
            )
//            Spacer(Modifier.height(4.dp))
//            AppText(
//                text = "Uncover. Remember. Win!",
//                style = MaterialTheme.typography.bodySmall
//            )
        }


    }
}