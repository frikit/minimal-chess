package org.github.home.chess.models

sealed class Piece(
    val name: String,
    open val color: Color
) {
    var isFirstMove = true
}

data class Empty(override val color: Color = Color.Empty) : Piece(" ", color)

data class Pawn(override val color: Color) : Piece("p", color) {
    companion object {
        const val maxNumbers: Int = 8
    }
}

data class Knight(override val color: Color) : Piece("n", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Bishop(override val color: Color) : Piece("b", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Rook(override val color: Color) : Piece("r", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Queen(override val color: Color) : Piece("q", color) {
    companion object {
        const val maxNumbers: Int = 1
    }
}

data class King(override val color: Color) : Piece("k", color) {
    companion object {
        const val maxNumbers: Int = 1
    }
}

