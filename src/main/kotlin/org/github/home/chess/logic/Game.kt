package org.github.home.chess.logic

import org.github.home.chess.models.Color
import org.github.home.chess.models.Empty
import org.github.home.chess.models.Move
import org.github.home.chess.models.Piece

class Game(table: Table = Table()) {

    private val board: Array<Array<Piece>> = table.board
    private val lastColorMove = arrayListOf<Move>(Move(Empty(), " "))

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
        return currentColor != lastColorMove.last().piece.color
    }

    private fun swapPieces(inits: List<Int>) {
        val column1 = inits[0]
        val row1 = inits[1]
        val column2 = inits[2]
        val row2 = inits[3]

        val piece = board[row1][column1]
        if (isValidPlayerMove(piece.color)) {
            board[row2][column2] = piece
            board[row1][column1] = Empty()

            lastColorMove.add(Move(piece, getHumanReadableString(inits)))
        } else {
            println("Invalid player move please fix you input!")
        }
    }

    private fun printStepHeader(inits: List<Int>) {
        println()
        println("------------------- NEXT MOVE -----------------------")
        println()

        printInput(inits)
        printHumanReadable(inits)
    }

    private fun getHumanReadableString(inits: List<Int>): String {
        val column1 = inits[0]
        val row1 = 7 - inits[1]
        val column2 = inits[2]
        val row2 = 7 - inits[3]

        return "${getChar(column1)}${row1 + 1}${getChar(column2)}${row2 + 1}"
    }

    private fun printHumanReadable(inits: List<Int>) {
        print("Used values:  ")
        print(getHumanReadableString(inits))
        println()
    }

    private fun printInput(inits: List<Int>) {
        print("Input values: ")
        inits.map { print(it) }
        println()
    }

    private fun getChar(intType: Int): Char {
        return (intType + 97).toChar()
    }

}
