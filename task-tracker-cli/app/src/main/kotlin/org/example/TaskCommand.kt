package org.example

import com.github.ajalt.clikt.core.CoreCliktCommand
import com.github.ajalt.clikt.core.context

abstract class TaskCommand(
    commandName: String,
) : CoreCliktCommand(commandName) {
    init {
        context {
            exitProcess = { status -> kotlin.system.exitProcess(status) }
            echoMessage = { _, message, newline, err ->
                val writer = if (err) System.err else System.out
                if (newline) writer.println(message) else writer.print(message)
            }
        }
    }
}
