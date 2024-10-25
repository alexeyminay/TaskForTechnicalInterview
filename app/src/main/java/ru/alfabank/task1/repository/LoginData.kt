package ru.alfabank.task1.repository

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val login: String,
    val password: String
)