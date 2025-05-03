package com.timkwali.payco.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.timkwali.payco.addcard.presentation.screen.AddCardScreen
import com.timkwali.payco.home.presentation.screen.HomeScreen
import com.timkwali.payco.login.presentation.screen.LoginScreen

@Composable
fun PaycoNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "login") {

        composable(Routes.Login) { LoginScreen(navController) }

        composable(
            route = "${Routes.Home}/{name}/{email}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { HomeScreen(it, navController) }

        composable(Routes.AddCard) { AddCardScreen(navController) }
    }
}