package com.challenge.task.presentation.user.domain.entity

data class User(
    val id: Long,
    val name: String,
    val email: String,
    val createdAt: Long
)