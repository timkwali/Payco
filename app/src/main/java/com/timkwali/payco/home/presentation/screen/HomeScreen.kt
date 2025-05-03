package com.timkwali.payco.home.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.timkwali.payco.core.presentation.components.button.PaycoFloatingActionButton
import com.timkwali.payco.core.presentation.navigation.Routes
import com.timkwali.payco.home.domain.model.Card
import com.timkwali.payco.home.domain.model.HomeState
import com.timkwali.payco.home.presentation.viewmodel.HomeEvent
import com.timkwali.payco.home.presentation.viewmodel.HomeUiEffect
import com.timkwali.payco.home.presentation.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(backStackEntry: NavBackStackEntry, navController: NavController) {
    val homeViewModel: HomeViewModel = koinViewModel()
    val homeState = homeViewModel.homeState.collectAsStateWithLifecycle()
    val effect = homeViewModel.uiEffect.collectAsStateWithLifecycle(null)
    val name = backStackEntry.arguments?.getString("name") ?: ""
    val email = backStackEntry.arguments?.getString("email") ?: ""

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
            onAddCardNavigate = { navController.navigate(Routes.AddCard) },
            onCardDetailsNavigate = { navController.navigate("${Routes.CardDetails}/$name/${it.id}") },
            modifier = Modifier.padding(contentPadding)
        )
    }

}