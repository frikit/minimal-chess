package org.github.home.chess.models

import java.util.*

sealed class Piece(
    val id: String,
    val name: String,
    open val color: Color
)

data class Empty(override val color: Color = Color.Empty) : Piece(generateID(), " ", color)

data class Pawn(override val color: Color) : Piece(generateID(), "p", color) {
    companion object {
        const val maxNumbers: Int = 8
    }
}

data class Knight(override val color: Color) : Piece(generateID(), "n", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Bishop(override val color: Color) : Piece(generateID(), "b", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Rook(override val color: Color) : Piece(generateID(), "r", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Queen(override val color: Color) : Piece(generateID(), "q", color) {
    companion object {
        const val maxNumbers: Int = 1
    }
}

data class King(override val color: Color) : Piece(generateID(), "k", color) {
    companion object {
        const val maxNumbers: Int = 1
    }
}


private fun generateID(): String {
    return UUID.randomUUID().toString()
}
