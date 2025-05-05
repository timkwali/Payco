package com.timkwali.payco.carddetails.data.repository

import com.timkwali.payco.carddetails.domain.repository.CardDetailsRepository
import com.timkwali.payco.core.data.api.PaycoApi
import com.timkwali.payco.core.data.api.model.UserCardResponse

class CardDetailsRepositoryImpl(
    private val paycoApi: PaycoApi
): CardDetailsRepository {
    override suspend fun deleteCard(id: Int): Boolean {
        return paycoApi.deleteCard(id)
    }

    override suspend fun getCardById(id: Int): UserCardResponse? {
        return paycoApi.getCardById(id)
    }
}