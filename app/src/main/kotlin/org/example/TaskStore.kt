package org.example

import kotlinx.serialization.json.Json
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.time.Clock

class TaskStore(
    private val storageFile: Path = defaultStorageFile(),
    private val clock: Clock = Clock.System,
) {
    private val json = Json { prettyPrint = true }

    fun add(description: String): Task {
        require(description.isNotBlank()) { "Item cannot be empty" }
        val tasks = load().toMutableList()
        val task = Task.create(description, clock)
        tasks.add(task)
        save(tasks)
        return task
    }

    fun list(): List<Task> = load()

    fun update(id: String, description: String): Task {
        require(description.isNotBlank()) { "Description cannot be empty" }
        val tasks = load().toMutableList()
        val index = tasks.indexOfFirst { it.id == id }
        require(index >= 0) { "Task not found: $id" }
        val updated = tasks[index].withDescription(description, clock)
        tasks[index] = updated
        save(tasks)
        return updated
    }

    fun delete(id: String): Task {
        val tasks = load().toMutableList()
        val index = tasks.indexOfFirst { it.id == id }
        require(index >= 0) { "Task not found: $id" }
        val deleted = tasks[index]
        tasks.removeAt(index)
        save(tasks)
        return deleted
    }

    fun markInProgress(id: String): Task = updateStatus(id, TaskStatus.IN_PROGRESS)

    fun markDone(id: String): Task = updateStatus(id, TaskStatus.DONE)

    private fun updateStatus(id: String, status: TaskStatus): Task {
        val tasks = load().toMutableList()
        val index = tasks.indexOfFirst { it.id == id }
        require(index >= 0) { "Task not found: $id" }
        val updated = tasks[index].withStatus(status, clock)
        tasks[index] = updated
        save(tasks)
        return updated
    }

    private fun load(): List<Task> {
        if (!Files.exists(storageFile)) {
            return emptyList()
        }
        val content = Files.readString(storageFile)
        if (content.isBlank()) {
            return emptyList()
        }
        return json.decodeFromString<TaskFile>(content).tasks
    }

    private fun save(tasks: List<Task>) {
        Files.createDirectories(storageFile.parent)
        Files.writeString(storageFile, json.encodeToString(TaskFile(tasks)))
    }

    companion object {
        fun defaultStorageFile(): Path =
            Paths.get(System.getProperty("user.home"), ".task-tracker-cli", "tasks.json")
    }
}
