package com.timkwali.payco.login.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkwali.payco.core.utils.Resource
import com.timkwali.payco.login.domain.model.LoginState
import com.timkwali.payco.login.domain.usecase.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    val login: Login
) : ViewModel() {

    private var _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> get() = _loginState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<LoginUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()


    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.OnEmailChange -> { _loginState.value = _loginState.value.copy(email = event.email) }
            is LoginEvent.OnPasswordChange -> { _loginState.value = _loginState.value.copy(password = event.password) }
            is LoginEvent.Submit -> submit()
        }
    }

    private fun submit() = viewModelScope.launch(Dispatchers.IO) {
        login.invoke(
            email = loginState.value.email.trim(),
            password = loginState.value.password.trim()
        ).collectLatest {
            _loginState.value = _loginState.value.copy(isLoading = it is Resource.Loading)
            when(it) {
                is Resource.Success<*> -> {
                    it.data?.let { loginResponse -> _loginState.value = _loginState.value.copy(loginResponse = loginResponse) }
                    _uiEffect.emit(LoginUiEffect.NavigateToDashboard)
                }
                is Resource.Error<*> -> _uiEffect.emit(LoginUiEffect.ShowSnackbar(it.message ?: "Error logging in."))
                else -> Unit
            }
        }
    }
}