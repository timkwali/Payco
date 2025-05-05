package com.timkwali.payco.home.presentation.viewmodel

import com.timkwali.payco.core.domain.model.Card

sealed class HomeEvent {
    data class OnCardClick(val card: Card): HomeEvent()
    object OnAddClick: HomeEvent()
    object OnRefresh: HomeEvent()
}