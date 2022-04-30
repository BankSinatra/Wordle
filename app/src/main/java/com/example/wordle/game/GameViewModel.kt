package com.example.wordle.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
const val NUMBER_OF_GUESSES = 6



class GameViewModel(): ViewModel(){
    private val _currentWord = MutableLiveData<String>()
    val currentWord : LiveData<String> = _currentWord

    private val _userGuessCount = MutableLiveData<Int>(0)
    val userGuessCount : LiveData<Int> = _userGuessCount

    private fun generateWord(){
        val word = allWordsList.random()
    }

    private fun resetGame(){
        generateWord()
        _userGuessCount.value = 0
    }

    private fun textValidation(char: Char): Boolean{
        val pattern = Regex("[a-zA-Z]") //If the character input is a letter
        return pattern.containsMatchIn(char.toString())
    }

    //TODO: Setup functions for deciding which letters are green, yellow and grey
}