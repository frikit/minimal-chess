package org.github.home.chess.logic

import org.github.home.chess.Main
import org.github.home.chess.models.Color
import org.github.home.chess.models.Pawn
import org.github.home.chess.utils.Resource
import org.junit.jupiter.api.Test

internal class PawnStrategyTest {

    private val folder = "pawn/"
    private val moveCeil = Resource.getResource("valid-move.txt", folder)
    private val moveCeilKill = Resource.getResource("valid-move-kill.txt", folder)
    private val moveAliasCeil = Resource.getResource("invalid-move-alias-location.txt", folder)
    private val moveDownTwoCeil = Resource.getResource("invalid-move-down.txt", folder)
    private val moveUpTwoCeil = Resource.getResource("invalid-move-up.txt", folder)
    private val moveDiagonalTwoCeil = Resource.getResource("invalid-move-diagonal.txt", folder)

    private val expectedColumn = 2
    private val expectedRow = 0
    private val expectedCeilPiece = Pawn(Color.White)

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

    @Test
    fun `test invalid move location(column down)`() {
        val game = Main.playGame(moveDownTwoCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(moveDownTwoCeil).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `test invalid move location(column up)`() {
        val game = Main.playGame(moveUpTwoCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(moveUpTwoCeil).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `test invalid move location(diagonal)`() {
        val game = Main.playGame(moveDiagonalTwoCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(moveDiagonalTwoCeil).size - 4 // 0 moves should be made
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }
}
