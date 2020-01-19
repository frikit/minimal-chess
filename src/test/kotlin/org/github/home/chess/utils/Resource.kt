package org.github.home.chess.utils

import java.io.File

object Resource {
    private val projFolder = "/Users/frikit/IdeaProjects/minimal-chess/src/main/resources/"

    fun getResource(fileName: String): String {
        return File(projFolder + fileName).absolutePath
    }
}
