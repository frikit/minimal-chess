package org.github.home.chess.models

class InputMove(inits: List<Int>) {

    val column1 by lazy { inits[0] }
    val row1 by lazy { inits[1] }
    val column2 by lazy { inits[2] }
    val row2 by lazy { inits[3] }

    init {
        require(inits.size == 4) { "Input should have only 4 elements" }
        require(column1 in 0..7) { "Column coord is out from board! [$column1]" }
        require(column2 in 0..7) { "Column coord is out from board! [$column2]" }
        require(row1 in 0..7) { "Row coord is out from board! [$row1]" }
        require(row2 in 0..7) { "Row coord is out from board! [$row2]" }
        require(column1 != column2 || row1 != row2) { "Coord are the same!" }
    }
}
