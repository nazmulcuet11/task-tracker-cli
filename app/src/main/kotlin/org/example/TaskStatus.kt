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
    ;

    companion object {
        fun fromLabel(label: String): TaskStatus =
            entries.firstOrNull { it.label == label }
                ?: throw IllegalArgumentException("Invalid status: $label")
    }
}
