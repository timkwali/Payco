package com.timkwali.payco.carddetails.presentation.viewmodel

sealed class CardDetailsUiEffect {
    data class ShowSnackbar(val message: String) : CardDetailsUiEffect()
    data object BackToDashboard : CardDetailsUiEffect()
}