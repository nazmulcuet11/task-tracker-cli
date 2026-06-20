package org.example

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional

internal class ListCommand(
    private val store: TaskStore,
) : TaskCommand("list") {
    override fun help(context: Context) = "List all tasks or filter by status (todo, in-progress, done)"

    private val statusFilter by argument().optional()

    override fun run() {
        val status = statusFilter?.let { filter ->
            try {
                TaskStatus.fromLabel(filter)
            } catch (e: IllegalArgumentException) {
                throw UsageError(e.message ?: "Invalid status")
            }
        }
        val tasks = store.list(status)
        if (tasks.isEmpty()) {
            echo("No tasks.")
        } else {
            tasks.forEachIndexed { index, task ->
                echo("${index + 1}. [${task.status.label}] ${task.description}")
                echo("   id: ${task.id}")
                echo("   created: ${task.createdAt} | updated: ${task.updatedAt}")
            }
        }
    }
}
