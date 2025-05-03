package com.timkwali.payco.carddetails.domain.model

data class Card(
    val id: Int,
    val cardNumber: String,
    val cvv: String,
    val expiryDate: String,
    val amount: Int
) {
    val spacedNumber = retrievedSpacedNumber()
    val maskedNumber = "**** **** **** ${cardNumber.takeLast(4)}"
    val typeOfCard = getCardType()

    private fun getCardType(): CardType? {
        if(cardNumber.isEmpty()) return null
        if(!cardNumber.first().isDigit()) return null

        return if((cardNumber.first().digitToInt() % 2) == 0) {
            CardType.MasterCard
        } else CardType.Visa
    }

    private fun retrievedSpacedNumber(): String {
        var num = ""
        cardNumber.forEachIndexed { i, char ->
            if(i == 0) num += char else {
                if(i % 4 == 0) {
                    num += " $char"
                } else num += char
            }
        }
        return num
    }
}

sealed class CardType{
    object Visa: CardType()
    object MasterCard: CardType()
}
