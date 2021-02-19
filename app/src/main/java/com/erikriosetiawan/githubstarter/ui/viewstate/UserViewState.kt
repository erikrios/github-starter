package com.erikriosetiawan.githubstarter.ui.viewstate

import com.erikriosetiawan.githubstarter.models.User

data class UserViewState(
    var loading: Boolean = false,
    var responseCode: Int? = null,
    var user: User? = null,
    var exception: Exception? = null
)
