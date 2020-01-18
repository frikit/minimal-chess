package org.github.home.chess.logic

import org.github.home.chess.models.*
import org.junit.jupiter.api.Test

internal class TableTest {

    @org.junit.jupiter.api.BeforeEach
    fun setUp() {
    }

    @org.junit.jupiter.api.AfterEach
    fun tearDown() {
    }

    @Test
    fun `test generated tables are the same`() {
        assert(Table() == Table()) { "new generate tables should be equal" }
    }

    @Test
    fun `test generated table are empty`() {
        Table().board.forEach { rows ->
            rows.forEach { piece ->
                assert(piece.name == Empty().name) { "Table is not empty!" }
            }
        }
    }

    @Test
    fun `test generated table check white-black`() {
        val table = Table().generateBoard()
        val board = table.board

        val white = board.map { it.filter { it.color == Color.White } }.flatten()
        val black = board.map { it.filter { it.color == Color.Black } }.flatten()
        assert(white.size == 16) { "Should be 16 white pieces on the table but found ${white.size}" }
        assert(black.size == 16) { "Should be 16 black pieces on the table but found ${black.size}" }
    }

    @Test
    fun `test generated table check pieces types`() {
        val table = Table().generateBoard()
        val board = table.board

        val pawns = board.map { it.filterIsInstance<Pawn>() }.flatten()
        val knights = board.map { it.filterIsInstance<Knight>() }.flatten()
        val bishops = board.map { it.filterIsInstance<Bishop>() }.flatten()
        val rooks = board.map { it.filterIsInstance<Rook>() }.flatten()
        val queen = board.map { it.filterIsInstance<Queen>() }.flatten()
        val king = board.map { it.filterIsInstance<King>() }.flatten()

        assert(pawns.size == 16) { "Should be 16 pawns pieces on the table" }
        assert(knights.size == 4) { "Should be 4 knigths pieces on the table" }
        assert(bishops.size == 4) { "Should be 4 bishops pieces on the table" }
        assert(rooks.size == 4) { "Should be 4 rooks pieces on the table" }
        assert(queen.size == 2) { "Should be 2 queen pieces on the table" }
        assert(king.size == 2) { "Should be 2 king pieces on the table" }
    }
}
