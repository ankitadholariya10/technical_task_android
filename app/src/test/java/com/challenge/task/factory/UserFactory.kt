package com.challenge.task.factory

import com.challenge.task.presentation.domain.entity.User

object UserFactory {

    fun createUser(
        id: Long = 0,
        name: String = StringFactory.create(),
        email: String = StringFactory.create(),
        createdAt: Long = 0
    ): User {
        return User(
            id = id,
            name = name,
            email = email,
            createdAt = createdAt
        )
    }
}