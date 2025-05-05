package com.timkwali.payco.carddetails.presentation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.timkwali.payco.R
import com.timkwali.payco.carddetails.presentation.viewmodel.CardDetailsViewModel
import com.timkwali.payco.core.presentation.components.appbar.PaycoAppBar
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDetailsScreen(
    backStackEntry: NavBackStackEntry,
    navController: NavController
) {
    val cardDetailsViewModel: CardDetailsViewModel = koinViewModel()
    val cardDetailsState = cardDetailsViewModel.cardDetailsState.collectAsStateWithLifecycle()
    val effect = cardDetailsViewModel.uiEffect.collectAsStateWithLifecycle(null)
    val name = backStackEntry.arguments?.getString("name") ?: ""
    val cardId = backStackEntry.arguments?.getInt("cardId") ?: 0
    val scrollState = rememberScrollState()

    LaunchedEffect(name) {
        cardDetailsViewModel.updateCardDetailsState(cardId, name)
    }


    Scaffold(
        topBar = { PaycoAppBar(
            title = stringResource(R.string.card_details),
            onClick = { navController.popBackStack() })
        }
    ) { contentPadding ->
        CardDetailsContent(
            cardDetailsState = cardDetailsState.value,
            onEvent = { cardDetailsViewModel.onEvent(it) },
            effect = effect.value,
            modifier = Modifier.padding(contentPadding),
            onNavigate = { navController.popBackStack() },
            scrollState = scrollState
        )
    }
}