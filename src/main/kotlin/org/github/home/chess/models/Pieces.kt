package org.github.home.chess.models

sealed class Piece(
    val name: String,
    open val longName: String,
    open val color: Color
) {
    var isFirstMove = true
}

data class Empty(override val color: Color = Color.Empty) : Piece(" ", " ", color)

data class Pawn(override val color: Color) : Piece(if (color == Color.White) "P" else "p", "Pawn", color) {
    companion object {
        const val maxNumbers: Int = 8
    }
}

data class Knight(override val color: Color) : Piece(if (color == Color.White) "N" else "n", "Knight", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Bishop(override val color: Color) : Piece(if (color == Color.White) "B" else "b", "Bishop", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Rook(override val color: Color) : Piece(if (color == Color.White) "R" else "r", "Rook", color) {
    companion object {
        const val maxNumbers: Int = 2
    }
}

data class Queen(override val color: Color) : Piece(if (color == Color.White) "Q" else "q", "Queen", color) {
    companion object {
        const val maxNumbers: Int = 1
    }
}

data class King(override val color: Color) : Piece(if (color == Color.White) "K" else "k", "King", color) {
    companion object {
        const val maxNumbers: Int = 1
    }
}

