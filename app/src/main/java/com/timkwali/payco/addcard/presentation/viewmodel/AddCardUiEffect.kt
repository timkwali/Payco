package com.timkwali.payco.addcard.presentation.viewmodel

sealed class AddCardUiEffect {
    data class ShowSnackbar(val message: String) : AddCardUiEffect()
    data object BackToDashboard : AddCardUiEffect()

}