package com.timkwali.payco.home.domain.model

import com.timkwali.payco.core.domain.model.Card

data class HomeState(
    val name: String = "",
    val email: String = "",
    val cards: List<Card> = emptyList(),
    val isLoading: Boolean = false,
) {
    val totalPortfolio: Int = getPortfolio()

    private fun getPortfolio(): Int {
        return cards.sumOf { c -> c.amount }
    }
}
