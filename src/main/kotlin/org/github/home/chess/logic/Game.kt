package org.github.home.chess.logic

import org.github.home.chess.logic.PieceStrategy.isValidTargetCeil
import org.github.home.chess.models.*
import org.github.home.chess.printer.GamePrinter

class Game(table: Table = Table()): History() {
    private val board: Array<Array<Piece>> = table.board

    init {
        table.generateBoard()
        GamePrinter.printTable(board)
    }

    fun move(moves: IntArray?): Boolean {
        if (moves == null) return false

        val inits = moves.toList()

        GamePrinter.printStepHeader(inits)

        swapPieces(inits)

        GamePrinter.printTable(board)

        return true
    }

    private fun isValidPlayerMove(currentColor: Color): Boolean {
        return (moveHistory.isEmpty() && currentColor == Color.White) //first move
                || (moveHistory.isNotEmpty() && currentColor != moveHistory.last().piece.color) //rest moves
    }

    private fun swapPieces(inits: List<Int>) {
        val input = InputMove(inits)
        val column1 = input.column1
        val row1 = input.row1
        val column2 = input.column2
        val row2 = input.row2

        val piece = board[row1][column1]
        val target = board[row2][column2]

        if (isValidPlayerMove(piece.color)
            && isValidMove(piece, board, input)
            && isValidTargetCeil(piece, target)
        ) {
            val check = isCheckForCheck(false)
            if (check) println("in check")

            board[row2][column2] = piece
            board[row1][column1] = Empty()

            val check2 = isCheckForCheck(true)

            if (check2 && check) {
                board[row2][column2] = target
                board[row1][column1] = piece
                println("check")
            } else {
                val humanReadableStep = GamePrinter.getHumanReadableString(inits)
                moveHistory.add(HistoryMove(piece, humanReadableStep, check2))
                piece.isFirstMove = false
            }
        } else {
            println("Invalid player move please fix you input!")
        }
    }

    private fun isCheckForCheck(print: Boolean): Boolean {
        val pair = findKings()
        val blackKing: Pair<Int, Int> = pair.first
        val whiteKing: Pair<Int, Int> = pair.second

        //check all between all elements as there is no check
        board.forEachIndexed { y, item ->
            item.forEachIndexed { x, piece ->
                run {
                    var moves = emptyList<Int>()
                    if (piece.color == Color.Black) {
                        moves = listOf(x, y, whiteKing.first, whiteKing.second)
                    }
                    if (piece.color == Color.White) {
                        moves = listOf(x, y, blackKing.first, blackKing.second)
                    }
                    if (piece.color != Color.Empty) {
                        val inputMove = InputMove(moves)
                        if (isValidMove(piece, board, inputMove)) {
                            if (print) {
                                print("CHECK ON: ")
                                GamePrinter.printHumanReadable(moves)
                            }
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun findKings(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var whiteKing1 = Pair(0, 0)
        var blackKing1 = Pair(0, 0)
        board.forEachIndexed { y, item ->
            item.forEachIndexed { x, piece ->
                run {
                    if (piece is King && piece.color == Color.White) {
                        whiteKing1 = Pair(x, y)
                    }
                    if (piece is King && piece.color == Color.Black) {
                        blackKing1 = Pair(x, y)
                    }
                }
            }
        }
        return Pair(blackKing1, whiteKing1)
    }

    private fun isValidMove(piece: Piece, board: Array<Array<Piece>>, input: InputMove): Boolean {
        return when (piece) {
            is King -> PieceStrategy.kingLogic(board, input)
            is Pawn -> PieceStrategy.pawnLogic(board, input)
            is Knight -> PieceStrategy.knightLogic(board, input)
            is Bishop -> PieceStrategy.bishopLogic(board, input)
            is Rook -> PieceStrategy.rookLogic(board, input)
            is Queen -> PieceStrategy.queenLogic(board, input)
            else -> false//TODO nare cum sa ajunga aici
        }
    }

}
