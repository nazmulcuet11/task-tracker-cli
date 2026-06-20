package org.example

import com.github.ajalt.clikt.core.main
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
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
