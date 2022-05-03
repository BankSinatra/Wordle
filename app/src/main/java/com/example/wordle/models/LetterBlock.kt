package com.example.wordle.models

class LetterBlock(){
    private lateinit var _state: LetterState
    private var _letter = ""
    fun changeState(newState: LetterState){
        if (_state == LetterState.UNSUBMITTED) {
            _state = newState
        }
    }
    fun changeLetter(letter: Char){
        _letter = letter.toString()
    }
    init {
        changeState(LetterState.UNSUBMITTED)
    }
}

enum class LetterState(){
    UNSUBMITTED,
    YELLOW,
    GREEN,
    WRONG
}