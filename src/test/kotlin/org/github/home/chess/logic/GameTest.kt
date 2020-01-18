package org.github.home.chess.logic

import org.github.home.chess.models.Empty
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    fun `init game check no moves`() {
        assert(Game().lastColorMove.size == 1) { "Only one empty move is present!" }
        assert(Game().lastColorMove[0].piece is Empty) { "Only one empty move is present!" }
    }

    //TODO add test with moves and check moves
}
