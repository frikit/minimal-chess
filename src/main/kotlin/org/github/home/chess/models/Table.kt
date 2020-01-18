package org.github.home.chess.models

import org.github.home.chess.generator.TableCreator

data class Table(
    //TODO remove
    val whitePieces: List<Piece>,
    val blackPieces: List<Piece>
) {
    val board = Array(8) { Array<Piece>(8) { Empty() } }

    fun generateBoard() {
        fillBoardWithEmpty()
        //white
        setPlayerBoard(0, board, Color.White)
        //black
        setPlayerBoard(7, board, Color.Black)
    }

    private fun fillBoardWithEmpty() {
        (0..7).forEach { i ->
            (0..7).forEach { j ->
                board[i][j] = Empty()
            }
        }
    }

    fun printTable() {
        println("    a    b    c    d    e    f    g    h    ")
        (7 downTo 0).forEach { i ->
            print("${i + 1} |")
            (0..7).forEach { j ->
                print("${board[i][j].name}:${board[i][j].color.name}")
                print("|")
            }
            print(" ${i + 1} |")
            println()
        }
        println("    a    b    c    d    e    f    g    h    ")
    }

    fun move(moves: IntArray?): Boolean {
        if (moves == null) return false

        println("============================")
        println()
        val inits = moves.toList()
        print("Input values: ")
        inits.map { print(it) }
        println()

        val target = 7 - inits[1]
        val target2 = 7 - inits[3]

        print("Used values:  ")
        print("${getChar(inits[0] + 97)}${target + 1}${getChar(inits[2] + 97)}${target2 + 1}")
        println()

        val piece = board[inits[0]][target]
        println("Step 1:")
//        printTable()
        board[inits[2]][target2] = piece
        println("Step 2:")
//        printTable()
        board[inits[0]][target] = Empty()
        println("Step 3:")
        printTable()

        return true
    }

    private fun getChar(ints: Int): Char {
        return ints.toChar()
    }

    private fun setPlayerBoard(startIndex: Int, board: Array<Array<Piece>>, color: Color) {
        val pawns = TableCreator.generatePieces(Pawn.maxNumbers) { Pawn(color) }
        TableCreator.validateMaxPieces(pawns, Pawn.maxNumbers)
        val knights = TableCreator.generatePieces(Knight.maxNumbers) { Knight(color) }
        TableCreator.validateMaxPieces(knights, Knight.maxNumbers)
        val bishops = TableCreator.generatePieces(Bishop.maxNumbers) { Bishop(color) }
        TableCreator.validateMaxPieces(bishops, Bishop.maxNumbers)
        val rooks = TableCreator.generatePieces(Rook.maxNumbers) { Rook(color) }
        TableCreator.validateMaxPieces(rooks, Rook.maxNumbers)
        val queen = TableCreator.generatePieces(Queen.maxNumbers) { Queen(color) }
        TableCreator.validateMaxPieces(queen, Queen.maxNumbers)
        val king = TableCreator.generatePieces(King.maxNumbers) { King(color) }
        TableCreator.validateMaxPieces(king, King.maxNumbers)

        //set table
        //pawns
        val pawnIndex = when (color) {
            Color.Black -> startIndex - 1
            Color.White -> startIndex + 1
            Color.Empty -> startIndex
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
}

