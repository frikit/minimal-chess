package org.github.home.chess.logic

import org.github.home.chess.models.Color
import org.github.home.chess.models.Empty
import org.github.home.chess.models.InputMove
import org.github.home.chess.models.Piece
import kotlin.math.abs
import kotlin.math.absoluteValue

object PieceStrategy {

    private fun isNearCeil(input: InputMove): Boolean {
        if (abs(input.column2 - input.column1) < 2
            && abs(input.row2 - input.row1) < 2
        ) {
            return true
        }
        return false
    }

    private fun isDiagonalCeil(board: Array<Array<Piece>>, input: InputMove): Boolean {
        var movable = false
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
        var enemies = 0
        var i: Int = input.row1 + 1
        var j: Int = input.column1 - 1
        val curr = board[input.row1][input.column1]
        while (i <= input.row2 && j >= input.column2) {
            val ceil = board[i][j]
            if (isEnemy(curr, ceil)) enemies++
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i++
            j--
        }
        return enemies < 2 && res
    }

    private fun goUpLeft(input: InputMove, board: Array<Array<Piece>>): Boolean {
        var res = false
        var enemies = 0
        var i: Int = input.row1 - 1
        var j: Int = input.column1 - 1
        val curr = board[input.row1][input.column1]
        while (i >= input.row2 && j >= input.column2) {
            val ceil = board[i][j]
            if (isEnemy(curr, ceil)) enemies++
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i--
            j--
        }

        return enemies < 2 && res
    }

    private fun goDownRight(input: InputMove, board: Array<Array<Piece>>): Boolean {
        var res = false
        var enemies = 0
        var i: Int = input.row1 + 1
        var j: Int = input.column1 + 1
        val curr = board[input.row1][input.column1]
        while (i <= input.row2 && j <= input.column2) {
            val ceil = board[i][j]
            if (isEnemy(curr, ceil)) enemies++
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i++
            j++
        }
        return enemies < 2 && res
    }

    private fun goUpRight(input: InputMove, board: Array<Array<Piece>>): Boolean {
        var res = false
        var enemies = 0
        var i: Int = input.row1 - 1
        var j: Int = input.column1 + 1
        val curr = board[input.row1][input.column1]
        while (i >= input.row2 && j <= input.column2) {
            val ceil = board[i][j]
            if (isEnemy(curr, ceil)) enemies++
            res = isValidTargetCeil(curr, ceil)
            if (!res) {
                break
            }
            i--
            j++
        }
        return enemies < 2 && res
    }

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
                val ceil = board[x][input.column1]
                if (x == lastIndex && ceil.color != curr.color) return true
                if (x != lastIndex && ceil.color != Color.Empty && ceil.color != curr.color) return false
                if (ceil.color == curr.color) return false

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
                val ceil = board[input.row1][x]
                if (x == lastIndex && ceil.color != curr.color) return true
                if (x != lastIndex && ceil.color != Color.Empty && ceil.color != curr.color) return false
                if (ceil.color == curr.color) return false

                x += offset
            }
        }

        return false
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

    private fun isPawnMove(board: Array<Array<Piece>>, input: InputMove): Boolean {
        val current = board[input.row1][input.column1]

        val isDirectionValid = isPawnDirectionValid(current, input)
        if (!isDirectionValid) return false

        val isFirstMoveLogic = isPawnFirstMove(current, input, board)
        if (!isFirstMoveLogic) return false

        val isOtherMoveLogic = isPawnNotFirstMove(current, input, board)
        if (!isOtherMoveLogic) return false

        //kill piece
        val offset = if (current.color == Color.White) -1 else 1
        val toKill = checkToKillWithPawn(board, input, current, offset)
        if (!toKill) return false

        return true
    }

    private fun isPawnNotFirstMove(current: Piece, input: InputMove, board: Array<Array<Piece>>): Boolean {
        // on the second move allow 1 spaces
        if (!current.isFirstMove) {
            val distance = input.row1 - input.row2
            if (distance.absoluteValue > 1) {
                return false
            }
            val distanceCol = input.column1 - input.column2
            if (distanceCol.absoluteValue > 1) {
                return false
            }
            val offset = if (current.color == Color.White) -1 else 1
            val c1 = input.row1 + offset
            if (board[c1][input.column1] != Empty()) {
                return false
            }
        }

        return true
    }

    private fun isPawnFirstMove(current: Piece, input: InputMove, board: Array<Array<Piece>>): Boolean {
        // on first move allow 2 spaces
        if (current.isFirstMove) {
            //row
            var distance = input.row1 - input.row2
            if (distance.absoluteValue > 2) {
                //can't move more than 2 ceil
                return false
            } else if (distance.absoluteValue == 2 && input.column1 != input.column2) {
                //if is 2 ceil far make sure direction is on same column
                return false
            }
            //column
            distance = input.column2 - input.column1
            if (distance.absoluteValue > 1) {
                //can't move more than 1 ceil when kill
                return false
            }

            val moveEmptySpot = checkMoveEmptySpotWithPawn(current, input, board)
            if (!moveEmptySpot) return false
        }

        return true
    }

    private fun checkMoveEmptySpotWithPawn(current: Piece, input: InputMove, board: Array<Array<Piece>>): Boolean {
        // check move only on empty spot
        val offset1 = if (current.color == Color.White) -1 else 1
        val offset2 = if (current.color == Color.White) -2 else 2
        val c1 = input.row1 + offset1
        val c2 = input.row1 + offset2
        if (board[c1][input.column1] != Empty()) {
            return false
        }
        if (board[c2][input.column1] != Empty()) {
            return false
        }

        return true
    }

    private fun isPawnDirectionValid(current: Piece, input: InputMove): Boolean {
        // check direction
        if (current.color == Color.White && input.row1 < input.row2)
            return false

        if (current.color == Color.Black && input.row1 > input.row2)
            return false

        return true
    }

    private fun checkToKillWithPawn(
        board: Array<Array<Piece>>,
        input: InputMove,
        current: Piece,
        offset: Int
    ): Boolean {
        var isLeft = true
        var isRight = true

        val columnRight = input.column1 + 1
        val columnLeft = input.column1 - 1
        val row = input.row1 + offset

        if ((input.column2 == columnRight || input.column2 == columnLeft) && input.row2 == row) {
            isLeft = columnLeft in 0..7 && isEnemy(current, board[row][columnLeft])
            isRight = !isLeft && columnRight in 0..7 && isEnemy(current, board[row][columnRight])
        }

        return isLeft || isRight
    }

    //base logic
    private fun isEnemy(curr: Piece, ceil: Piece) =
        ceil.color !is Color.Empty && curr.color != ceil.color

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

    fun queenLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && (isDiagonalCeil(board, input) || isHorizontalOrVerticalMove(board, input))
    }

    fun knightLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && isOneOfLShapeMove(input)
    }

    fun pawnLogic(board: Array<Array<Piece>>, input: InputMove): Boolean {
        return isValidFromTo(board, input) && isPawnMove(board, input)
    }

}
