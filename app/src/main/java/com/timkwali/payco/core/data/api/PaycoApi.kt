package com.timkwali.payco.core.data.api

import com.timkwali.payco.core.data.api.model.UserCardResponse
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.utils.SIMULATED_NETWORK_DELAY
import kotlinx.coroutines.delay

object PaycoApi {
    private var userCardResponses = mutableListOf<UserCardResponse>()

    private var tempCard: UserCardResponse? = null

    private var amountToPay = (1..1000).random()

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

    suspend fun getAmountToPay(): Int {
        delay(SIMULATED_NETWORK_DELAY)
        return amountToPay
    }

    suspend fun payAmount(card: Card?): Boolean {
        delay(SIMULATED_NETWORK_DELAY)

        var isExistingCard = false
        var existingCard: UserCardResponse? = null
        var index = 0
        for(i in userCardResponses.indices) {
            val v = userCardResponses[i]
            if(v.cardNumber == card?.cardNumber) {
                isExistingCard = true
                index = i
                existingCard = v
                break
            }
        }

        if(isExistingCard) {
            existingCard?.amount?.let {
                if(it < amountToPay) {
                    return false
                } else {
                    userCardResponses[index] = UserCardResponse(
                        existingCard?.id,
                        existingCard?.cardNumber,
                        existingCard?.cvv,
                        existingCard?.expiryDate,
                        amount = it - amountToPay
                    )
                    amountToPay = 0
                    return true
                }
            }
            return false
        } else {
            card?.let {
                if(card.cardNumber != tempCard?.cardNumber) {
                    tempCard = UserCardResponse(
                        id = (1..5000).random(),
                        cardNumber = card.cardNumber,
                        cvv = card.cvv,
                        expiryDate = card.expiryDate,
                        amount = (1..5000).random()
                    )

                    val newCardAmount = tempCard?.amount ?: 0
                    if(newCardAmount < amountToPay) return false
                    newCardAmount - amountToPay
                    amountToPay = 0
                    return true
                }
            }
            return false
        }
    }
}