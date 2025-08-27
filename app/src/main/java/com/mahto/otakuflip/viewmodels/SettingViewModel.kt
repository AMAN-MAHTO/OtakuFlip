package com.mahto.otakuflip.viewmodels

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.data.AnimeTheme
import com.mahto.otakuflip.data.GAMEMODE
import com.mahto.otakuflip.data.SettingsPreferenceRepository
import com.mahto.otakuflip.data.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel@Inject constructor(
private val soundManager: SoundManager,
private val repository: SettingsPreferenceRepository
) : ViewModel() {
    val animeTheme: StateFlow<AnimeTheme> = repository.animeTheme.stateIn(
        scope = viewModelScope,
//        started = SharingStarted.Companion.WhileSubscribed(),
        initialValue = AnimeTheme.NARUTO_THEME,
        started = SharingStarted.Eagerly,
    )
    val selectedMode: StateFlow<GAMEMODE> = repository.selectedMode.stateIn(
        scope = viewModelScope,
        initialValue = GAMEMODE.EASY_MODE,
        started = SharingStarted.Eagerly, // always active
    )
    val isMuted: StateFlow<Boolean> = repository.isMuted.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.Eagerly, // always active
    )

    val volume: StateFlow<Float>  = repository.volume.stateIn(
        scope = viewModelScope,
        initialValue = 1f,
        started = SharingStarted.Eagerly,
    )

    fun setIsMuted(isMuted: Boolean){
        viewModelScope.launch {
            repository.setIsMuted(isMuted)
        }
    }

    fun setVolume(volume: Float){
        viewModelScope.launch {
            repository.setVolume(volume)
        }
    }

}