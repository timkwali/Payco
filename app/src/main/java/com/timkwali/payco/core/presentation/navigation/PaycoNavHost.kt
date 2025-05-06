package com.timkwali.payco.core.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.timkwali.payco.addcard.presentation.screen.AddCardScreen
import com.timkwali.payco.carddetails.presentation.screen.CardDetailsScreen
import com.timkwali.payco.core.utils.NAV_ARG_CARD_ID
import com.timkwali.payco.core.utils.NAV_ARG_EMAIL
import com.timkwali.payco.core.utils.NAV_ARG_NAME
import com.timkwali.payco.home.presentation.screen.HomeScreen
import com.timkwali.payco.login.presentation.screen.LoginScreen
import com.timkwali.payco.payment.presentation.screen.PaymentScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaycoNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) { LoginScreen(navController) }

        composable(
            route = Screen.HomeWithArgs.route,
            arguments = listOf(
                navArgument(NAV_ARG_NAME) { type = NavType.StringType },
                navArgument(NAV_ARG_EMAIL) { type = NavType.StringType }
            )
        ) { HomeScreen(it, navController) }

        composable(Screen.AddCard.route) { AddCardScreen(navController) }

        composable(
            route = Screen.CardDetailsWithArgs.route,
            arguments = listOf(
                navArgument(NAV_ARG_NAME) { type = NavType.StringType },
                navArgument(NAV_ARG_CARD_ID) { type = NavType.IntType }
            )
        ) { CardDetailsScreen(it, navController) }

        composable(Screen.Payment.route) { PaymentScreen(navController) }
    }
}