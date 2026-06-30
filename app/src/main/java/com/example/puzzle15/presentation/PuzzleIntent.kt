package com.example.puzzle15.presentation

sealed class PuzzleIntent {
    data class TileClicked(val x: Int, val y: Int) : PuzzleIntent()
    object ShuffleClicked : PuzzleIntent()
}
