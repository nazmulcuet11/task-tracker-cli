package org.example

import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.Instant
import java.util.UUID

@Serializable
data class Task(
    val id: String,
    val description: String,
    val status: TaskStatus,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    fun withDescription(description: String, clock: Clock = Clock.System): Task =
        copy(description = description, updatedAt = clock.now())

    fun withStatus(status: TaskStatus, clock: Clock = Clock.System): Task =
        copy(status = status, updatedAt = clock.now())

    companion object {
        fun create(
            description: String,
            clock: Clock = Clock.System,
        ): Task {
            val now = clock.now()
            return Task(
                id = UUID.randomUUID().toString(),
                description = description,
                status = TaskStatus.TODO,
                createdAt = now,
                updatedAt = now,
            )
        }
    }
}
