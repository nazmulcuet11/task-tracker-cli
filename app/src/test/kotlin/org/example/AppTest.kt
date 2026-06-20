package org.example

import com.github.ajalt.clikt.core.main
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Clock
import kotlin.time.Instant

class AppTest {
    private val fixedInstant = Instant.parse("2026-06-20T10:00:00Z")
    private val fixedClock = object : Clock {
        override fun now() = fixedInstant
    }

    @Test
    fun addAndListTasks() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)

        store.add("buy milk")
        store.add("walk dog")

        assertEquals(
            listOf("buy milk", "walk dog"),
            store.list().map { it.description },
        )
        assertEquals(TaskStatus.TODO, store.list().first().status)
    }

    @Test
    fun listReturnsEmptyWhenNoTasks() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)

        assertEquals(emptyList(), store.list())
    }

    @Test
    fun addSupportsMultiWordItems() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)

        runCli(store, arrayOf("add", "finish", "project", "report"))

        assertEquals("finish project report", store.list().single().description)
    }

    @Test
    fun updateChangesDescription() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)
        val task = store.add("buy milk")

        runCli(store, arrayOf("update", task.id, "Buy groceries and cook dinner"))

        val updated = store.list().single()
        assertEquals("Buy groceries and cook dinner", updated.description)
        assertEquals(task.id, updated.id)
        assertEquals(task.status, updated.status)
        assertEquals(task.createdAt, updated.createdAt)
    }

    @Test
    fun updateFailsWhenTaskNotFound() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)

        assertFailsWith<IllegalArgumentException> {
            store.update("missing-id", "new description")
        }
    }

    @Test
    fun markInProgressUpdatesStatus() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)
        val task = store.add("buy milk")

        runCli(store, arrayOf("mark-in-progress", task.id))

        assertEquals(TaskStatus.IN_PROGRESS, store.list().single().status)
    }

    @Test
    fun markDoneUpdatesStatus() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)
        val task = store.add("buy milk")

        runCli(store, arrayOf("mark-done", task.id))

        assertEquals(TaskStatus.DONE, store.list().single().status)
    }

    @Test
    fun markInProgressFailsWhenTaskNotFound() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)

        assertFailsWith<IllegalArgumentException> {
            store.markInProgress("missing-id")
        }
    }

    @Test
    fun taskPersistsAllFieldsAsJson() {
        val storageFile = Files.createTempFile("tasks", ".json")
        val store = TaskStore(storageFile, fixedClock)

        val task = store.add("buy milk")

        val reloaded = TaskStore(storageFile, fixedClock).list().single()
        assertEquals(task.id, reloaded.id)
        assertEquals("buy milk", reloaded.description)
        assertEquals(TaskStatus.TODO, reloaded.status)
        assertEquals(fixedInstant, reloaded.createdAt)
        assertEquals(fixedInstant, reloaded.updatedAt)
    }
}

private fun runCli(store: TaskStore, args: Array<String>) {
    buildCli(store).main(args)
}
