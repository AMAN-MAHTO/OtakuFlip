package com.mahto.otakuflip.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.R
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
        started = SharingStarted.WhileSubscribed(),
        initialValue = AnimeTheme.NARUTO_THEME
    )
     val gameMode: StateFlow<GAMEMODE> = repository.selectedMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = GAMEMODE.EASY_MODE
    )

    val _highScore = gameMode
        .filterNotNull() // Optional if gameMode could be null
        .distinctUntilChanged()
        .flatMapLatest { mode ->
            repository.highScore(mode)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0 // fallback if highScore not yet loaded
        )

    private val _state = MutableStateFlow(FlipCardsState())
    val state: StateFlow<FlipCardsState> = _state.asStateFlow()
    private val _numberOfPlayers = mutableStateOf(1)
    fun numberOfPlayer(players: Int){
        _numberOfPlayers.value = players
    }

    private val firstCLick = mutableStateOf(false)

    private val _timeTaken= mutableStateOf(0)
    val timeTaken = _timeTaken

    fun onGameEnd(){
        _timeTaken.value = _state.value.timeElapsed
        timerJob?.cancel()
        restartTimer()
        firstCLick.value = false
        updateHighScore()


    }
    fun updateHighScore(){
        if(_numberOfPlayers.value == 1){
            state.value.playerScore[1]?.let {
                if(it > _highScore.value){
                    viewModelScope.launch {
                        repository.setHighScore(gameMode.value, it)
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
                _state.update { it.copy(timeElapsed = it.timeElapsed+1)  }
            }
        }
    }
    fun restartTimer(){
        _state.update { it.copy(timeElapsed  = 0) }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    fun startGame() {
        val images = _animeTheme.value.images

        val randomImages = images.shuffled().take(gameMode.value.gridSize.uniqueCardsNumber)
        val cards = (randomImages + randomImages).shuffled().mapIndexed { index, image ->
            Card(id = index, imageId = image)
        }

        _state.update {
            it.copy(
                cards = cards.toMutableList(),
                currentPlayer = 1,
                playerScore = mapOf(1 to 0, 2 to 0),
                matchedCards = 0,
                firstFlippedCard = null,
                isCardMatching = false,
                uniqueCards = gameMode.value.gridSize.uniqueCardsNumber
            )
        }
    }

    fun onClickCard(card: Card) {
        val currentState = _state.value
        if(!firstCLick.value ){
            startTimer()
            firstCLick.value = true
        }

        if (currentState.isCardMatching || card.isMatched || card.isFlipped) return

        val updatedCards = currentState.cards.toMutableList()
        val index = updatedCards.indexOfFirst { it.id == card.id }
        updatedCards[index] = updatedCards[index].copy(isFlipped = true)

        _state.update {
            it.copy(cards = updatedCards)
        }

        val firstCard = _state.value.firstFlippedCard
        if (firstCard == null) {
            _state.update {
                it.copy(firstFlippedCard = updatedCards[index])
            }
        } else {
            _state.update { it.copy(isCardMatching = true) }

            val secondCard = updatedCards[index]

            viewModelScope.launch {
                delay(1000)

                if (firstCard.imageId == secondCard.imageId) {
                    matchingCards(firstCard, secondCard)
                    updateScore(_state.value.currentPlayer)
                } else {
                    unMatchedCards(firstCard, secondCard)
                    switchPlayer()
                }

                _state.update {
                    it.copy(firstFlippedCard = null, isCardMatching = false)
                }
            }
        }
    }

    fun onClickHome(){

    }
    fun onClickShop(){

    }

    private fun matchingCards(card1: Card, card2: Card) {
        val updatedCards = _state.value.cards.toMutableList()
        val idx1 = updatedCards.indexOfFirst { it.id == card1.id }
        val idx2 = updatedCards.indexOfFirst { it.id == card2.id }

        updatedCards[idx1] = updatedCards[idx1].copy(isMatched = true, beginCut = true)
        updatedCards[idx2] = updatedCards[idx2].copy(isMatched = true, beginCut = true)

        _state.update {
            it.copy(cards = updatedCards)
        }

        viewModelScope.launch {
            delay(400)

            val cutCards = _state.value.cards.toMutableList()
            cutCards[idx1] = cutCards[idx1].copy(isGone = true)
            cutCards[idx2] = cutCards[idx2].copy(isGone = true)

            _state.update {
                it.copy(cards = cutCards, matchedCards = it.matchedCards + 1)
            }
        }
    }

    private fun unMatchedCards(card1: Card, card2: Card) {
        _combo.value = 1
        val updatedCards = _state.value.cards.toMutableList()
        val idx1 = updatedCards.indexOfFirst { it.id == card1.id }
        val idx2 = updatedCards.indexOfFirst { it.id == card2.id }

        updatedCards[idx1] = updatedCards[idx1].copy(isFlipped = false)
        updatedCards[idx2] = updatedCards[idx2].copy(isFlipped = false)

        _state.update {
            it.copy(cards = updatedCards)
        }
    }

    private fun switchPlayer() {
        if(_numberOfPlayers.value == 2){
            val currentPlayer = _state.value.currentPlayer
            _state.update {
                it.copy(currentPlayer = if (currentPlayer == 1) 2 else 1)
            }
        }

    }

    private fun updateScore(player: Int) {
        val newScoreMap = _state.value.playerScore.toMutableMap()
        newScoreMap[player] = (newScoreMap[player] ?: 0) + 10*_combo.value
        _combo.value = _combo.value + 1
        _state.update {
            it.copy(playerScore = newScoreMap)
        }
        updateHighScore()
    }
}

