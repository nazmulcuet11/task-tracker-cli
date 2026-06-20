package org.example

import com.github.ajalt.clikt.core.main
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals

class AppTest {
    @Test
    fun addAndListTasks() {
        val storageFile = Files.createTempFile("tasks", ".txt")
        val store = TaskStore(storageFile)

        store.add("buy milk")
        store.add("walk dog")

        assertEquals(listOf("buy milk", "walk dog"), store.list())
    }

    @Test
    fun listReturnsEmptyWhenNoTasks() {
        val storageFile = Files.createTempFile("tasks", ".txt")
        val store = TaskStore(storageFile)

        assertEquals(emptyList(), store.list())
    }

    @Test
    fun addSupportsMultiWordItems() {
        val storageFile = Files.createTempFile("tasks", ".txt")
        val store = TaskStore(storageFile)

        runCli(store, arrayOf("add", "finish", "project", "report"))

        assertEquals(listOf("finish project report"), store.list())
    }
}

private fun runCli(store: TaskStore, args: Array<String>) {
    buildCli(store).main(args)
}
