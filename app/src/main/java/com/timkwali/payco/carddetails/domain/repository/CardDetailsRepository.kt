package com.timkwali.payco.carddetails.domain.repository

import com.timkwali.payco.core.data.api.model.UserCard

interface CardDetailsRepository {
    suspend fun deleteCard(id: Int): Boolean
    suspend fun getCardById(id: Int): UserCard?
}