package com.timkwali.payco.payment.presentation.viewmodel

sealed class PaymentUiEffect {
    data class ShowSnackbar(val message: String) : PaymentUiEffect()
    data object NavigateToDashboard: PaymentUiEffect()
}