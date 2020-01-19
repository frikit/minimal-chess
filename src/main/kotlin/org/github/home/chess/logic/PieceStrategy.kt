package org.github.home.chess.logic

import org.github.home.chess.models.Color
import org.github.home.chess.models.Empty
import org.github.home.chess.models.InputMove
import org.github.home.chess.models.Piece
import kotlin.math.abs

object PieceStrategy {

    //king
    private fun isNearCeil(input: InputMove): Boolean {
        if (abs(input.column2 - input.column1) < 2
            && abs(input.row2 - input.row1) < 2
        ) {
            return true
        }
        return false
    }

    //bishop
    private fun isDiagonalCeil(board: Array<Array<Piece>>, input: InputMove): Boolean {
        var movable = false
        //TODO check not jump over other pieces while moving on diagonally
        if (abs(input.row2 - input.row1) == abs(input.column2 - input.column1)) {
            if (input.row1 > input.row2 && input.column1 > input.column2) {
                // Destination is up and to the left of the current location
                movable = goUpLeft(input, board)
            } else if (input.row1 < input.row2 && input.column1 > input.column2) {
                // Destination is down and to the left of the current location
                movable = goDownLeft(input, board)
            } else if (input.row1 > input.row2 && input.column1 < input.column2) {
                // Destination is up and to the right of the current location
                movable = goUpRight(input, board)
            } else if (input.row1 < input.row2 && input.column1 < input.column2) {
                // Destination is down and to the right of the current location
                movable = goDownRight(input, board)
            }
        }

        return movable
    }

    //TODO refactor these 4 methods
    private fun goDownLeft(input: InputMove, board: Array<Array<Piece>>): Boolean {
        var res = false
        var i: Int = input.row1 + 1
        var j: Int = input.column1 - 1
        val curr = board[input.row1][input.column1]
        while (i <= input.row2 && j >= input.column2) {
            val ceil = board[i][j]
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i++
            j--
        }
        return res
    }

    private fun goUpLeft(input: InputMove, board: Array<Array<Piece>>): Boolean {
        var res = false
        var i: Int = input.row1 - 1
        var j: Int = input.column1 - 1
        val curr = board[input.row1][input.column1]
        while (i >= input.row2 && j >= input.column2) {
            val ceil = board[i][j]
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i--
            j--
        }

        return res
    }

    private fun goDownRight(input: InputMove, board: Array<Array<Piece>>): Boolean {
        var res = false
        var i: Int = input.row1 + 1
        var j: Int = input.column1 + 1
        val curr = board[input.row1][input.column1]
        while (i <= input.row2 && j <= input.column2) {
            val ceil = board[i][j]
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i++
            j++
        }
        return res
    }

    private fun goUpRight(input: InputMove, board: Array<Array<Piece>>): Boolean {
        var res = false
        var i: Int = input.row1 - 1
        var j: Int = input.column1 + 1
        val curr = board[input.row1][input.column1]
        while (i >= input.row2 && j <= input.column2) {
            val ceil = board[i][j]
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i--
            j++
        }
        return res
    }

    //rook
    //TODO refactor
    private fun isHorizontalOrVerticalMove(board: Array<Array<Piece>>, input: InputMove): Boolean {
        val curr = board[input.row1][input.column1]
        val offset: Int

        //rows
        if (input.row1 != input.row2 && input.column1 == input.column2) {
            offset = if (input.row1 < input.row2) {
                1
            } else {
                -1
            }
            var x: Int = input.row1 + offset
            val lastIndex = input.row2
            while (x != input.row2 + offset) {
                if (x == lastIndex && board[x][input.column1].color != curr.color) return true
                if (x != lastIndex && board[x][input.column1].color != Color.Empty && board[x][input.column1].color != curr.color) return false
                if (board[x][input.column1].color == curr.color) return false

                x += offset
            }
        } else if (input.row1 == input.row2 && input.column1 != input.column2) {
            //columns
            offset = if (input.column1 < input.column2) {
                1
            } else {
                -1
            }
            var x: Int = input.column1 + offset
            val lastIndex = input.column2
            while (x != input.column2 + offset) {
                if (x == lastIndex && board[input.row1][x].color != curr.color) return true
                if (x != lastIndex && board[input.row1][x].color != Color.Empty && board[input.row1][x].color != curr.color) return false
                if (board[input.row1][x].color == curr.color) return false
                x += offset
            }
        }

        return false
    }

    //base logic
    fun isValidTargetCeil(piece: Piece, target: Piece): Boolean {
        return piece.color != target.color
    }

    private fun fromCeil(board: Array<Array<Piece>>, input: InputMove): Piece {
        return board[input.row1][input.column1]
    }

    private fun toCeil(board: Array<Array<Piece>>, input: InputMove): Piece {
        return board[input.row2][input.column2]
    }

    fun isValidPieceSelected(piece: Piece): Boolean {
        return when (piece) {
            is Empty -> false
            else -> true
        }
    }

    private fun isValidFromTo(board: Array<Array<Piece>>, input: InputMove): Boolean {
        val isValidFrom = isValidPieceSelected(fromCeil(board, input))
        val isValidTo = isValidTargetCeil(fromCeil(board, input), toCeil(board, input))
        return isValidFrom && isValidTo
    }

    //pieces
    fun kingLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && isNearCeil(input)
    }

    fun bishopLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && isDiagonalCeil(board, input)
    }

    fun rookLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && isHorizontalOrVerticalMove(board, input)
    }

}
