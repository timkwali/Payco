package com.timkwali.payco.payment.domain.model

import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.utils.formatAmount

data class PaymentState(
    val currentCard: Card? = null,
    val cards: List<Card> = emptyList(),
    val amountToPay: Int = 0,
    val isLoading: Boolean = false,
) {
    val formatedAmount = amountToPay.formatAmount()
}
