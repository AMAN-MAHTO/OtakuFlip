package com.mahto.otakuflip.data

import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mahto.otakuflip.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

object SettingKeys {
    val ANIME_THEME = stringPreferencesKey("anime_theme")
    val GRID_SIZE = intPreferencesKey("grid_size")
    val HIGH_SCORE = intPreferencesKey("high_score")

    val SELECTED_MODE = stringPreferencesKey("selected_mode")

    fun gridSizeKey(mode: GAMEMODE) = intPreferencesKey(mode.name + "_grid_size")
    fun highScoreKey(mode: GAMEMODE) = intPreferencesKey(mode.name + "_high_score")

}

sealed class AnimeTheme(
    val name: String,
    val images: List<Int>,
    val bgColor: Color = Color(0xff3D5AC0),
    val bgImg: Int = R.drawable.akatsuki_logo,
    val bgImgFull:Int = R.drawable.bgopf
) {
    object NARUTO_THEME : AnimeTheme(
        "naruto_theme", listOf(
            R.drawable.n1, R.drawable.n2, R.drawable.n4, R.drawable.n5,
            R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9,
            R.drawable.n10, R.drawable.n11, R.drawable.n12, R.drawable.n13,
            R.drawable.n15, R.drawable.n16, R.drawable.n17, R.drawable.n18,
            R.drawable.n19, R.drawable.n20, R.drawable.n21, R.drawable.n22,
            R.drawable.n23, R.drawable.n24, R.drawable.n25, R.drawable.n26,
            R.drawable.n27, R.drawable.n28, R.drawable.n29, R.drawable.n30,
            R.drawable.n31, R.drawable.n32, R.drawable.n33, R.drawable.n34,
            R.drawable.ds1, R.drawable.op1, R.drawable.op0, R.drawable.w1
        ),
        bgImgFull = R.drawable.bgnf
    )

    object ONE_PIECE_THEME : AnimeTheme(
        "one_piece_theme", listOf(
            R.drawable.op0, R.drawable.op3, R.drawable.op4, R.drawable.op5,
            R.drawable.op6, R.drawable.op7, R.drawable.op8, R.drawable.op9, R.drawable.op10,
            R.drawable.op11, R.drawable.op12, R.drawable.op13, R.drawable.op14, R.drawable.op15,
            R.drawable.op16, R.drawable.op17, R.drawable.op18, R.drawable.op19, R.drawable.op20,
            R.drawable.op21, R.drawable.op22, R.drawable.op23, R.drawable.op24, R.drawable.op25,
            R.drawable.op26, R.drawable.op27, R.drawable.op28, R.drawable.op29, R.drawable.op30,
            R.drawable.op31, R.drawable.op32, R.drawable.op33, R.drawable.op34, R.drawable.op35,
            R.drawable.op36, R.drawable.op37, R.drawable.op38, R.drawable.op39, R.drawable.op40,
            R.drawable.op41, R.drawable.op42, R.drawable.op43, R.drawable.op44, R.drawable.op45,
            R.drawable.op46, R.drawable.op47, R.drawable.op48, R.drawable.op49, R.drawable.op50,
            R.drawable.op51, R.drawable.op52, R.drawable.op53, R.drawable.op54, R.drawable.op55,
            R.drawable.op56, R.drawable.op57, R.drawable.op58, R.drawable.op59, R.drawable.op60,
            R.drawable.op61,
        ),
        bgImgFull = R.drawable.opbgf3
    )

    object WIFU_THEME : AnimeTheme(
        "wifu_theme", listOf(
            R.drawable.n1, R.drawable.n2, R.drawable.n4, R.drawable.n5,
            R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9,
            R.drawable.n10, R.drawable.n11, R.drawable.n12, R.drawable.n13,
            R.drawable.n15, R.drawable.n16, R.drawable.n17, R.drawable.n18,
            R.drawable.n19, R.drawable.n20, R.drawable.n21, R.drawable.n22,
            R.drawable.n23, R.drawable.n24, R.drawable.n25, R.drawable.n26,
            R.drawable.n27, R.drawable.n28, R.drawable.n29, R.drawable.n30,
            R.drawable.n31, R.drawable.n32, R.drawable.n33, R.drawable.n34,
            R.drawable.ds1, R.drawable.op1, R.drawable.op0, R.drawable.w1
        ),
        bgImgFull = R.drawable.bgnf
    )

    object DEMON_SLAYER_THEME : AnimeTheme(
        "demon_slayer_theme",
        listOf(
            R.drawable.ds1, R.drawable.ds2, R.drawable.ds3, R.drawable.ds4,
            R.drawable.ds5, R.drawable.ds6, R.drawable.ds7, R.drawable.ds8,
            R.drawable.ds9, R.drawable.ds10, R.drawable.ds11, R.drawable.ds12,
            R.drawable.ds13, R.drawable.ds14, R.drawable.ds15, R.drawable.ds16,
            R.drawable.ds17, R.drawable.ds18, R.drawable.ds19, R.drawable.ds20,
            R.drawable.ds21, R.drawable.ds22, R.drawable.ds23, R.drawable.ds24,
            R.drawable.ds25, R.drawable.ds26, R.drawable.ds27, R.drawable.ds28,
            R.drawable.ds29, R.drawable.ds30,
        ),
        Color(0xff28AF74),
        R.drawable.bgds,
        R.drawable.bgdsf
    )
    companion object{
        fun fromName(name: String) = when(name){
            "naruto_theme"-> AnimeTheme.NARUTO_THEME
            "one_piece_theme"-> AnimeTheme.ONE_PIECE_THEME
            "demon_slayer_theme"-> AnimeTheme.DEMON_SLAYER_THEME
            "wifu_theme"-> AnimeTheme.WIFU_THEME
            else -> AnimeTheme.NARUTO_THEME
        }
    }
}

