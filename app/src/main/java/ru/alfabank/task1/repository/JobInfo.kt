package ru.alfabank.task1.repository

import kotlinx.serialization.Serializable

@Serializable
data class JobInfo(
    val organizationName: String,
)