package org.example

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TaskStatus(val label: String) {
    @SerialName("todo")
    TODO("todo"),

    @SerialName("in-progress")
    IN_PROGRESS("in-progress"),

    @SerialName("done")
    DONE("done"),
}
