package com.timkwali.payco.carddetails.presentation.viewmodel

sealed class CardDetailsEvent {
    data class DeleteCard(val id: Int): CardDetailsEvent()
    data object Refresh: CardDetailsEvent()
}