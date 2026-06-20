package org.example

import com.github.ajalt.clikt.core.Context

internal class ListCommand(
    private val store: TaskStore,
) : TaskCommand("list") {
    override fun help(context: Context) = "List all items"

    override fun run() {
        val items = store.list()
        if (items.isEmpty()) {
            echo("No tasks.")
        } else {
            items.forEachIndexed { index, item ->
                echo("${index + 1}. $item")
            }
        }
    }
}
