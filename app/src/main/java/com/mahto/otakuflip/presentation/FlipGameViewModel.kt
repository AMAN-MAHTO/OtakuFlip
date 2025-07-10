package com.mahto.otakuflip.presentation

import androidx.compose.material3.Card
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahto.otakuflip.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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


@HiltViewModel
class FlipGameViewModel @Inject constructor() : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards = _cards.asStateFlow()

    private val _currentPlayer = MutableStateFlow(1)
    val currentPlayer= _currentPlayer.asStateFlow()

    private val _playerScore = MutableStateFlow(mapOf(1 to 0, 2 to 0))
    val playerScore = _playerScore.asStateFlow()

    private val _matchedCardsCount = MutableStateFlow(0)
    val matchedCardsCount= _matchedCardsCount.asStateFlow()

    private var firstFlippedCard: Card? = null
    private var isCardMatching: Boolean = false

    val uniqueCards = 24

    init {
        startGame()
    }

    fun startGame() {
        val images = listOf(
            R.drawable.n1, R.drawable.n2, R.drawable.n4, R.drawable.n5,
            R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9,
            R.drawable.n10, R.drawable.n11, R.drawable.n12, R.drawable.n13,
            R.drawable.n15, R.drawable.n16, R.drawable.n17, R.drawable.n18,
            R.drawable.n19, R.drawable.n20, R.drawable.n21, R.drawable.n22,
            R.drawable.n23, R.drawable.n24, R.drawable.n25, R.drawable.n26,
            R.drawable.n27, R.drawable.n28, R.drawable.n29, R.drawable.n30,
            R.drawable.n31, R.drawable.n32, R.drawable.n33, R.drawable.n34,
        )

        val randomImages = images.shuffled().take(uniqueCards)
        val cards = (randomImages + randomImages).shuffled().mapIndexed { index, image ->
            Card(id = index, imageId = image)
        }

        _cards.value = cards
        _currentPlayer.value = 1
        _playerScore.value = mapOf(1 to 0, 2 to 0)
        _matchedCardsCount.value = 0
        firstFlippedCard = null
        isCardMatching = false
    }

    fun onClickCard(card: Card) {
        if (isCardMatching || card.isMatched || card.isFlipped) return

        val updatedList = _cards.value.toMutableList()
        val index = updatedList.indexOfFirst { it.id == card.id }
        updatedList[index] = updatedList[index].copy(isFlipped = true)
        _cards.value = updatedList

        if (firstFlippedCard == null) {
            firstFlippedCard = updatedList[index]
        } else {
            isCardMatching = true
            val secondCard = updatedList[index]

            viewModelScope.launch {
                delay(1000)

                if (firstFlippedCard!!.imageId == secondCard.imageId) {
                    matchingCards(firstFlippedCard!!, secondCard)
                    updateScore(_currentPlayer.value)
                } else {
                    unMatchedCards(firstFlippedCard!!, secondCard)
                    switchPlayer()
                }

                firstFlippedCard = null
                isCardMatching = false
            }
        }
    }

    private fun matchingCards(card1: Card, card2: Card) {
        val list = _cards.value.toMutableList()
        val idx1 = list.indexOfFirst { it.id == card1.id }
        val idx2 = list.indexOfFirst { it.id == card2.id }

        list[idx1] = list[idx1].copy(isMatched = true, beginCut = true)
        list[idx2] = list[idx2].copy(isMatched = true, beginCut = true)
        _cards.value = list

        viewModelScope.launch {
            delay(400)
            val list2 = _cards.value.toMutableList()
            list2[idx1] = list2[idx1].copy(isGone = true)
            list2[idx2] = list2[idx2].copy(isGone = true)
            _cards.value = list2
        }

        _matchedCardsCount.value += 1
    }

    private fun unMatchedCards(card1: Card, card2: Card) {
        val list = _cards.value.toMutableList()
        val idx1 = list.indexOfFirst { it.id == card1.id }
        val idx2 = list.indexOfFirst { it.id == card2.id }

        list[idx1] = list[idx1].copy(isFlipped = false)
        list[idx2] = list[idx2].copy(isFlipped = false)
        _cards.value = list
    }

    private fun switchPlayer() {
        _currentPlayer.value = if (_currentPlayer.value == 1) 2 else 1
    }

    private fun updateScore(player: Int) {
        val score = _playerScore.value.toMutableMap()
        score[player] = (score[player] ?: 0) + 10
        _playerScore.value = score
    }
}
