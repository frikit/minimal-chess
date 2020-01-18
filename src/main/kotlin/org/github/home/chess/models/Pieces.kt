package org.github.home.chess.models

import java.util.*

sealed class Piece(
    val id: String,
    val name: String,
    open val color: Color,
    val maxNumbers: Int
)

data class Empty(override val color: Color = Color.Empty) : Piece(generateID(), "  ", color, maxNumbers) {
    companion object {
        const val maxNumbers: Int = 99
    }
}

data class Pawn(override val color: Color) : Piece(generateID(), "pa", color, maxNumbers) {
    companion object {
        const val maxNumbers: Int = 8
    }
}

data class Knight(override val color: Color) : Piece(generateID(), "kn", color, maxNumbers) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Bishop(override val color: Color) : Piece(generateID(), "bi", color, maxNumbers) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Rook(override val color: Color) : Piece(generateID(), "ro", color, maxNumbers) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Queen(override val color: Color) : Piece(generateID(), "qu", color, maxNumbers) {
    companion object {
        const val maxNumbers: Int = 1
    }
}

data class King(override val color: Color) : Piece(generateID(), "ki", color, maxNumbers) {
    companion object {
        const val maxNumbers: Int = 1
    }
}


private fun generateID(): String {
    return UUID.randomUUID().toString()
}
