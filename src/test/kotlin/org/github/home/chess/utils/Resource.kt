package org.github.home.chess.utils

import java.io.File

object Resource {
    private val projFolder = "/Users/frikit/IdeaProjects/minimal-chess/src/main/resources/"

    fun getResource(fileName: String, folder: String = "base/"): String {
        return File(projFolder + folder + fileName).absolutePath
    }

    fun readLines(path: String, folder: String = ""): List<String> {
        return File(path).readLines()
    }
}
