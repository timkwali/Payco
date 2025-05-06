package com.timkwali.payco.home.domain.repository

import com.timkwali.payco.core.data.api.model.UserCardResponse

interface HomeRepository {
    suspend fun getCards(): List<UserCardResponse>

    suspend fun getAmountToPay(): Int
}