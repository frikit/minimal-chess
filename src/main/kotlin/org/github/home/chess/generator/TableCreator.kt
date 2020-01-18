package org.github.home.chess.generator

import org.github.home.chess.models.*

object TableCreator {

    fun init(): Table {
        val whitePieces = generatePieces(Color.White)
        val blackPieces = generatePieces(Color.Black)

        return Table(whitePieces, blackPieces)
    }

    fun generatePieces(nr: Int, block: () -> Piece): Array<Piece> {
        return (1..nr).map { block.invoke() }.toTypedArray()
    }

    fun validateMaxPieces(pieces: Array<Piece>, maxAllowed: Int) {
        require(pieces.size == maxAllowed) { "Invalid number of piece -> ${pieces.size} != $maxAllowed" }
    }

    private fun generatePieces(color: Color): List<Piece> {
        val pawns = generatePieces(Pawn.maxNumbers) { Pawn(color) }
        validateMaxPieces(pawns, Pawn.maxNumbers)
        val knights = generatePieces(Knight.maxNumbers) { Knight(color) }
        validateMaxPieces(knights, Knight.maxNumbers)
        val bishops = generatePieces(Bishop.maxNumbers) { Bishop(color) }
        validateMaxPieces(bishops, Bishop.maxNumbers)
        val rooks = generatePieces(Rook.maxNumbers) { Rook(color) }
        validateMaxPieces(rooks, Rook.maxNumbers)
        val queen = generatePieces(Queen.maxNumbers) { Queen(color) }
        validateMaxPieces(queen, Queen.maxNumbers)
        val king = generatePieces(King.maxNumbers) { King(color) }
        validateMaxPieces(king, King.maxNumbers)

        return arrayOf((pawns + knights + bishops + rooks + queen + king)).flatten()
    }
}
