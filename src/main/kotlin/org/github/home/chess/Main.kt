package org.github.home.chess

fun main() {
    println("Start chess game!")
    addShutdownHook()
    Thread.sleep(10_000)
    println("Finish game")
}


private fun addShutdownHook() {
    val mainThread = Thread.currentThread()
    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            println("Will print score table or something like this!")
            //kill process with the main thread will not end current running logic
            mainThread.join(5_000)
        }
    })
}
