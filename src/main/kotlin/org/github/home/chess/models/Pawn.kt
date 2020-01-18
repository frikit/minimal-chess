package org.github.home.chess.models

import org.github.home.chess.models.color.Color

sealed class Piece(
    val name: String,
    open val color: Color,
    val maxNumbers: Int
)

data class Pawn(override val color: Color) : Piece("pawn", color, 8)
data class Knight(override val color: Color) : Piece("knight", color, 2)
data class Bishop(override val color: Color) : Piece("bishop", color, 2)
data class Rook(override val color: Color) : Piece("rook", color, 2)
data class Queen(override val color: Color) : Piece("queen", color, 1)
data class King(override val color: Color) : Piece("king", color, 1)
