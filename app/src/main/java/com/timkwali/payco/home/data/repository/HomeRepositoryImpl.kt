package com.timkwali.payco.home.data.repository

import com.timkwali.payco.core.data.api.PaycoApi
import com.timkwali.payco.core.data.api.model.UserCardResponse
import com.timkwali.payco.home.domain.repository.HomeRepository

class HomeRepositoryImpl(
    private val dummyHomeApi: PaycoApi
): HomeRepository {
    override suspend fun getCards(): List<UserCardResponse> {
        val cards = dummyHomeApi.getCards()
        return cards
    }
}