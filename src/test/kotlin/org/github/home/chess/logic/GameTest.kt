package org.github.home.chess.logic

import org.github.home.chess.Main
import org.junit.jupiter.api.Test
import java.io.File

internal class GameTest {

    val validMoves = "/Users/frikit/IdeaProjects/minimal-chess/src/main/resources/sample-moves.txt"
    val oneInvalidMove = "/Users/frikit/IdeaProjects/minimal-chess/src/main/resources/two-times-same-color.txt"

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
        val game = Main.playGame(oneInvalidMove)
        val curr = game.moveHistory.size
        val expected = File(oneInvalidMove).readLines().size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    //TODO add test with moves and check moves
}
