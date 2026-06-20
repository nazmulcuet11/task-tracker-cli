package org.example

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument

internal class MarkInProgressCommand(
    private val store: TaskStore,
) : TaskCommand("mark-in-progress") {
    override fun help(context: Context) = "Mark a task as in progress"

    private val id by argument()

    override fun run() {
        val task = try {
            store.markInProgress(id)
        } catch (e: IllegalArgumentException) {
            throw UsageError(e.message ?: "Mark in progress failed")
        }
        echo("Marked in progress: [${task.status.label}] ${task.description} (${task.id})")
    }
}
