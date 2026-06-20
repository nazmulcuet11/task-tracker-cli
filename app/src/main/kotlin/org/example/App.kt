package org.example

import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) {
    buildCli().main(args)
}

internal fun buildCli(store: TaskStore = TaskStore()): TaskCli =
    TaskCli()
        .subcommands(AddCommand(store), ListCommand(store), UpdateCommand(store), DeleteCommand(store))
