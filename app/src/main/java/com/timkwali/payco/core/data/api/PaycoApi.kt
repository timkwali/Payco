package com.timkwali.payco.core.data.api

import com.timkwali.payco.core.utils.SIMULATED_NETWORK_DELAY
import com.timkwali.payco.core.data.api.model.UserCard
import kotlinx.coroutines.delay

object PaycoApi {
    private var userCards = mutableListOf<UserCard>()

    suspend fun getCards(): List<UserCard> {
        delay(SIMULATED_NETWORK_DELAY)
        return userCards
    }

    suspend fun addCard(
        cardNumber: String?,
        cvv: String?,
        expiryDate: String?,
    ): UserCard? {
        delay(SIMULATED_NETWORK_DELAY)
        if (userCards.any { it.cardNumber == cardNumber }) return null

        var finalId = 0
        val existingIds = userCards.map { it.id }.toSet()
        while (existingIds.contains(finalId)) {
            finalId = (1..5000).random()
        }

        val newCard = UserCard(
            id = finalId,
            cardNumber = cardNumber,
            cvv = cvv,
            expiryDate = expiryDate,
            amount = (1..5000).random()
        )
        userCards.add(newCard)
        return newCard
    }

    suspend fun getCardById(id: Int): UserCard? {
        delay(SIMULATED_NETWORK_DELAY)
        var userCard: UserCard? = null
        userCards.forEach {
            if(it.id == id) userCard = it
        }
        return userCard
    }

    suspend fun deleteCard(id: Int): Boolean {
        delay(SIMULATED_NETWORK_DELAY)
        userCards.forEach {
            if(it.id == id) userCards.remove(it)
            return true
        }
        return false
    }
}