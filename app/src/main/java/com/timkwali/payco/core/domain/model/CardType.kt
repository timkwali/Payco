package com.timkwali.payco.core.domain.model

sealed class CardType{
    data object Visa: CardType()
    data object MasterCard: CardType()
}