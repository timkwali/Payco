package com.timkwali.payco.login.presentation.viewmodel

sealed class LoginEvent {
    data class OnEmailChange(val email: String): LoginEvent()
    data class OnPasswordChange(val password: String): LoginEvent()
    object Submit: LoginEvent()
}