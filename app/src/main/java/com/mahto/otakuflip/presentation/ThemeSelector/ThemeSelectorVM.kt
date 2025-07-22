package com.mahto.otakuflip.presentation.ThemeSelector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.data.AnimeTheme
import com.mahto.otakuflip.data.GAMEMODE
import com.mahto.otakuflip.data.GridSize
import com.mahto.otakuflip.data.SettingsPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeSelectorVM @Inject constructor(
    private val repository: SettingsPreferenceRepository
) : ViewModel() {

    val animeTheme: StateFlow<AnimeTheme> = repository.animeTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = AnimeTheme.NARUTO_THEME
    )
    fun setAnimeTheme(themeName: AnimeTheme){
        viewModelScope.launch {
            repository.setAnimeTheme(themeName)

        }
    }

    val selectedMode: StateFlow<GAMEMODE> = repository.selectedMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GAMEMODE.EASY_MODE,
    )
    fun setGameMode(gamemode: GAMEMODE){
        viewModelScope.launch {
            repository.setGameMode(gamemode)
        }
    }

}