package com.timkwali.payco.payment.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.presentation.components.button.PaycoButton
import com.timkwali.payco.core.presentation.components.card.CardForm
import com.timkwali.payco.core.presentation.components.progress.PaycoCircularProgress
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.SubTitleText
import com.timkwali.payco.home.presentation.component.CardList
import com.timkwali.payco.payment.domain.model.PaymentState
import com.timkwali.payco.payment.presentation.viewmodel.PaymentEvent
import com.timkwali.payco.payment.presentation.viewmodel.PaymentUiEffect

@Composable
fun PaymentContent(
    paymentState: PaymentState,
    effect: PaymentUiEffect?,
    onEvent: (PaymentEvent) -> Unit,
    onBackNavigate: () -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    val defaultCard = Card(0, "", "", "", 0)
    val context = LocalContext.current
    LaunchedEffect(effect) {
        when (effect) {
            is PaymentUiEffect.ShowSnackbar -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            is PaymentUiEffect.NavigateToDashboard -> {
                Toast.makeText(context, R.string.payment_successful, Toast.LENGTH_SHORT).show()
                onBackNavigate()
            }
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        onEvent(PaymentEvent.OnRefresh)
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Column {
            BodyText(text = stringResource(R.string.amount_to_pay))
            Spacer(modifier = Modifier.height(5.dp))
            SubTitleText(text = "$${paymentState.formatedAmount}")
        }

        Spacer(modifier = Modifier.height(24.dp))

        CardForm(
            cardNumber = paymentState.currentCard?.cardNumber ?: "",
            cvv = paymentState.currentCard?.cvv ?: "",
            expiryDate = paymentState.currentCard?.expiryDate ?: "",
            enabled = !paymentState.isLoading,
            modifier = Modifier,
            onExpiryDateChange = { onEvent(PaymentEvent.OnCurrentCardValueChange(card = paymentState.currentCard?.copy(expiryDate = it) ?: defaultCard)) },
            onCardNumberChange = { onEvent(PaymentEvent.OnCurrentCardValueChange(card = paymentState.currentCard?.copy(cardNumber = it) ?: defaultCard)) },
            onCvvChange = { onEvent(PaymentEvent.OnCurrentCardValueChange(card = paymentState.currentCard?.copy(cvv = it) ?: defaultCard)) },
        )

        Spacer(modifier = Modifier.height(24.dp))

        PaycoButton(
            text = "Pay $${paymentState.formatedAmount}",
            onClick = { onEvent(PaymentEvent.OnPay) },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
        )

        if(paymentState.isLoading) {
            Spacer(modifier = Modifier.height(10.dp))
            PaycoCircularProgress(modifier = Modifier.padding(top = 10.dp)
                .align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(15.dp))
        } else{
            Spacer(modifier = Modifier.height(50.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            SubTitleText(text = stringResource(R.string.pay_with_saved_card), modifier = Modifier.weight(1f))
            BodyText(text = paymentState.cards.size.toString())
        }

        Spacer(modifier = Modifier.height(24.dp))

        CardList(
            cards = paymentState.cards.reversed(),
            onCardClick = { onEvent(PaymentEvent.OnCardSelect(it)) },
        )
    }
}