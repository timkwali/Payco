package com.timkwali.payco.carddetails.presentation.viewmodel

import com.timkwali.payco.addcard.presentation.viewmodel.AddCardUiEffect

sealed class CardDetailsUiEffect {
    data class ShowSnackbar(val message: String) : CardDetailsUiEffect()
    data object BackToDashboard : CardDetailsUiEffect()
}