package org.github.home.chess.models

sealed class Color(val name: String) {
    object White : Color("w")
    object Black : Color("b")
    object Empty : Color(" ")
}
