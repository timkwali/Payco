package com.timkwali.payco.core.presentation.navigation

import com.timkwali.payco.core.utils.NAV_ARG_CARD_ID
import com.timkwali.payco.core.utils.NAV_ARG_EMAIL
import com.timkwali.payco.core.utils.NAV_ARG_NAME
import com.timkwali.payco.core.utils.NAV_ROUTE_ADD_CARD
import com.timkwali.payco.core.utils.NAV_ROUTE_CARD_DETAILS
import com.timkwali.payco.core.utils.NAV_ROUTE_HOME
import com.timkwali.payco.core.utils.NAV_ROUTE_LOGIN
import com.timkwali.payco.core.utils.NAV_ROUTE_PAYMENT

sealed class Screen(val route: String) {
    data object Login: Screen(NAV_ROUTE_LOGIN)

    data object HomeWithArgs: Screen("$NAV_ROUTE_HOME/{${NAV_ARG_NAME}}/{$NAV_ARG_EMAIL}") {
        fun createRoute(name: String, email: String) = "$NAV_ROUTE_HOME/$name/$email"
    }

    data object AddCard: Screen(NAV_ROUTE_ADD_CARD)

    data object CardDetailsWithArgs: Screen("$NAV_ROUTE_CARD_DETAILS/{${NAV_ARG_NAME}}/{${NAV_ARG_CARD_ID}}") {
        fun createRoute(name: String, cardId: Int) = "$NAV_ROUTE_CARD_DETAILS/$name/$cardId"
    }

    data object Payment: Screen(NAV_ROUTE_PAYMENT)

}