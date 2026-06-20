package org.example

import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple

internal class AddCommand(
    private val store: TaskStore,
) : TaskCommand("add") {
    override fun help(context: Context) = "Add an item to the task list"

    private val itemParts by argument().multiple(required = true)

    override fun run() {
        val description = itemParts.joinToString(" ")
        val task = store.add(description)
        echo("Added: [${task.status.label}] ${task.description} (${task.id})")
    }
}
