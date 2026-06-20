package org.example

import kotlinx.serialization.Serializable

@Serializable
data class TaskFile(
    val tasks: List<String> = emptyList(),
)
