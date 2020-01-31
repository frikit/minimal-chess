package org.github.home.chess.models

data class HistoryMove(val piece: Piece, val move: String, val isCheck: Boolean) {

    override fun toString(): String {
        return "Move(move='$move', player=${piece.color.longName}, piece=${piece.longName})"
    }
}
