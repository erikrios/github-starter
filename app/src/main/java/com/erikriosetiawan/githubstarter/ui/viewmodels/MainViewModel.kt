package com.erikriosetiawan.githubstarter.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erikriosetiawan.githubstarter.repositories.UserRepository
import com.erikriosetiawan.githubstarter.ui.viewstate.UserViewState
import com.erikriosetiawan.githubstarter.ui.viewstate.UsersViewState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _usersViewState = MutableLiveData<UsersViewState>().apply {
        value = UsersViewState()
    }

    val usersViewState: LiveData<UsersViewState>
        get() = _usersViewState

    private val _userViewState = MutableLiveData<UserViewState>().apply {
        value = UserViewState()
    }

    val userViewState: LiveData<UserViewState>
        get() = _userViewState

    init {
        getUsers()
    }

    private fun getUsers(): Job = viewModelScope.launch {
        _usersViewState.value = UsersViewState(loading = true)

        try {
            val response = repository.getUsers()

            when {
                response.isSuccessful -> {
                    _usersViewState.value =
                        UsersViewState(
                            loading = false,
                            responseCode = response.code(),
                            users = response.body()
                        )
                }
                else -> {
                    _usersViewState.value = UsersViewState(
                        loading = false,
                        responseCode = response.code(),
                        exception = Exception("Failed to get data with response code ${response.code()}")
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _usersViewState.value = UsersViewState(loading = false, exception = Exception(e))
        }
    }

    fun getUserDetails(username: String): Job = viewModelScope.launch {
        _userViewState.value = UserViewState(loading = true)

        try {
            val response = repository.getUserDetails(username)

            when {
                response.isSuccessful -> {
                    _userViewState.value =
                        UserViewState(
                            loading = false,
                            responseCode = response.code(),
                            user = response.body()
                        )
                }
                else -> {
                    _userViewState.value = UserViewState(
                        loading = false,
                        responseCode = response.code(),
                        exception = Exception("Failed to get data with response code ${response.code()}")
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _userViewState.value = UserViewState(loading = false, exception = Exception(e))
        }
    }
}