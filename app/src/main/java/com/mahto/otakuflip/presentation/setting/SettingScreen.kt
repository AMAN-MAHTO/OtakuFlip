package com.mahto.otakuflip.presentation.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahto.otakuflip.presentation.ScreenHeader
import com.mahto.otakuflip.viewmodels.SettingViewModel


@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
    onClickBack: ()->Unit,
) {
    val isMuted = viewModel.isMuted.collectAsState().value
    val volume = viewModel.volume.collectAsState().value
    val animeTheme = viewModel.animeTheme.collectAsState().value

    Box(Modifier.fillMaxSize()){
        Image(painter = painterResource(animeTheme.bgImgFull), contentScale = ContentScale.Fit, contentDescription = "")
        Box(
            Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .padding(top = 20.dp)
                .fillMaxSize()
        ){
            ScreenHeader(
                modifier = Modifier.align(Alignment.TopCenter),
                onClickBack = onClickBack,
                enableBackButton = true
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // 🔇 Mute toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = if (isMuted) "Muted" else "Sound On")
                    FilledIconToggleButton(
                        checked = isMuted,
                        onCheckedChange = {
//                            viewModel.toggleMute()
                            viewModel.setIsMuted(!isMuted)
                        }
                    ) {
                        if (isMuted) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_lock_silent_mode),
                                contentDescription = "Muted"
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_lock_silent_mode_off),
                                contentDescription = "Sound On"
                            )
                        }
                    }
                }

//                 🔊 Volume slider
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Volume: ${(volume * 100).toInt()}%")
                    Slider(
                        value = volume,
                        onValueChange = { newValue ->
                            viewModel.setVolume(newValue)
                                        },
                        valueRange = 0f..1f,
                        steps = 9 // 10 steps (0.1 each)
                    )
                }
            }


        }
    }
}