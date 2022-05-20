package com.example.wordle.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val NUMBER_OF_GUESSES = 6


class GameViewModel : ViewModel() {
    //This variable tracks how many times we have guessed
    private val _guessNumber = MutableLiveData<Int>(0)
    val guessNumber: LiveData<Int> = _guessNumber

    //tracks if the game is over
    private val _gameEndState = MutableLiveData(GameEndState())
    val gameEndState: LiveData<GameEndState> = _gameEndState

    //This variable keeps track of the word we are guessing
    val guess = MutableLiveData("")

    //We'll pick a word to guess later hold on
    private lateinit var word: String

    private fun generateWord() {
        word = allWordsList.random()
    }

    private fun resetGame() {
        generateWord()
        _guessNumber.value = 0
    }

    init {
        resetGame()
    }

    fun submitWord() {
        if (_guessNumber.value!! < NUMBER_OF_GUESSES - 1) {
            _guessNumber.value = _guessNumber.value?.plus(1)
            guess.value = ""
        }else{
            gameOver(false)
        }
    }

    fun evaluateWord(userGuess: String): MutableMap<Int, LetterState> {
        val colorMap = mutableMapOf(
            0 to LetterState.WRONG,
            1 to LetterState.WRONG,
            2 to LetterState.WRONG,
            3 to LetterState.WRONG,
            4 to LetterState.WRONG
        )
        for (index in word.indices) {
            if(userGuess == word) {
                gameOver(true)
            }
            if (userGuess[index] in word) {
                if (userGuess[index] == word[index]) {
                    colorMap[index] = LetterState.GREEN
                } else {
                    colorMap[index] = LetterState.YELLOW
                }
            }
        }
        return colorMap
    }

    private fun gameOver(win: Boolean){
        val gameState = _gameEndState.value
        val newGameState = gameState?.copy(gameEnded = true, gameWon = win)
        _gameEndState.value = newGameState!!
    }

    fun wordValidation(userGuess: String): Boolean {
        return userGuess in allWordsList
    }

    fun getWord(): String {
        return word
    }
}

enum class LetterState {
    YELLOW,
    GREEN,
    WRONG
}