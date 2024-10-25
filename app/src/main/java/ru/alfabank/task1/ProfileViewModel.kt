package ru.alfabank.task1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.alfabank.task1.repository.LoginData
import ru.alfabank.task1.repository.LoginResult
import ru.alfabank.task1.repository.Profile
import ru.alfabank.task1.repository.ProfileRepository

class ProfileViewModel(
    val repository: ProfileRepository
) : ViewModel() {

    var profileState = MutableStateFlow<Profile?>(null)
    var isLoginSuccess = MutableStateFlow(false)
    var loginData = MutableStateFlow(LoginData("", ""))
    var progressBarState = MutableStateFlow(false)
    var loginResult: LoginResult? = null

    fun login() {
        viewModelScope.launch {
            progressBarState.value = true
            loginResult = repository.login(loginData.value)
            isLoginSuccess.update { true }
            progressBarState.value = false
            getProfile()
        }
    }

    fun onPasswordChanged(password: String) {
        loginData.update { it.copy(password = password) }
    }

    fun onLoginChanged(login: String) {
        loginData.update { it.copy(login = login) }
    }

    private fun getProfile() {
        viewModelScope.launch {
            progressBarState.value = true
            profileState.value = repository.getProfile(loginResult!!.userId, loginResult!!.token)
            progressBarState.value = false
        }
    }
}
