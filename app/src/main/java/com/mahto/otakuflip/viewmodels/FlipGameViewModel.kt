package com.mahto.otakuflip.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.data.AnimeTheme
import com.mahto.otakuflip.data.GAMEMODE
import com.mahto.otakuflip.data.GridSize
import com.mahto.otakuflip.data.SettingsPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Card(
    val id: Int,
    val imageId: Int,
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false,
    val beginCut: Boolean = false,
    val isGone: Boolean = false
)

data class FlipCardsState(
    val cards: MutableList<Card> = mutableListOf<Card>(),
    val currentPlayer: Int = 1,
    val playerScore: Map<Int, Int> = mapOf<Int, Int>(1 to 0, 2 to 0),
    val matchedCards: Int = 0,
    val firstFlippedCard: Card? = null,
    val isCardMatching: Boolean = false,
    val uniqueCards: Int = GridSize.MEDIUM.uniqueCardsNumber,
    val timeElapsed: Int = 0,
    )
@HiltViewModel
class FlipGameViewModel @Inject constructor(
    private val repository: SettingsPreferenceRepository
) : ViewModel() {
    private val _combo = mutableStateOf(1)
    val comobo = _combo
     val _animeTheme: StateFlow<AnimeTheme> = repository.animeTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AnimeTheme.NARUTO_THEME
    )

     val _gameMode: StateFlow<GAMEMODE> = repository.selectedMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = GAMEMODE.EASY_MODE
    )
