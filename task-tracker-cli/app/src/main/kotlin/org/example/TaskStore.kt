package org.example

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class TaskStore(private val storageFile: Path = defaultStorageFile()) {
    fun add(item: String) {
        require(item.isNotBlank()) { "Item cannot be empty" }
        Files.createDirectories(storageFile.parent)
        Files.writeString(
            storageFile,
            item + System.lineSeparator(),
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND,
        )
    }

    fun list(): List<String> =
        if (!Files.exists(storageFile)) {
            emptyList()
        } else {
            Files.readAllLines(storageFile).filter { it.isNotBlank() }
        }

    companion object {
        fun defaultStorageFile(): Path =
            Paths.get(System.getProperty("user.home"), ".task-tracker-cli", "tasks.txt")
    }
}
