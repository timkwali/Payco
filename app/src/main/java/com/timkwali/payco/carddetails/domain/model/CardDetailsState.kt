package com.timkwali.payco.carddetails.domain.model

import com.timkwali.payco.core.domain.model.Card

data class CardDetailsState(
    val cardId: Int = 0,
    val name: String = "",
    val isLoading: Boolean = false,
    val card: Card? = null,
    val deleteCardResponse: Boolean = false,
)
