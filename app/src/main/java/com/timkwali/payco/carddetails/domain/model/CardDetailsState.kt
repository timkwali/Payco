package com.timkwali.payco.carddetails.domain.model

data class CardDetailsState(
    val cardId: Int = 0,
    val name: String = "",
    val isLoading: Boolean = false,
    val getCardResponse: Card? = null,
    val deleteCardResponse: Boolean = false,
)
