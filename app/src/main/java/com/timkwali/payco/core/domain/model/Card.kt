package com.timkwali.payco.core.domain.model

data class Card(
    val id: Int,
    val cardNumber: String,
    val cvv: String,
    val expiryDate: String,
    val amount: Int
) {
    val maskedNumber =  "**** **** **** ${cardNumber.takeLast(4)}"
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
