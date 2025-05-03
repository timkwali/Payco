package com.timkwali.payco.addcard.presentation.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.timkwali.payco.R
import com.timkwali.payco.addcard.presentation.viewmodel.AddCardViewModel
import com.timkwali.payco.core.presentation.components.appbar.PaycoAppBar
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    navController: NavHostController
) {
    val addCardViewModel: AddCardViewModel = koinViewModel()
    val addCardState = addCardViewModel.addCardState.collectAsStateWithLifecycle()
    val uiEffect = addCardViewModel.uiEffect.collectAsStateWithLifecycle(null)

    Scaffold(
        topBar = { PaycoAppBar(
            title = stringResource(R.string.add_card),
            onClick = { navController.popBackStack() }
        )}
    ) { contentPadding ->
        AddCardContent(
            addCardState = addCardState.value,
            effect = uiEffect.value,
            onEvent = { addCardViewModel.onEvent(event = it) },
            onNavigate = { navController.popBackStack() },
            modifier = Modifier.padding(contentPadding)
        )
    }
}