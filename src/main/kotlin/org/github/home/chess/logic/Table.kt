package org.github.home.chess.logic

import org.github.home.chess.models.*

class Table(internal val board: Array<Array<Piece>> = Array(8) { Array<Piece>(8) { Empty() } }) {

    fun generateBoard(): Table {
        //white
        setPlayerBoard(7, board, Color.White)
        //black
        setPlayerBoard(0, board, Color.Black)

        return this
    }

    private fun setPlayerBoard(startIndex: Int, board: Array<Array<Piece>>, color: Color) {
        val pawns = generatePieces(Pawn.maxNumbers) {
            Pawn(
                color
            )
        }
        validateMaxPieces(pawns, Pawn.maxNumbers)
        val knights = generatePieces(Knight.maxNumbers) {
            Knight(
                color
            )
        }
        validateMaxPieces(knights, Knight.maxNumbers)
        val bishops = generatePieces(Bishop.maxNumbers) {
            Bishop(
                color
            )
        }
        validateMaxPieces(bishops, Bishop.maxNumbers)
        val rooks = generatePieces(Rook.maxNumbers) {
            Rook(
                color
            )
        }
        validateMaxPieces(rooks, Rook.maxNumbers)
        val queen = generatePieces(Queen.maxNumbers) {
            Queen(
                color
            )
        }
        validateMaxPieces(queen, Queen.maxNumbers)
        val king = generatePieces(King.maxNumbers) {
            King(
                color
            )
        }
        validateMaxPieces(king, King.maxNumbers)

        //set table
        //pawns
        val pawnIndex = when (color) {
            Color.White -> startIndex - 1
            Color.Black -> startIndex + 1
            else -> throw IllegalArgumentException("Color should be white or black!")
        }

        (0..7).map {
            board[pawnIndex][it] = pawns[it]
        }

        //rooks
        board[startIndex][0] = rooks[0]
        board[startIndex][7] = rooks[1]
        //knights
        board[startIndex][1] = knights[0]
        board[startIndex][6] = knights[1]
        //bishops
        board[startIndex][2] = bishops[0]
        board[startIndex][5] = bishops[1]
        //queen
        board[startIndex][3] = queen[0]
        //king
        board[startIndex][4] = king[0]
    }

    private fun generatePieces(nr: Int, block: () -> Piece): Array<Piece> {
        return (1..nr).map { block.invoke() }.toTypedArray()
    }

    private fun validateMaxPieces(pieces: Array<Piece>, maxAllowed: Int) {
        require(pieces.size == maxAllowed) { "Invalid number of piece -> ${pieces.size} != $maxAllowed" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Table

        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        return board.contentDeepHashCode()
    }
}

