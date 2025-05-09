package com.timkwali.payco.home.domain.model

import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.utils.formatAmount

data class HomeState(
    val name: String = "",
    val email: String = "",
    val cards: List<Card> = emptyList(),
    val amountToPay: Int = 0,
    val isLoading: Boolean = false,
) {
    val formatedAmount: String = amountToPay.formatAmount()
}
