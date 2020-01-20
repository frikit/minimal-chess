package org.github.home.chess.logic

import org.github.home.chess.models.Color
import org.github.home.chess.models.Empty
import org.github.home.chess.models.InputMove
import org.github.home.chess.models.Piece
import kotlin.math.abs
import kotlin.math.absoluteValue

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

    private fun isOneOfLShapeMove(input: InputMove): Boolean {
        val targetPosition = Pair(input.column2, input.row2)

        //up d
        var row = input.row1 + 2
        var column = input.column1 + 1
        val upRight = Pair(column, row)
        column = input.column1 - 1
        val upLeft = Pair(column, row)

        //right d
        row = input.row1 - 2
        column = input.column1 + 1
        val rightUp = Pair(column, row)
        column = input.column1 - 1
        val rightDown = Pair(column, row)

        //down
        column = input.column1 - 2
        row = input.row1 + 1
        val downRight = Pair(column, row)
        row = input.row1 - 1
        val downLeft = Pair(column, row)

        //left
        column = input.column1 + 2
        row = input.row1 + 1
        val leftUp = Pair(column, row)
        row = input.row1 - 1
        val leftDown = Pair(column, row)

        val positions = listOf(upLeft, upRight, downLeft, downRight, leftDown, leftUp, rightDown, rightUp)

        return positions.contains(targetPosition)
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

    fun queenLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && (isDiagonalCeil(board, input) || isHorizontalOrVerticalMove(board, input))
    }

    fun knightLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && isOneOfLShapeMove(input)
    }

    fun pawnLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && isPawnMove(board, input)
    }

    private fun isPawnMove(board: Array<Array<Piece>>, input: InputMove): Boolean {
        val current = board[input.row1][input.column1]

        // check direction
        if (current.color == Color.White) {
            if (input.row1 < input.row2)
                return false
        }

        // check direction
        if (current.color == Color.Black) {
            if (input.row1 > input.row2)
                return false
        }

        // on first move allow 2 spaces
        if (current.isFirstMove) {
            val distance = input.row1 - input.row2
            if (distance.absoluteValue > 2) {
                //can't move more than 2 ceil
                return false
            } else if (distance.absoluteValue >= 2 && input.column1 != input.column2) {
                //if is 2 ceil far make sure direction is on same column
                return false
            }
            // check move only on empty spot
            if (current.color == Color.White) {
                val c1 = input.row1 - 1
                val c2 = input.row1 - 2
                if (board[c1][input.column1] != Empty()) {
                    return false
                }
                if (board[c2][input.column1] != Empty()) {
                    return false
                }
            }
            if (current.color == Color.Black) {
                val c1 = input.row1 + 1
                val c2 = input.row1 + 2
                if (board[c1][input.column1] != Empty()) {
                    return false
                }
                if (board[c2][input.column1] != Empty()) {
                    return false
                }
            }
        }

        // on first move allow 1 spaces
        if (!current.isFirstMove) {
            val distance = input.row1 - input.row2
            if (distance.absoluteValue > 1) {
                return false
            }
            if (current.color == Color.White) {
                val c1 = input.row1 - 1
                if (board[c1][input.column1] != Empty()) {
                    return false
                }
            }
            if (current.color == Color.Black) {
                val c1 = input.row1 + 1
                if (board[c1][input.column1] != Empty()) {
                    return false
                }
            }
        }

        if (current.color == Color.White) {
            val columnRight = input.column1 + 1
            val columnLeft = input.column1 - 1
            val row = input.row1 - 1
            if (input.column2 == columnRight || input.column2 == columnLeft && input.row2 == row) {
                if (columnLeft in 0..7 && board[row][columnLeft].color != Color.Black)
                    return false

                if (columnRight in 0..7 && board[row][columnRight].color != Color.Black)
                    return false
            }

        }

        if (current.color == Color.Black) {
            val columnRight = input.column1 + 1
            val columnLeft = input.column1 - 1
            val row = input.row1 + 1
            if ((input.column2 == columnRight || input.column2 == columnLeft) && input.row2 == row) {
                if (columnLeft in 0..7 && board[row][columnLeft].color != Color.White)
                    return false

                if (columnRight in 0..7 && board[row][columnRight].color != Color.White)
                    return false
            }

        }

        return true
    }

}