sealed class GridSize(val uniqueCardsNumber: Int, val column: Int) {
    object SMALL : GridSize(12, column = 4)
    object MEDIUM : GridSize(24, column = 6)
    object LARGE : GridSize(30, column = 6)
}

sealed class GAMEMODE(val name: String, val gridSize: GridSize, val highScore: Int) {
    object EASY_MODE : GAMEMODE(name = "easy", gridSize = GridSize.SMALL, highScore = 0)
    object MEDIUM_MODE : GAMEMODE(name = "medium", gridSize = GridSize.MEDIUM, highScore = 0)
    object HARD_MODE : GAMEMODE(name = "hard", gridSize = GridSize.LARGE, highScore = 0)

    companion object {
        fun fromName(name: String) = when (name) {
            "easy" -> GAMEMODE.EASY_MODE
            "medium" -> GAMEMODE.MEDIUM_MODE
            "hard" -> GAMEMODE.HARD_MODE
            else -> GAMEMODE.EASY_MODE
        }
    }

}


@Singleton
class SettingsPreferenceRepository
@Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val animeTheme: Flow<AnimeTheme> = dataStore.data
        .map { val name = it[SettingKeys.ANIME_THEME] ?: AnimeTheme.NARUTO_THEME.name
            AnimeTheme.fromName(name)
        }

    val selectedMode: Flow<GAMEMODE> = dataStore.data.map {
        val name = it[SettingKeys.SELECTED_MODE] ?: GAMEMODE.EASY_MODE.name
        GAMEMODE.fromName(name)
    }

    suspend fun setGameMode(gamemode: GAMEMODE) {
        dataStore.edit {
            it[SettingKeys.SELECTED_MODE] = gamemode.name
        }
    }

    fun highScore(mode: GAMEMODE): Flow<Int> = dataStore.data
        .map { it[SettingKeys.highScoreKey(mode)] ?: 0 }

    suspend fun setHighScore(mode: GAMEMODE, score: Int) {
        dataStore.edit { it[SettingKeys.highScoreKey(mode)] = score }
    }

    suspend fun setAnimeTheme(animeTheme: AnimeTheme) {
        dataStore.edit { it[SettingKeys.ANIME_THEME] = animeTheme.name }
    }

}