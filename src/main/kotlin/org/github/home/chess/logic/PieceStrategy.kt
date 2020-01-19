package org.github.home.chess.logic

import org.github.home.chess.models.Empty
import org.github.home.chess.models.InputMove
import org.github.home.chess.models.Piece
import kotlin.math.abs

object PieceStrategy {

    private fun isNearCeil(input: InputMove): Boolean {
        if (abs(input.column2 - input.column1) < 2
            && abs(input.row2 - input.row1) < 2
        ) {
            return true
        }
        return false
    }

    private fun fromCeil(board: Array<Array<Piece>>, input: InputMove): Piece {
        return board[input.row1][input.column1]
    }

    private fun toCeil(board: Array<Array<Piece>>, input: InputMove): Piece {
        return board[input.row2][input.column2]
    }

    //base logic
    fun isValidTargetCeil(piece: Piece, target: Piece): Boolean {
        return piece.color != target.color
    }

    fun isValidPieceSelected(piece: Piece): Boolean {
        return when (piece) {
            is Empty -> false
            else -> true
        }
    }

    //pieces
    fun kingLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        val isValidFrom = isValidPieceSelected(fromCeil(board, input))
        val isValidTo = isValidTargetCeil(fromCeil(board, input), toCeil(board, input))
        return isValidFrom && isValidTo && isNearCeil(input)
    }
}
