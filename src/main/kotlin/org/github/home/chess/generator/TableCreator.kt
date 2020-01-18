package org.github.home.chess.generator

import org.github.home.chess.models.*

object TableCreator {

    fun init(): Table {
        val whitePieces = generatePieces(Color.White)
        val blackPieces = generatePieces(Color.Black)

        return Table(whitePieces, blackPieces)
    }

    private fun generatePieces(nr: Int, block: () -> Piece): Array<Piece> {
        return (1..nr).map { block.invoke() }.toTypedArray()
    }

    private fun generatePieces(color: Color): List<Piece> {
        val pawns = generatePieces(8) { Pawn(color) }
        val knights = generatePieces(2) { Knight(color) }
        val bishops = generatePieces(2) { Bishop(color) }
        val rooks = generatePieces(2) { Rook(color) }
        val queen = generatePieces(1) { Queen(color) }
        val king = generatePieces(1) { King(color) }
        return arrayOf((pawns + knights + bishops + rooks + queen + king)).flatten()
    }
}
