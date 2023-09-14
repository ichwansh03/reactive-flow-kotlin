package com.ichwan.react.rxkotlin.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiState: StateFlow<LoginUiState> = _loginState

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginUiState.Loading
        delay(2000L)

        if (username == "ichwan" && password == "123a")
            _loginState.value = LoginUiState.Success
        else
            _loginState.value = LoginUiState.Error("Username or password is invalid")
    }

    sealed class LoginUiState {
        object Success : LoginUiState()
        object Loading : LoginUiState()
        object Empty : LoginUiState()
        data class Error(val message: String) : LoginUiState()
    }
}