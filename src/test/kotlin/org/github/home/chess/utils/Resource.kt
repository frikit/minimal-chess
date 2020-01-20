package org.github.home.chess.utils

import java.io.File
import java.nio.file.Paths

object Resource {
    private val projFolder = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/"

    fun getResource(fileName: String, folder: String = "base/"): String {
        return File(projFolder + folder + fileName).absolutePath
    }

    fun readLines(path: String): List<String> {
        return File(path).readLines()
    }
}
