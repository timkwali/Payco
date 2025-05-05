package com.timkwali.payco.addcard.domain.repository

import com.timkwali.payco.core.data.api.model.UserCardResponse

interface AddCardRepository {
    suspend fun addCard(
        cardNumber: String,
        cvv: String,
        expiryDate: String
    ): UserCardResponse?
}