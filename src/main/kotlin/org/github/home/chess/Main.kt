package org.github.home.chess

import com.whitehatgaming.UserInput
import com.whitehatgaming.UserInputFile
import org.github.home.chess.models.Table

fun main() {
    printWelcomeMessage()

    addShutdownHook()

    val table = Table()
    table.generateBoard()
    table.printTable()

    val path = "/Users/frikit/IdeaProjects/minimal-chess/src/main/resources/sample-moves.txt"
    val input: UserInput = UserInputFile(path)
    var play = true
    while (play) {
        play = table.move(input.nextMove())
    }

    printByeMessage()
}

//messages
private fun printWelcomeMessage() {
    println("Start chess game!")
}

private fun printByeMessage() {
    println("Finish game!")
}

private fun addShutdownHook() {
    val mainThread: Thread = Thread.currentThread()
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            println("Will print score table or something like this!")
            //kill process with the main thread will not end current running logic
            mainThread.join(5_000)
        }
    })
}
