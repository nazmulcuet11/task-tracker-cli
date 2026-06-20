package org.example

import com.github.ajalt.clikt.core.Context

class TaskCli : TaskCommand("task-cli") {
    override fun help(context: Context) = "A simple task tracker"

    override fun run() = Unit
}
