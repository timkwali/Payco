package com.timkwali.payco.carddetails.presentation.viewmodel

import com.timkwali.payco.core.data.api.model.UserCard

sealed class CardDetailsEvent {
    data class DeleteCard(val id: Int): CardDetailsEvent()
    data object Refresh: CardDetailsEvent()
}