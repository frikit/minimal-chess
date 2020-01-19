package org.github.home.chess.logic

import org.github.home.chess.Main
import org.github.home.chess.models.Color
import org.github.home.chess.models.Knight
import org.github.home.chess.utils.Resource
import org.junit.jupiter.api.Test

internal class KnightStrategyTest {

    private val folder = "knight/"
    private val moveCeil = Resource.getResource("valid-move.txt", folder)
    private val moveCeilKill = Resource.getResource("valid-move-kill.txt", folder)
    private val moveAliasCeil = Resource.getResource("invalid-move-alias-location.txt", folder)

    private val expectedColumn = 3
    private val expectedRow = 1
    private val expectedCeilPiece = Knight(Color.White)

    @Test
    fun `test valid move location`() {
        val game = Main.playGame(moveCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(moveCeil).size
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `test valid move location kill one`() {
        val table = Table()
        val game = Main.playGame(moveCeilKill, table)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(moveCeilKill).size
        assert(curr == expected) { "Found in file $expected != $curr from history" }

        val piece = table.board[expectedColumn][expectedRow]
        assert(piece == expectedCeilPiece) { "Should be ${expectedCeilPiece.javaClass.name}! found [$piece]" }
    }

    @Test
    fun `test valid move location but alias already there`() {
        val game = Main.playGame(moveAliasCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(moveAliasCeil).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

}
