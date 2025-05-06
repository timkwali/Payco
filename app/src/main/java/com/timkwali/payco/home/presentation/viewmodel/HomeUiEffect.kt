package com.timkwali.payco.home.presentation.viewmodel

import com.timkwali.payco.core.domain.model.Card

sealed class HomeUiEffect {
    data class ShowSnackbar(val message: String) : HomeUiEffect()
    data class NavigateToCardDetails(val card: Card): HomeUiEffect()
    object NavigateToAddCard : HomeUiEffect()
    object NavigateToPayment : HomeUiEffect()
}


