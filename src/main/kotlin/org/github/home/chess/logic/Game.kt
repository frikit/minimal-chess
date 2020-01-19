package org.github.home.chess.logic

import org.github.home.chess.logic.PieceStrategy.isValidPieceSelected
import org.github.home.chess.logic.PieceStrategy.isValidTargetCeil
import org.github.home.chess.models.*

class Game(table: Table = Table()) {
    companion object {
        private fun getChar(intType: Int): Char {
            return (intType + 97).toChar()
        }

        private fun getHumanReadableString(inits: List<Int>): String {
            val column1 = inits[0]
            val row1 = 7 - inits[1]
            val column2 = inits[2]
            val row2 = 7 - inits[3]

            return "${getChar(column1)}${row1 + 1}${getChar(column2)}${row2 + 1}"
        }

        fun printHumanReadable(inits: List<Int>) {
            print("Used values:  ")
            print(getHumanReadableString(inits))
            println()
        }

        fun printInput(inits: List<Int>) {
            print("Input values: ")
            inits.map { print(it) }
            println()
        }
    }

    private val board: Array<Array<Piece>> = table.board
    val moveHistory = arrayListOf<Move>()

    init {
        table.generateBoard()
        printTable(board)
    }

    private fun printTable(board: Array<Array<Piece>>) {
        println("   a   b   c   d   e   f   g   h")
        (0..7).forEach { i ->
            print("${8 - i} |")
            (0..7).forEach { j ->
                print("${board[i][j].name}:${board[i][j].color.name}")
                print("|")
            }
            print(" ${8 - i}")
            println()
        }
        println("   a   b   c   d   e   f   g   h")
    }

    fun move(moves: IntArray?): Boolean {
        if (moves == null) return false

        val inits = moves.toList()

        printStepHeader(inits)

        swapPieces(inits)

        printTable(board)

        return true
    }

    private fun isValidPlayerMove(currentColor: Color): Boolean {
        return (moveHistory.isEmpty() && currentColor == Color.White) //first move
                || (moveHistory.isNotEmpty() && currentColor != moveHistory.last().piece.color) //rest moves
    }

    private fun swapPieces(inits: List<Int>) {
        val input = InputMove(inits)
        val column1 = input.column1
        val row1 = input.row1
        val column2 = input.column2
        val row2 = input.row2

        val piece = board[row1][column1]
        val target = board[row2][column2]

        //TODO remove for debug purpose
        if (!isValidPlayerMove(piece.color)) println("Is invalid player move!")
        if (!isValidPieceSelected(piece)) println("Is invalid piece selected!")
        if (!isValidMove(piece, board, input)) println("Is invalid move made for piece!")
        if (!isValidTargetCeil(piece, target)) println("Is invalid target ceil!")

        if (isValidPlayerMove(piece.color)
            && isValidPieceSelected(piece)
            && isValidMove(piece, board, input)
            && isValidTargetCeil(piece, target)
        ) {
            board[row2][column2] = piece
            board[row1][column1] = Empty()

            moveHistory.add(Move(piece, getHumanReadableString(inits)))
        } else {
            println("Invalid player move please fix you input!")
        }
    }

    private fun isValidMove(piece: Piece, board: Array<Array<Piece>>, input: InputMove): Boolean {
        return when (piece) {
            is King -> PieceStrategy.kingLogic(board, input)
            is Pawn -> true
            is Knight -> true
            is Bishop -> PieceStrategy.bishopLogic(board, input)
            is Rook -> PieceStrategy.rookLogic(board, input)
            is Queen -> true
            else -> false//TODO nare cum sa ajunga aici
        }
    }


    private fun printStepHeader(inits: List<Int>) {
        println()
        println("------------------- NEXT MOVE -----------------------")
        println()

        printInput(inits)
        printHumanReadable(inits)
    }

}
