package com.erikriosetiawan.githubstarter.services

import com.erikriosetiawan.githubstarter.models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    /**
     * GET request to loads some users
     */
    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    /**
     * GET request to get the user details
     */
    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): Response<User>
}