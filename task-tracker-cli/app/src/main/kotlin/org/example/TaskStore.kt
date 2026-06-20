package org.example

import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class TaskStore(private val storageFile: Path = defaultStorageFile()) {
    private val json = Json { prettyPrint = true }

    fun add(item: String) {
        require(item.isNotBlank()) { "Item cannot be empty" }
        val tasks = load().toMutableList()
        tasks.add(item)
        save(tasks)
    }

    fun list(): List<String> = load()

    private fun load(): List<String> {
        if (!Files.exists(storageFile)) {
            return emptyList()
        }
        val content = Files.readString(storageFile)
        if (content.isBlank()) {
            return emptyList()
        }
        return json.decodeFromString<TaskFile>(content).tasks
    }

    private fun save(tasks: List<String>) {
        Files.createDirectories(storageFile.parent)
        Files.writeString(storageFile, json.encodeToString(TaskFile(tasks)))
    }

    companion object {
        fun defaultStorageFile(): Path =
            Paths.get(System.getProperty("user.home"), ".task-tracker-cli", "tasks.json")
    }
}
