package com.example.wordle.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
const val NUMBER_OF_GUESSES = 6



class GameViewModel(): ViewModel(){
    private val _currentWord = MutableLiveData<String>()
    val currentWord : LiveData<String> = _currentWord

    private val _userGuesses = MutableLiveData<Int>(0)
    val userGuesses : LiveData<Int> = _userGuesses

    private fun generateWord(){
        val word = allWordsList.random()
    }

    private fun resetGame(){
        generateWord()
        _userGuesses.value = 0
    }


    //TODO: Function that determines which editTexts are open for editing

    //TODO: Setup Text Validation functions (Letters ONLY)

    //TODO: Setup functions for deciding which letters are green, yellow and grey

    //TODO: Setup
}