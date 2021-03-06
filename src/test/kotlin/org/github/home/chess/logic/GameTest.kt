package org.github.home.chess.logic

import org.github.home.chess.Main
import org.github.home.chess.utils.Resource
import org.junit.jupiter.api.Test
import java.io.File

internal class GameTest {

    private val validMoves = Resource.getResource("valid-moves.txt")
    private val twoTimesWithWhite = Resource.getResource("two-times-same-color.txt")
    private val startWithBlack = Resource.getResource("first-move-black-color.txt")
    private val emptyCeilMove = Resource.getResource("invalid-move-empty-slot.txt")
    private val whiteOnWhiteCeilMove = Resource.getResource("move-white-on-white.txt")
    private val blackOnBlackCeilMove = Resource.getResource("move-black-on-black.txt")
    private val whiteCheckMate = Resource.getResource("white-checkmate.txt")
    private val notStartedGame = Resource.getResource("not-started-game.txt")
    private val checkOnKingMoveInvalidPiece = Resource.getResource("attempt-to-move-invalid-piece-in-check.txt")

    @Test
    fun `init game check no moves`() {
        assert(Game().moveHistory.isEmpty()) { "Empty move is present!" }
    }

    @Test
    fun `check make all valid moves`() {
        val game = Main.playGame(validMoves)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(validMoves).size
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check make all valid moves print history`() {
        val game = Main.playGame(validMoves)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(validMoves).size
        assert(curr == expected) { "Found in file $expected != $curr from history" }

        //not throw exception
        Game.printMovesHistory(game)
    }

    @Test
    fun `check make one invalid move from 3 moves`() {
        val game = Main.playGame(twoTimesWithWhite)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(twoTimesWithWhite).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check start with black`() {
        val game = Main.playGame(startWithBlack)
        val curr = game.moveHistory.size
        val expected = File(startWithBlack).readLines().size - 1 //first is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check move empty ceil`() {
        val game = Main.playGame(emptyCeilMove)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(emptyCeilMove).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check move white on white`() {
        val game = Main.playGame(whiteOnWhiteCeilMove)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(whiteOnWhiteCeilMove).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check move black on black`() {
        val game = Main.playGame(blackOnBlackCeilMove)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(blackOnBlackCeilMove).size - 1 //one is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }

    @Test
    fun `check mate with white`() {
        val game = Main.playGame(whiteCheckMate)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(whiteCheckMate).size
        assert(curr == expected) { "Found in file $expected != $curr from history" }

        val whoWin = Game.getWhoWin(game)
        assert(whoWin.contains("White WIN!")) { "White should win!" }
    }

    @Test
    fun `not started game`() {
        val game = Main.playGame(notStartedGame)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(notStartedGame).size - 1 //one move and is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }

        val whoWin = Game.getWhoWin(game)
        assert(whoWin == "No moves have been made from start of the game!") { "Should be empty history" }
    }

    @Test
    fun `test invalid move pieces on check on king`() {
        val game = Main.playGame(checkOnKingMoveInvalidPiece)
        val curr = game.moveHistory.size
        val expected = Resource.readLines(checkOnKingMoveInvalidPiece).size - 2 //two is invalid
        assert(curr == expected) { "Found in file $expected != $curr from history" }
    }
}
