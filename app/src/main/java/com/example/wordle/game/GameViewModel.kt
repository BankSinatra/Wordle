package com.example.wordle.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val NUMBER_OF_GUESSES = 6


class GameViewModel : ViewModel() {
    //This variable tracks how many times we have guessed
    private val _guessNumber = MutableLiveData<Int>()
    val guessNumber: LiveData<Int> = _guessNumber

    //This variable keeps track of the word we are guessing
    val guess = MutableLiveData<String>()

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

    private fun textValidation(char: Char): Boolean {
        val pattern = Regex("[a-zA-Z]") //If the character input is a letter
        return pattern.containsMatchIn(char.toString())
    }

    fun submitWord(){
        Log.i("ViewModel", "SubmitWord called")
    }

}