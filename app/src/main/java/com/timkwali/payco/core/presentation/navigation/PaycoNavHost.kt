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
import com.timkwali.payco.home.presentation.screen.HomeScreen
import com.timkwali.payco.login.presentation.screen.LoginScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PaycoNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Routes.Login) {

        composable(Routes.Login) { LoginScreen(navController) }

        composable(
            route = "${Routes.Home}/{name}/{email}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { HomeScreen(it, navController) }

        composable(Routes.AddCard) { AddCardScreen(navController) }

        composable(
            route = "${Routes.CardDetails}/{name}/{cardId}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("cardId") { type = NavType.IntType }
            )
        ) { CardDetailsScreen(it, navController) }
    }
}