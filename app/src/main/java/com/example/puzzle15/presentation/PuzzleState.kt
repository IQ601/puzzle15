package com.example.puzzle15.presentation

data class PuzzleState(
    val board: Array<MutableList<Int>> = arrayOf(
        mutableListOf<Int>(1, 2, 3, 4),
        mutableListOf<Int>(5, 6, 7, 8),
        mutableListOf<Int>(9, 10, 11, 12),
        mutableListOf<Int>(13, 14, 15, 0)
    ),
    val firstSelection: Pair<Int, Int>? = null,
    val moves: Int = 0,
    val isSolved: Boolean = false
) {
    fun isTileClickable(row: Int, col: Int): Boolean {
        if (firstSelection != null) {
            return board[row][col] == 0
        }
        if (board[row][col] == 0) return false
        val isNextToEmpty =
            (row > 0 && board[row - 1][col] == 0) ||
                    (row < 3 && board[row + 1][col] == 0) ||
                    (col > 0 && board[row][col - 1] == 0) ||
                    (col < 3 && board[row][col + 1] == 0)

        return isNextToEmpty
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as PuzzleState
        if (moves != other.moves) return false
        if (isSolved != other.isSolved) return false
        // Use contentDeepEquals for 2D arrays
        if (!board.contentDeepEquals(other.board)) return false
        if (firstSelection != other.firstSelection) return false
        return true
    }

    override fun hashCode(): Int {
        var result = moves
        result = 31 * result + isSolved.hashCode()
        result = 31 * result + board.contentDeepHashCode()
        result = 31 * result + (firstSelection?.hashCode() ?: 0)
        return result
    }
}



