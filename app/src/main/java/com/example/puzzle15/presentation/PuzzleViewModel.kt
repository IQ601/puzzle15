package com.example.puzzle15.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PuzzleViewModel : ViewModel() {

    private val _state = MutableStateFlow(PuzzleState())
    val state: StateFlow<PuzzleState> = _state.asStateFlow()

    fun onIntent(intent: PuzzleIntent) {
        when (intent) {
            is PuzzleIntent.TileClicked -> onTileClicked(intent.y, intent.x)
            PuzzleIntent.ShuffleClicked -> onShuffleClicked()
        }
    }

    private fun onTileClicked(row: Int, col: Int) {
        val currentState = _state.value
        if (!currentState.isTileClickable(row, col)) return

        if (currentState.firstSelection == null) {
            // First click: Select the tile
            _state.value = currentState.copy(firstSelection = row to col)
        } else {
            // Second click: Swap with the empty spot
            val (selRow, selCol) = currentState.firstSelection
            
            // Create a deep copy of the board to trigger StateFlow update
            val newBoard = currentState.board.map { it.toMutableList() }.toTypedArray()
            
            // Swap
            val temp = newBoard[selRow][selCol]
            newBoard[selRow][selCol] = newBoard[row][col]
            newBoard[row][col] = temp
            
            val solved = checkSolved(newBoard)
            
            _state.value = currentState.copy(
                board = newBoard,
                firstSelection = null,
                moves = currentState.moves + 1,
                isSolved = solved
            )
        }
    }

    private fun onShuffleClicked() {
        val pieces = (0..15).toMutableList()
        pieces.shuffle()

        val newBoard = arrayOf(
            mutableListOf(0, 0, 0, 0),
            mutableListOf(0, 0, 0, 0),
            mutableListOf(0, 0, 0, 0),
            mutableListOf(0, 0, 0, 0)
        )

        for (i in 0..3) {
            for (j in 0..3) {
                val pieceIndex = i * 4 + j
                newBoard[i][j] = pieces[pieceIndex]
            }
        }

        _state.value = PuzzleState(
            board = newBoard,
            moves = 0,
            isSolved = checkSolved(newBoard)
        )
    }

    private fun checkSolved(board: Array<MutableList<Int>>): Boolean {
        for (i in 0..3) {
            for (j in 0..3) {
                val expected = if (i == 3 && j == 3) 0 else i * 4 + j + 1
                if (board[i][j] != expected) return false
            }
        }
        return true
    }
}
