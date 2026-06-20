package org.example

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple

internal class UpdateCommand(
    private val store: TaskStore,
) : TaskCommand("update") {
    override fun help(context: Context) = "Update a task's description"

    private val id by argument()
    private val descriptionParts by argument().multiple(required = true)

    override fun run() {
        val task = try {
            store.update(id, descriptionParts.joinToString(" "))
        } catch (e: IllegalArgumentException) {
            throw UsageError(e.message ?: "Update failed")
        }
        echo("Updated: [${task.status.label}] ${task.description} (${task.id})")
    }
}
