package com.challenge.task.data.network.api

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import com.challenge.task.data.network.entities.UsersResponse

interface UserApiService {

    @GET("users/")
    suspend fun getUsers(@Query("page") page: Int?): UsersResponse

    @POST("users")
    suspend fun createUser(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("gender") gender: String,
        @Query("status") status: String
    )

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Long): Response<*>
}