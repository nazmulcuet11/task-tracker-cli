package org.example

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument

internal class DeleteCommand(
    private val store: TaskStore,
) : TaskCommand("delete") {
    override fun help(context: Context) = "Delete a task"

    private val id by argument()

    override fun run() {
        val deleted = try {
            store.delete(id)
        } catch (e: IllegalArgumentException) {
            throw UsageError(e.message ?: "Delete failed")
        }
        echo("Deleted: [${deleted.status.label}] ${deleted.description} (${deleted.id})")
    }
}
