package org.github.home.chess.printer

import org.github.home.chess.logic.Game
import org.github.home.chess.models.Piece

object GamePrinter {

    private fun getChar(intType: Int): Char {
        return (intType + 97).toChar()
    }

    fun printMovesHistory(game: Game) {
        println("Here is history:")
        game.moveHistory.forEachIndexed { idx, it ->
            print("${idx + 1}. ")
            println(it)
        }
    }

    fun printTable(board: Array<Array<Piece>>) {
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

    fun getHumanReadableString(inits: List<Int>): String {
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

    fun printStepHeader(inits: List<Int>) {
        println()
        println("------------------- NEXT MOVE -----------------------")
        println()

        printInput(inits)
        printHumanReadable(inits)
    }

    fun printWhoWin(game: Game) {
        println(getWhoWin(game))
    }

    fun getWhoWin(game: Game): String {
        return if (game.moveHistory.isEmpty()) {
            "No moves have been made from start of the game!"
        } else {
            "Player ${game.moveHistory.last().piece.color.longName} WIN!"
        }
    }
}
