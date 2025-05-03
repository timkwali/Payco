package com.timkwali.payco.addcard.presentation.viewmodel

sealed class AddCardEvent {
    data class OnCardNumberChange(val cardNumber: String): AddCardEvent()
    data class OnCvvChange(val cvv: String): AddCardEvent()
    data class OnExpiryDateChange(val expiryDate: String): AddCardEvent()
    object Submit: AddCardEvent()
}