package com.example.wordle.game

import androidx.lifecycle.ViewModel
import com.example.wordle.models.LetterBlock

const val NUMBER_OF_GUESSES = 6


class GameViewModel() : ViewModel() {
    private var _currentLetter: Int = 0
    private var _currentWord: Int = 0
    val currentLetter = _currentLetter
    val currentWord = _currentWord
    private lateinit var word: String

    init {
        resetGame()
        generateWord()
    }

    private fun generateWord() {
        word = allWordsList.random()
    }

    private fun resetGame() {
        generateWord()
        _currentLetter = 0
        _currentWord = 0
    }

    private fun textValidation(char: Char): Boolean {
        val pattern = Regex("[a-zA-Z]") //If the character input is a letter
        return pattern.containsMatchIn(char.toString())
    }


}