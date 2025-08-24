package com.mahto.otakuflip.data

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.R
import com.mahto.otakuflip.viewmodels.SettingViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SoundManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: SettingsPreferenceRepository

) {
    private var soundPool: SoundPool
    private val soundMap = mutableMapOf<String, Int>()
    private var isMuted: Boolean = false
    private var volume: Float = 1.0f
    private val scope = CoroutineScope(Dispatchers.IO)


    init {
        val audioAttribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttribute)
            .build()

        soundMap["flip"] = soundPool.load(context, R.raw.card_flip2, 1)
//        soundMap["click"] = soundPool.load(application, R.raw.button_click, 1)
//        soundMap["score"] = soundPool.load(application, R.raw.new_highscore, 1)

        repository.isMuted
            .onEach { isMuted = it }
            .launchIn(scope)

//        repository.volumeFlow
//            .onEach { volume = it }
//            .launchIn(scope)

    }

    fun playSound(name: String) {
        soundMap[name]?.let { soundId ->
            val v = if(isMuted) 0f else volume
            soundPool.play(soundId, v, v, 1, 0, 1f)
        }
    }
    fun toggleMute() {
        isMuted = !isMuted
    }

    fun setVolume(newVolume: Float) {
        volume = newVolume
    }

    fun release() {
        soundPool.release()
    }


}