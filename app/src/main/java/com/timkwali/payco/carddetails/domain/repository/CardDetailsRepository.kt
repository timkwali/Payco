package com.timkwali.payco.carddetails.domain.repository

import com.timkwali.payco.core.data.api.model.UserCardResponse

interface CardDetailsRepository {
    suspend fun deleteCard(id: Int): Boolean
    suspend fun getCardById(id: Int): UserCardResponse?
}