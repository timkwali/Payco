package com.timkwali.payco.addcard.data.repository

import com.timkwali.payco.addcard.domain.repository.AddCardRepository
import com.timkwali.payco.core.data.api.PaycoApi
import com.timkwali.payco.core.data.api.model.UserCardResponse

class AddCardRepositoryImpl(
    private val paycoApi: PaycoApi
): AddCardRepository {
    override suspend fun addCard(cardNumber: String, cvv: String, expiryDate: String): UserCardResponse? {
        return paycoApi.addCard(cardNumber, cvv, expiryDate)
    }
}