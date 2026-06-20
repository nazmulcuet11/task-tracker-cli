package org.example

import com.github.ajalt.clikt.core.CliktCommand

abstract class TaskCommand(
    commandName: String,
) : CliktCommand(commandName)
