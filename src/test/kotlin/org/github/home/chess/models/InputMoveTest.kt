package org.github.home.chess.models

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

internal class InputMoveTest {

    @Test
    fun `valid input provided no exception`() {
        assertDoesNotThrow {
            InputMove(listOf(0, 1, 2, 3))
        }
    }

    @Test
    fun `valid input provided same column different rows`() {
        assertDoesNotThrow {
            InputMove(listOf(0, 1, 0, 3))
        }
    }

    @Test
    fun `valid input provided same row different columns`() {
        assertDoesNotThrow {
            InputMove(listOf(0, 1, 2, 1))
        }
    }

    @Test
    fun `invalid input provided same location`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 0, 0, 0))
        }
    }

    @Test
    fun `invalid input provided same location - 2`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 1, 0, 1))
        }
    }

    @Test
    fun `invalid input provided too much`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 1, 2, 3, 4))
        }
    }

    @Test
    fun `invalid input provided not enough`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 1, 2))
        }
    }

    @Test
    fun `invalid input column1 provided`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(-1, 1, 2, 3))
        }
    }

    @Test
    fun `invalid input column1 provided - 2`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(8, 1, 2, 3))
        }
    }

    @Test
    fun `invalid input column2 provided`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 1, -2, 3))
        }
    }

    @Test
    fun `invalid input column2 provided - 2`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 1, 8, 3))
        }
    }

    @Test
    fun `invalid input row1 provided`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, -1, 2, 3))
        }
    }

    @Test
    fun `invalid input row1 provided - 2`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 8, 2, 3))
        }
    }

    @Test
    fun `invalid input row2 provided`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 1, 2, -1))
        }
    }

    @Test
    fun `invalid input row2 provided - 2`() {
        assertThrows<IllegalArgumentException> {
            InputMove(listOf(0, 1, 2, 8))
        }
    }
}
