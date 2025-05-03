package com.timkwali.payco.addcard.domain.repository

import com.timkwali.payco.core.data.api.model.UserCard

interface AddCardRepository {
    suspend fun addCard(
        cardNumber: String,
        cvv: String,
        expiryDate: String
    ): UserCard?
}