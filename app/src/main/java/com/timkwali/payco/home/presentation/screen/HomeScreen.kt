package com.timkwali.payco.home.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.timkwali.payco.core.presentation.components.button.PaycoFloatingActionButton
import com.timkwali.payco.core.presentation.navigation.Screen
import com.timkwali.payco.home.presentation.viewmodel.HomeEvent
import com.timkwali.payco.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(backStackEntry: NavBackStackEntry, navController: NavController) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val homeState = homeViewModel.homeState.collectAsStateWithLifecycle()
    val effect = homeViewModel.uiEffect.collectAsStateWithLifecycle(null)
    val name = backStackEntry.arguments?.getString("name") ?: ""
    val email = backStackEntry.arguments?.getString("email") ?: ""
    val scrollState = rememberScrollState()

    LaunchedEffect(name) {
        homeViewModel.updateUserInfo(name, email)
    }

    Scaffold(
        floatingActionButton = PaycoFloatingActionButton(
            onClick = { homeViewModel.onEvent(HomeEvent.OnAddClick) }
        )
    ) { contentPadding ->
        HomeContent(
            homeState = homeState.value,
            effect = effect.value,
            onEvent = { homeViewModel.onEvent(it) },
            onAddCardNavigate = { navController.navigate(Screen.AddCard.route) },
            onCardDetailsNavigate = { navController.navigate(Screen.CardDetailsWithArgs.createRoute(name = name, cardId = it.id)) },
            modifier = Modifier.padding(contentPadding),
            scrollState = scrollState
        )
    }

}