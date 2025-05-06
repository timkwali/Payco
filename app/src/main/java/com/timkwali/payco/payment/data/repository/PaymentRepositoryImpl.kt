package com.timkwali.payco.payment.data.repository

import com.timkwali.payco.core.data.api.PaycoApi
import com.timkwali.payco.core.data.api.model.UserCardResponse
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.payment.domain.repository.PaymentRepository

class PaymentRepositoryImpl(
    private val paycoApi: PaycoApi
): PaymentRepository {
    override suspend fun getCards(): List<UserCardResponse> {
        val cards = paycoApi.getCards()
        return cards
    }

    override suspend fun getAmountToPay(): Int {
        return paycoApi.getAmountToPay()
    }

    override suspend fun payAmount(card: Card?): Boolean {
        return paycoApi.payAmount(card)
    }
}