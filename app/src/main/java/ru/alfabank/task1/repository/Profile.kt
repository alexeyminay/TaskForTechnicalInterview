package ru.alfabank.task1.repository

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val firstName: String,
    val lastName: String,
)