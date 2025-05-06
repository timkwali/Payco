package com.timkwali.payco.addcard.domain.model

import com.timkwali.payco.core.data.api.model.UserCardResponse
import com.timkwali.payco.core.domain.model.Card

data class AddCardState(
    val cardNumber: String = "",
    val cvv: String = "",
    val expiryDate: String = "",
    val isLoading: Boolean = false,
    val addCardResponse: UserCardResponse? = null
) {
    val card = addCardResponse?.run {
        Card(
            id = id ?: 0,
            cardNumber = cardNumber ?: "",
            cvv = cvv ?: "",
            expiryDate = expiryDate ?: "",
            amount = amount ?: 0,
        )
    }
}
