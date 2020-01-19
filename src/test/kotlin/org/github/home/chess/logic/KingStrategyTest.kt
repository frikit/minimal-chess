package org.github.home.chess.logic

import org.github.home.chess.Main
import org.github.home.chess.utils.Resource
import org.junit.jupiter.api.Test

internal class KingStrategyTest {

    private val kingMoveCeil = Resource.getResource("king-valid-move.txt")
    private val kingMoveAliasCeil = Resource.getResource("king-invalid-move-alias-location.txt")
    private val kingMoveDownTwoCeil = Resource.getResource("king-invalid-move-down.txt")
    private val kingMoveUpTwoCeil = Resource.getResource("king-invalid-move-up.txt")
    private val kingMoveDiagonalTwoCeil = Resource.getResource("king-invalid-move-diagonal.txt")


    @Test
    fun `test valid move location`() {
        val game = Main.playGame(kingMoveCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(kingMoveCeil).size
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `test valid move location but alias already there`() {
        val game = Main.playGame(kingMoveAliasCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(kingMoveAliasCeil).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `test invalid move location(column down)`() {
        val game = Main.playGame(kingMoveDownTwoCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(kingMoveDownTwoCeil).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `test invalid move location(column up)`() {
        val game = Main.playGame(kingMoveUpTwoCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(kingMoveUpTwoCeil).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `test invalid move location(diagonal)`() {
        val game = Main.playGame(kingMoveDiagonalTwoCeil)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(kingMoveDiagonalTwoCeil).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }
}
