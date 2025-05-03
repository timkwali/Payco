package com.timkwali.payco.carddetails.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.carddetails.domain.model.CardDetailsState
import com.timkwali.payco.carddetails.domain.model.CardType
import com.timkwali.payco.carddetails.presentation.component.TitleBodyView
import com.timkwali.payco.carddetails.presentation.component.UserCardProfile
import com.timkwali.payco.carddetails.presentation.viewmodel.CardDetailsEvent
import com.timkwali.payco.carddetails.presentation.viewmodel.CardDetailsUiEffect
import com.timkwali.payco.core.presentation.components.button.PaycoButton
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.TitleText
import com.timkwali.payco.home.presentation.viewmodel.HomeEvent
import com.timkwali.payco.home.presentation.viewmodel.HomeUiEffect

@Composable
fun CardDetailsContent(
    cardDetailsState: CardDetailsState,
    onEvent: (CardDetailsEvent) -> Unit,
    effect: CardDetailsUiEffect?,
    onNavigate: () -> Unit,
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
        modifier = modifier.padding(horizontal = 16.dp)
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
            CircularProgressIndicator(
                color = colorScheme.secondary,
                modifier = Modifier.padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}