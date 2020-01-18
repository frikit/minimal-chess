package org.github.home.chess.models

data class Table(val board: Array<Array<Piece>> = Array(8) { Array<Piece>(8) { Empty() } }) {

    fun generateBoard() {
        //white
        setPlayerBoard(7, board, Color.White)
        //black
        setPlayerBoard(0, board, Color.Black)
    }

    fun printTable() {
        println("   a   b   c   d   e   f   g   h")
        (0..7).forEach { i ->
            print("${8 - i} |")
            (0..7).forEach { j ->
                print("${board[i][j].name}:${board[i][j].color.name}")
                print("|")
            }
            print(" ${8 - i}")
            println()
        }
        println("   a   b   c   d   e   f   g   h")
    }

    fun move(moves: IntArray?): Boolean {
        if (moves == null) return false

        println()
        println("------------------- NEXT MOVE -----------------------")
        println()

        val inits = moves.toList()
        printInput(inits)
        printHumanReadable(inits)

        val column1 = inits[0]
        val row1 = inits[1]
        val column2 = inits[2]
        val row2 = inits[3]

        val piece = board[row1][column1]
        board[row2][column2] = piece
        board[row1][column1] = Empty()
        printTable()

        return true
    }

    private fun printHumanReadable(inits: List<Int>) {
        val column1 = inits[0]
        val row1 = 7 - inits[1]
        val column2 = inits[2]
        val row2 = 7 - inits[3]

        print("Used values:  ")
        print("${getChar(column1)}${row1 + 1}${getChar(column2)}${row2 + 1}")
        println()
    }

    private fun printInput(inits: List<Int>) {
        print("Input values: ")
        inits.map { print(it) }
        println()
    }

    private fun getChar(intType: Int): Char {
        return (intType + 97).toChar()
    }

    private fun setPlayerBoard(startIndex: Int, board: Array<Array<Piece>>, color: Color) {
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

        //set table
        //pawns
        val pawnIndex = when (color) {
            Color.White -> startIndex - 1
            Color.Black -> startIndex + 1
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

