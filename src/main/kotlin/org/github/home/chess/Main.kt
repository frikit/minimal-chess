package org.github.home.chess

import com.whitehatgaming.UserInput
import com.whitehatgaming.UserInputFile
import org.github.home.chess.logic.Game
import org.github.home.chess.logic.Table

object Main {

    lateinit var game: Game

    @JvmStatic
    fun main(args: Array<String>) {
        printWelcomeMessage()

        addShutdownHook()

        val path = if (args.isEmpty())
            "/Users/frikit/IdeaProjects/minimal-chess/src/main/resources/sample.txt"
        else
            args[0]

        playGame(path)
    }

    fun playGame(path: String, table: Table = Table()): Game {
        game = Game(table)

        val input: UserInput = UserInputFile(path)
        var play = true
        while (play) {
            play = game.move(input.nextMove())
        }

        return game
    }

    //messages
    private fun printWelcomeMessage() {
        println("Start chess game!")
    }

    private fun printWhoWin() {
        if (game.moveHistory.isEmpty()) {
            println("No moves have been made from start of the game!")
        } else {
            println("Player ${game.moveHistory.last().piece.color.longName} WIN!")
        }
    }

    private fun printMovesHistory() {
        println("Here is history:")
        game.moveHistory.forEachIndexed { idx, it ->
            print("${idx + 1}. ")
            println(it)
        }
    }

    private fun addShutdownHook() {
        val mainThread: Thread = Thread.currentThread()
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                printWhoWin()
                printMovesHistory()

                //kill process with the main thread will not end current running logic
                mainThread.join(5_000)
            }
        })
    }
}
