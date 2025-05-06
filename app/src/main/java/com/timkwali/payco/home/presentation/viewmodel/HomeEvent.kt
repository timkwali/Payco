package com.timkwali.payco.home.presentation.viewmodel

import com.timkwali.payco.core.domain.model.Card

sealed class HomeEvent {
    data class OnCardClick(val card: Card): HomeEvent()
    data object OnAddClick: HomeEvent()
    data object OnRefresh: HomeEvent()
    data object OnPayClick: HomeEvent()
}