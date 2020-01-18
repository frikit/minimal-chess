package org.github.home.chess.models

import java.util.*

sealed class Piece(
    val id: String,
    val name: String,
    open val color: Color,
    val maxNumbers: Int
)

data class Pawn(override val color: Color) : Piece(generateID(), "pawn", color, 8)
data class Knight(override val color: Color) : Piece(generateID(), "knight", color, 2)
data class Bishop(override val color: Color) : Piece(generateID(), "bishop", color, 2)
data class Rook(override val color: Color) : Piece(generateID(), "rook", color, 2)
data class Queen(override val color: Color) : Piece(generateID(), "queen", color, 1)
data class King(override val color: Color) : Piece(generateID(), "king", color, 1)


private fun generateID(): String {
    return UUID.randomUUID().toString()
}
