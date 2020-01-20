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
            "sample.txt"
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
        println(Game.getWhoWin(game))
    }

    private fun addShutdownHook() {
        val mainThread: Thread = Thread.currentThread()
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                printWhoWin()
                Game.printMovesHistory(game)

                //kill process with the main thread will not end current running logic
                mainThread.join(5_000)
            }
        })
    }
}
