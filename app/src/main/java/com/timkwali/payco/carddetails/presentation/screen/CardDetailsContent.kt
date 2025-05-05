package com.timkwali.payco.carddetails.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.carddetails.domain.model.CardDetailsState
import com.timkwali.payco.carddetails.presentation.component.UserCardProfile
import com.timkwali.payco.carddetails.presentation.viewmodel.CardDetailsEvent
import com.timkwali.payco.carddetails.presentation.viewmodel.CardDetailsUiEffect
import com.timkwali.payco.core.presentation.components.button.PaycoButton
import com.timkwali.payco.core.presentation.components.progress.PaycoCircularProgress

@Composable
fun CardDetailsContent(
    cardDetailsState: CardDetailsState,
    onEvent: (CardDetailsEvent) -> Unit,
    effect: CardDetailsUiEffect?,
    onNavigate: () -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            is CardDetailsUiEffect.ShowSnackbar -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            is CardDetailsUiEffect.BackToDashboard -> {
                Toast.makeText(context, R.string.successfully_delete_card, Toast.LENGTH_SHORT).show()
                onNavigate()
            }
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        onEvent(CardDetailsEvent.Refresh)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {

        UserCardProfile(
            cardDetailsState = cardDetailsState,
            onEvent = { onEvent(it) },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(50.dp))

        PaycoButton(
            text = stringResource(R.string.delete_card),
            onClick = { onEvent(CardDetailsEvent.DeleteCard(cardDetailsState.cardId))},
            enabled = !cardDetailsState.isLoading
        )

        if(cardDetailsState.isLoading) {
            PaycoCircularProgress(
                modifier = Modifier.padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}