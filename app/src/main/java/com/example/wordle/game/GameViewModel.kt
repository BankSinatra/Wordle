package com.example.wordle.game

import androidx.lifecycle.ViewModel
import com.example.wordle.models.LetterBlock

const val NUMBER_OF_GUESSES = 6


class GameViewModel() : ViewModel() {
    private var _currentLetter: Int = 0
    private var _currentWord: Int = 0
    val currentLetter = _currentLetter
    val currentWord = _currentWord
    lateinit var word: String
    lateinit var grid: List<List<LetterBlock>>

    init {
        resetGame()
        generateWord()
        grid = generateGrid()
    }

    private fun generateGrid(): List<List<LetterBlock>> {
        val myList = mutableListOf(mutableListOf<LetterBlock>())
        for (word in 0..5) {
            myList.add(mutableListOf())
            for (letter in 0..4)
                myList[word].add(LetterBlock())
        }
        return myList.toList()
    }

    fun findFocus() {
        //TODO: This sets the focus for the right editText
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