package com.timkwali.payco.payment.presentation.viewmodel

import com.timkwali.payco.core.domain.model.Card

sealed class PaymentEvent {
    data class OnCurrentCardValueChange(val card: Card): PaymentEvent()
    data class OnCardSelect(val card: Card): PaymentEvent()
    data object OnPay: PaymentEvent()
    data object OnRefresh: PaymentEvent()
}