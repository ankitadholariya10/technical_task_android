package com.challenge.task.presentation.domain

import com.challenge.task.presentation.domain.entity.User
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getUsers(): List<User> {
        val response = userRepository.getUsers(page = null)
        return userRepository.getUsers(page = response.meta.pagination.pages)
            .data
            .map { user ->
                User(
                    id = user.id,
                    name = user.name,
                    email = user.email,
                    createdAt = System.currentTimeMillis()
                )
            }
    }

    suspend fun createUser(name: String, email: String) {
        return userRepository.createUser(name, email)
    }

    suspend fun deleteUser(userId: Long) {
        return userRepository.deleteUser(userId)
    }
}
