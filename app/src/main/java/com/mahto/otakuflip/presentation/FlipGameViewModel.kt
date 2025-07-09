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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Card(
    val id: Int,
    val imageId: Int,
    val isFliped: Boolean = false,
    val isMatched: Boolean = false,
    val beginCut: Boolean = false,
    val isGone: Boolean = false,
)



class FlipGameViewModel : ViewModel() {

    private val _cards = mutableStateListOf<Card>()
    val cards = _cards

    var curentPlayer by mutableStateOf(1)
        private set

    var playerScore = mutableStateMapOf<Int,Int>(1 to 0, 2 to 0)
        private set

    private var firstFlippedCard: Card? = null

    private var isCardMatching: Boolean = false


    init {
        startGame()
    }

    fun startGame(){
        val images = listOf(
//            R.drawable.n1,
            R.drawable.n2,
//            R.drawable.n3,
            R.drawable.n4,
            R.drawable.n5,
            R.drawable.n6,
            R.drawable.n7,
            R.drawable.n8,
            R.drawable.n9,
            R.drawable.n10,
            R.drawable.n11,
            R.drawable.n12,
            R.drawable.n13,
//            R.drawable.n14,
            R.drawable.n15,
            R.drawable.n16,
            R.drawable.n17,
            R.drawable.n18,
            R.drawable.n19,
            R.drawable.n20,
        )
        val randomImages = images.shuffled().subList(0,12)
        val cards = (randomImages + randomImages).shuffled()

        _cards.clear()
        _cards.addAll(cards.mapIndexed { index, image->
            Card(index,image)
        })

        firstFlippedCard = null
        isCardMatching = false
        curentPlayer = 1
        playerScore[0] = 0
        playerScore[1] = 0

    }

    fun onClickCard(card: Card){
        if(isCardMatching || card.isMatched || card.isFliped) return

        val index = _cards.indexOfFirst { it.id == card.id }
        _cards[index] = _cards[index].copy(isFliped = true)


        if(firstFlippedCard == null){
            firstFlippedCard = _cards[index]
        }else{
            isCardMatching = true

            val secondCard = _cards[index]

            viewModelScope.launch {
                delay(1000)

                if(firstFlippedCard!!.imageId == secondCard.imageId){
                    matchingCards(firstFlippedCard!!, secondCard)
                    updateScore(curentPlayer)

                }else{
                    unMatchedCards(firstFlippedCard!!, secondCard)
                    switchPlayer()

                }
                firstFlippedCard = null
                isCardMatching = false

            }

        }

    }

    fun matchingCards(card1: Card, card2: Card){
        val index1 = _cards.indexOfFirst { it.id == card1.id }
        val index2 = _cards.indexOfFirst { it.id == card2.id }

        _cards[index1] = _cards[index1].copy(isMatched = true, beginCut = true)
        _cards[index2] = _cards[index2].copy(isMatched = true, beginCut = true)
        viewModelScope.launch {

            delay(700)
            _cards[index1] = _cards[index1].copy(isGone = true)
            _cards[index2] = _cards[index2].copy(isGone = true)
        }
    }

    fun unMatchedCards(card1: Card, card2: Card){
        val index1 = _cards.indexOfFirst { it.id == card1.id }
        val index2 = _cards.indexOfFirst { it.id == card2.id }

        _cards[index1] = _cards[index1].copy(isFliped = false)
        _cards[index2] = _cards[index2].copy(isFliped = false)
    }

    fun switchPlayer(){
        curentPlayer = if(curentPlayer == 1) 2 else 1
    }
    fun updateScore(player: Int){
        playerScore[player] = (playerScore[player]?:0) + 10;
    }



}