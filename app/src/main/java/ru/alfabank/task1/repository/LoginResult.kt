package ru.alfabank.task1.repository

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    val token: String,
    val userId: String
)