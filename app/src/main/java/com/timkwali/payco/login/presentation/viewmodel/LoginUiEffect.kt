package com.timkwali.payco.login.presentation.viewmodel

sealed class LoginUiEffect {
    object NavigateToDashboard : LoginUiEffect()
    data class ShowSnackbar(val message: String) : LoginUiEffect()
}