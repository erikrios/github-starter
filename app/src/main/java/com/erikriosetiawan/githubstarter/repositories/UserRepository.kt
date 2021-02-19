package com.erikriosetiawan.githubstarter.repositories

import com.erikriosetiawan.githubstarter.services.ServiceBuilder
import com.erikriosetiawan.githubstarter.services.UserService

class UserRepository {

    private val userService = ServiceBuilder.buildService(UserService::class.java)

    suspend fun getUsers() = userService.getUsers()

    suspend fun getUserDetails(username: String) = userService.getUserDetails(username)
}