package com.challenge.task.presentation.user.domain

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.challenge.task.data.network.api.UserApiService
import com.challenge.task.data.network.entities.UsersResponse
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApiService
) {
    suspend fun getUsers(page: Int?): UsersResponse {
        return withContext(Dispatchers.IO) {
            Log.e("Page ==>", page.toString() + "==>Get")
            userApi.getUsers(page)
        }
    }

    suspend fun createUser(name: String, email: String) {
        return withContext(Dispatchers.IO) {
            Log.e("Create ==>", "User")
            userApi.createUser(name = name, email = email, gender = "male", status = "active")
        }
    }

    suspend fun deleteUser(userId: Long) {
        return withContext(Dispatchers.IO) {
            Log.e("Delete ==>", "User")
            userApi.deleteUser(userId)
        }
    }
}