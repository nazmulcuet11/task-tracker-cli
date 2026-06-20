package org.example

import kotlinx.serialization.Serializable
import java.time.Clock
import java.time.Instant
import java.util.UUID

@Serializable
data class Task(
    val id: String,
    val description: String,
    val status: TaskStatus,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant,
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant,
) {
    companion object {
        fun create(
            description: String,
            clock: Clock = Clock.systemUTC(),
        ): Task {
            val now = clock.instant()
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
