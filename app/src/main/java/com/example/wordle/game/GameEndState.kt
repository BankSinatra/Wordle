package com.example.wordle.game

data class GameEndState(
    val gameEnded: Boolean = false,
    val gameWon: Boolean? = null
)
