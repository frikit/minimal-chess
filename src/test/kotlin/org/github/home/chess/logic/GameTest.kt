package org.github.home.chess.logic

import org.github.home.chess.Main
import org.github.home.chess.utils.Resource
import org.junit.jupiter.api.Test
import java.io.File

internal class GameTest {

    private val validMoves = Resource.getResource("valid-moves.txt")
    private val twoTimesWithWhite = Resource.getResource("two-times-same-color.txt")
    private val startWithBlack = Resource.getResource("first-move-black-color.txt")

    @Test
    fun `init game check no moves`() {
        assert(Game().moveHistory.isEmpty()) { "Empty move is present!" }
    }

    @Test
    fun `check make all valid moves`() {
        val game = Main.playGame(validMoves)
        val curr = game.moveHistory.size
        val expected = File(validMoves).readLines().size
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check make one invalid move from 3 moves`() {
        val game = Main.playGame(twoTimesWithWhite)
        val curr = game.moveHistory.size
        val expected = File(twoTimesWithWhite).readLines().size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check make start with black`() {
        val game = Main.playGame(startWithBlack)
        val curr = game.moveHistory.size
        val expected = File(startWithBlack).readLines().size - 1 //first is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

}
