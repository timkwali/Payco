package com.timkwali.payco.addcard.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.addcard.domain.model.AddCardState
import com.timkwali.payco.addcard.presentation.viewmodel.AddCardEvent
import com.timkwali.payco.addcard.presentation.viewmodel.AddCardUiEffect
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.presentation.components.button.PaycoButton
import com.timkwali.payco.core.presentation.components.card.CardForm
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.core.presentation.components.progress.PaycoCircularProgress
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.textfield.PaycoTextField
import com.timkwali.payco.core.presentation.components.transformation.CardNumberVisualTransformation
import com.timkwali.payco.core.presentation.components.transformation.ExpiryDateVisualTransformation
import com.timkwali.payco.core.utils.MAX_CARD_NUMBERS
import com.timkwali.payco.core.utils.MAX_CVV_NUMBERS

@Composable
fun AddCardContent(
    addCardState: AddCardState,
    effect: AddCardUiEffect?,
    onEvent: (AddCardEvent) -> Unit,
    onNavigate: () -> Unit,
    scrollState: ScrollState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            is AddCardUiEffect.ShowSnackbar -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            is AddCardUiEffect.BackToDashboard -> {
                Toast.makeText(context, R.string.successfully_add_card, Toast.LENGTH_SHORT).show()
                onNavigate()
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(scrollState)
    ) {
        CardForm(
            cardNumber = addCardState.cardNumber,
            cvv = addCardState.cvv,
            expiryDate = addCardState.expiryDate,
            enabled = !addCardState.isLoading,
            onCardNumberChange = { onEvent(AddCardEvent.OnCardNumberChange(it)) },
            onCvvChange = { onEvent(AddCardEvent.OnCvvChange(it)) },
            onExpiryDateChange = { onEvent(AddCardEvent.OnExpiryDateChange(it)) },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(50.dp))

        PaycoButton(
            text = stringResource(R.string.add_card),
            enabled = !addCardState.isLoading,
            onClick = { onEvent(AddCardEvent.Submit) }
        )

        if(addCardState.isLoading) {
            PaycoCircularProgress(
                modifier = Modifier.padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}