//    private val _animeTheme = MutableStateFlow<AnimeTheme>(AnimeTheme.NARUTO_THEME)
//    val animeTheme = _animeTheme.asStateFlow()
//
//    private val _gameMode = MutableStateFlow<GAMEMODE>(GAMEMODE.EASY_MODE)
//    val gameMode = _gameMode.asStateFlow()

    private val _cards = MutableStateFlow<List<Card>>(listOf())
    val cards = _cards.asStateFlow()

    private val _cardsData = MutableStateFlow<List<Card>>(listOf())

    val _highScore = _gameMode
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


    private val _numberOfPlayers = mutableStateOf(1)
    fun numberOfPlayer(players: Int){
        _numberOfPlayers.value = players
    }


    private val _currentPlayer = MutableStateFlow(1)
    val currentPlayer = _currentPlayer.asStateFlow()

    private val _playerScore = MutableStateFlow(mapOf(1 to 0, 2 to 0))
    val playerScore = _playerScore.asStateFlow()

    private val _matchedCards = MutableStateFlow(0)
    val matchedCards = _matchedCards.asStateFlow()


    private val _uniqueCards = MutableStateFlow(GridSize.MEDIUM.uniqueCardsNumber)
    val uniqueCards = _uniqueCards.asStateFlow()


    private val _firstFlippedCard = MutableStateFlow<Card?>(null)
    val firstFlippedCard = _firstFlippedCard.asStateFlow()

    private val _isCardMatching = MutableStateFlow(false)
    val isCardMatching = _isCardMatching.asStateFlow()

    private val _timeElapsed = MutableStateFlow(0)
    val timeElapsed = _timeElapsed.asStateFlow()




    val _isloading = mutableStateOf(false)
    fun startGame() {
        _isloading.value =false
        firstCLick.value = false
        timerJob?.cancel()
        restartTimer()
        val images = _animeTheme.value.images
        val randomImages = images.shuffled().take(_gameMode.value.gridSize.uniqueCardsNumber)
        _cards.value = (randomImages + randomImages).shuffled().mapIndexed { index, image ->
            Card(id = index, imageId = image)
        }
        _playerScore.value = mapOf(1 to 0, 2 to 0)
        _matchedCards.value = 0
        _uniqueCards.value = _gameMode.value.gridSize.uniqueCardsNumber
        _isloading.value = true
    }

    private val firstCLick = mutableStateOf(false)

    private val _timeTaken= mutableStateOf(0)
    val timeTaken = _timeTaken

    fun onGameEnd(){
        _timeTaken.value = _timeElapsed.value
        timerJob?.cancel()
        restartTimer()
        firstCLick.value = false
        updateHighScore()
    }
    fun updateHighScore(){
        if(_numberOfPlayers.value == 1){
            _playerScore.value[1]?.let {
                if(it > _highScore.value){
                    viewModelScope.launch {
                        repository.setHighScore(_gameMode.value, it)
                    }

                }
            }
        }

    }

    var timerJob: Job? = null

    fun startTimer(){
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while(true){
                delay(1000)
                _timeElapsed.update { it+1  }
            }
        }
    }
    fun restartTimer(){
        _timeElapsed.update {  0 }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }


    fun onClickCard(card: Card) {
        if(!firstCLick.value ){
            startTimer()
            firstCLick.value = true
        }

        if (_isCardMatching.value || card.isMatched || card.isFlipped) return

        val updatedCards = _cards.value.toMutableList()
        val index = updatedCards.indexOfFirst { it.id == card.id }
        updatedCards[index] = updatedCards[index].copy(isFlipped = true)

        _cards.update {
            updatedCards
        }

        val firstCard = _firstFlippedCard.value
        if (firstCard == null) {
            _firstFlippedCard.update {
                updatedCards[index]
            }
        } else {
            _isCardMatching.update { true }

            val secondCard = updatedCards[index]

            viewModelScope.launch {
                delay(1000)

                if (firstCard.imageId == secondCard.imageId) {
                    matchingCards(firstCard, secondCard)
                    updateScore(_currentPlayer.value)
                } else {
                    unMatchedCards(firstCard, secondCard)
                    switchPlayer()
                }

                _isCardMatching.update {
                    false
                }
                _firstFlippedCard.update {
                    null
                }
            }
        }
    }

    fun onClickHome(){

    }
    fun onClickShop(){

    }

    private fun matchingCards(card1: Card, card2: Card) {
        val updatedCards = _cards.value.toMutableList()
        val idx1 = updatedCards.indexOfFirst { it.id == card1.id }
        val idx2 = updatedCards.indexOfFirst { it.id == card2.id }

        updatedCards[idx1] = updatedCards[idx1].copy(isMatched = true, beginCut = true)
        updatedCards[idx2] = updatedCards[idx2].copy(isMatched = true, beginCut = true)

        _cards.update {
            updatedCards
        }

        viewModelScope.launch {
            delay(400)

            val cutCards = _cards.value.toMutableList()
            cutCards[idx1] = cutCards[idx1].copy(isGone = true)
            cutCards[idx2] = cutCards[idx2].copy(isGone = true)

            _cards.update {
               cutCards
            }
            _matchedCards.update {
                it + 1
            }
        }
    }

    private fun unMatchedCards(card1: Card, card2: Card) {
        _combo.value = 1
        val updatedCards = _cards.value.toMutableList()
        val idx1 = updatedCards.indexOfFirst { it.id == card1.id }
        val idx2 = updatedCards.indexOfFirst { it.id == card2.id }

        updatedCards[idx1] = updatedCards[idx1].copy(isFlipped = false)
        updatedCards[idx2] = updatedCards[idx2].copy(isFlipped = false)

        _cards.update {
            updatedCards
        }
    }

    private fun switchPlayer() {
        if(_numberOfPlayers.value == 2){
            val currentPlayer = _currentPlayer.value
            _currentPlayer.update {
                if (currentPlayer == 1) 2 else 1
            }
        }

    }

    private fun updateScore(player: Int) {
        val newScoreMap = _playerScore.value.toMutableMap()
        newScoreMap[player] = (newScoreMap[player] ?: 0) + 10*_combo.value
        _combo.value = _combo.value + 1
        _playerScore.update { newScoreMap
        }
        updateHighScore()
    }
}

