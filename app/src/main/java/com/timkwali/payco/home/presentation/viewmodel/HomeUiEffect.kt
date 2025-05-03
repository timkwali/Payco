package com.timkwali.payco.home.presentation.viewmodel

import com.timkwali.payco.home.domain.model.Card
import com.timkwali.payco.login.presentation.viewmodel.LoginUiEffect

sealed class HomeUiEffect {
    data class ShowSnackbar(val message: String) : HomeUiEffect()
    data class NavigateToCardDetails(val card: Card): HomeUiEffect()
    object NavigateToAddCard : HomeUiEffect()
}


