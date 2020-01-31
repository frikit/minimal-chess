package org.github.home.chess.logic

import org.github.home.chess.models.HistoryMove
import java.util.ArrayList

open class History {

    open val moveHistory: ArrayList<HistoryMove> = arrayListOf()

}
