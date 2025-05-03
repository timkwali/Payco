package com.timkwali.payco.addcard.domain.model

import com.timkwali.payco.core.data.api.model.UserCard

data class AddCardState(
    val cardNumber: String = "",
    val cvv: String = "",
    val expiryDate: String = "",
    val isLoading: Boolean = false,
    val addCardResponse: UserCard? = null
)
