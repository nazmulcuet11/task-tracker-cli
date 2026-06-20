package org.example

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument

internal class MarkDoneCommand(
    private val store: TaskStore,
) : TaskCommand("mark-done") {
    override fun help(context: Context) = "Mark a task as done"

    private val id by argument()

    override fun run() {
        val task = try {
            store.markDone(id)
        } catch (e: IllegalArgumentException) {
            throw UsageError(e.message ?: "Mark done failed")
        }
        echo("Marked done: [${task.status.label}] ${task.description} (${task.id})")
    }
}
