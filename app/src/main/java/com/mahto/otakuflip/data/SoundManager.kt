package com.mahto.otakuflip.data

import android.app.Application
import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.R
import com.mahto.otakuflip.viewmodels.SettingViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
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
    private var animeTheme: AnimeTheme = AnimeTheme.NARUTO_THEME

    private var mediaPlayer: MediaPlayer? = null
    private var currentMusicId: Int? = null

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
            .onEach { isMuted = it
            updateMusicVolume()}
            .launchIn(scope)

        repository.volume
            .onEach { volume = it
            updateMusicVolume()
            }
            .launchIn(scope)
//
//        repository.animeTheme
//            .map { it.bgMusic }
//            .distinctUntilChanged()
//            .onEach { it->
//                playBackgroundMusic(it)
//            }
//            .launchIn(scope)

//        combine (
//            repository.isMuted,
//            repository.volume,
//            repository.animeTheme.map { it.bgMusic } // just musicId, not whole object
//        ) { muted, vol, musicId ->
//            Triple(muted, vol, musicId)
//        }
//            .distinctUntilChanged() // prevent duplicate Triple emissions
//            .onEach { (muted, vol, musicId) ->
//                isMuted = muted
//                volume = vol
//
//                // Update existing music volume
//                updateMusicVolume()
//
//                // Change music only if theme changed
//                if (currentMusicId != musicId) {
//                    Log.d("jjk", "playBackgroundMusic: bgMusic")
//
//                    playBackgroundMusic(musicId)
//                }
//            }
//            .launchIn(scope)


    }

    fun playSound(name: String) {
        soundMap[name]?.let { soundId ->
            val v = if (isMuted) 0f else volume
            soundPool.play(soundId, v, v, 1, 0, 1f)
        }
    }

    private fun updateMusicVolume() {
        mediaPlayer?.setVolume(
            if (isMuted) 0f else volume,
            if (isMuted) 0f else volume
        )
    }
    fun playBackgroundMusic(musicId: Int) {
        if(currentMusicId != null && musicId == currentMusicId)  {
            updateMusicVolume()
            return
        }
        currentMusicId = musicId

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, musicId)
            .apply {
                isLooping = true
                setVolume(if (isMuted) 0f else volume, if (isMuted) 0f else volume)
                start()
                    Log.d("jjk", "playBackgroundMusic: bgMusic")

            }


    }

    fun release() {
        soundPool.release()
        mediaPlayer?.release()
        mediaPlayer = null
        currentMusicId = null
    }


}