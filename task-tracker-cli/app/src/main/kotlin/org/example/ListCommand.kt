package org.example

import com.github.ajalt.clikt.core.Context

internal class ListCommand(
    private val store: TaskStore,
) : TaskCommand("list") {
    override fun help(context: Context) = "List all items"

    override fun run() {
        val tasks = store.list()
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
