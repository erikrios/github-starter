package com.erikriosetiawan.githubstarter.ui.viewstate

import com.erikriosetiawan.githubstarter.models.User

data class UsersViewState(
    var loading: Boolean = false,
    var responseCode: Int? = null,
    var users: List<User>? = null,
    var exception: Exception? = null
)
