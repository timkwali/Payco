package com.timkwali.payco.home.domain.model

import java.util.Objects

data class Card(
    val id: Int,
    val cardNumber: String,
    val cvv: String,
    val expiryDate: String,
    val amount: Int
) {
    val maskedNumber = retrieveMaskedNumber()
    private fun retrieveMaskedNumber(): String {
        var mask = ""
        cardNumber.forEachIndexed { i, char ->
            if(i >= cardNumber.lastIndex - 4) {
                mask += char
            } else mask += "*"
        }
        return mask
    }

    val typeOfCard = getCardType()
    private fun getCardType(): CardType? {
        if(cardNumber.isEmpty()) return null
        if(!cardNumber.first().isDigit()) return null

        return if((cardNumber.first().digitToInt() % 2) == 0) {
            CardType.MasterCard
        } else CardType.Visa
    }
}

sealed class CardType{
    object Visa: CardType()
    object MasterCard: CardType()
}
