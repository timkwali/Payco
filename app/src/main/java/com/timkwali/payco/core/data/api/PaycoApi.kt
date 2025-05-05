package com.timkwali.payco.core.data.api

import com.timkwali.payco.core.utils.SIMULATED_NETWORK_DELAY
import com.timkwali.payco.core.data.api.model.UserCardResponse
import kotlinx.coroutines.delay

object PaycoApi {
    private var userCardResponses = mutableListOf<UserCardResponse>()

    suspend fun getCards(): List<UserCardResponse> {
        delay(SIMULATED_NETWORK_DELAY)
        return userCardResponses
    }

    suspend fun addCard(
        cardNumber: String?,
        cvv: String?,
        expiryDate: String?,
    ): UserCardResponse? {
        delay(SIMULATED_NETWORK_DELAY)
        if (userCardResponses.any { it.cardNumber == cardNumber }) return null

        var finalId = 0
        val existingIds = userCardResponses.map { it.id }.toSet()
        while (existingIds.contains(finalId)) {
            finalId = (1..5000).random()
        }

        val newCard = UserCardResponse(
            id = finalId,
            cardNumber = cardNumber,
            cvv = cvv,
            expiryDate = expiryDate,
            amount = (1..5000).random()
        )
        userCardResponses.add(newCard)
        return newCard
    }

    suspend fun getCardById(id: Int): UserCardResponse? {
        delay(SIMULATED_NETWORK_DELAY)
        var userCardResponse: UserCardResponse? = null
        userCardResponses.forEach {
            if(it.id == id) userCardResponse = it
        }
        return userCardResponse
    }

    suspend fun deleteCard(id: Int): Boolean {
        delay(SIMULATED_NETWORK_DELAY)
        userCardResponses.forEach {
            if(it.id == id) {
                userCardResponses.remove(it)
                return true
            }
        }
        return false
    }
}