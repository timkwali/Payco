package com.timkwali.payco.core.domain.model

import com.timkwali.payco.core.utils.groupByThrees

data class Card(
    val id: Int,
    val cardNumber: String,
    val cvv: String,
    val expiryDate: String,
    val amount: Int
) {
    val spacedNumber = cardNumber.chunked(4).joinToString(" ")
    val maskedNumber =  "**** **** **** ${cardNumber.takeLast(4)}"
    val typeOfCard = getCardType()
    val formatedAmount = amount.groupByThrees()

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
