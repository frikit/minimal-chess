package org.github.home.chess.models

sealed class Color(val name: String, val longName: String) {
    object White : Color("w", "White")
    object Black : Color("b", "Black")
    object Empty : Color(" ", " ")
}
