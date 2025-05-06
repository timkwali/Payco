package com.timkwali.payco.payment.domain.repository

import com.timkwali.payco.core.data.api.model.UserCardResponse
import com.timkwali.payco.core.domain.model.Card

interface PaymentRepository {
    suspend fun getCards(): List<UserCardResponse>

    suspend fun getAmountToPay(): Int

    suspend fun payAmount(card: Card?): Boolean
}