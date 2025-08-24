package com.mahto.otakuflip.viewmodels

import android.app.Application
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.R
import com.mahto.otakuflip.data.AnimeTheme
import com.mahto.otakuflip.data.GAMEMODE
import com.mahto.otakuflip.data.SettingsPreferenceRepository
import com.mahto.otakuflip.data.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeSelectorVM @Inject constructor(
    private val soundManager: SoundManager,
    private val repository: SettingsPreferenceRepository
) : ViewModel() {

    val animeTheme: StateFlow<AnimeTheme> = repository.animeTheme.stateIn(
        scope = viewModelScope,
//        started = SharingStarted.Companion.WhileSubscribed(),
        initialValue = AnimeTheme.NARUTO_THEME,
        started = SharingStarted.Eagerly,
    )



    fun setAnimeTheme(themeName: AnimeTheme){
        soundManager.playSound("flip")

        viewModelScope.launch {
            repository.setAnimeTheme(themeName)

        }
    }

    val selectedMode: StateFlow<GAMEMODE> = repository.selectedMode.stateIn(
        scope = viewModelScope,
//        started = SharingStarted.Companion.WhileSubscribed(),
        initialValue = GAMEMODE.EASY_MODE,
        started = SharingStarted.Eagerly, // always active
    )

    val _highScore = selectedMode
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapLatest { mode ->
            repository.highScore(mode)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0 // fallback if highScore not yet loaded
        )
    fun setGameMode(gamemode: GAMEMODE){
        viewModelScope.launch {
            repository.setGameMode(gamemode)
        }
    }


}