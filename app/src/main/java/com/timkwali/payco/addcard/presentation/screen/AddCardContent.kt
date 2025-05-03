package com.timkwali.payco.addcard.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.addcard.domain.model.AddCardState
import com.timkwali.payco.addcard.presentation.viewmodel.AddCardEvent
import com.timkwali.payco.addcard.presentation.viewmodel.AddCardUiEffect
import com.timkwali.payco.core.presentation.components.button.PaycoButton
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.textfield.PaycoTextField
import com.timkwali.payco.core.utils.MAX_CARD_NUMBERS
import com.timkwali.payco.core.utils.MAX_CVV_NUMBERS

@Composable
fun AddCardContent(
    addCardState: AddCardState,
    effect: AddCardUiEffect?,
    onEvent: (AddCardEvent) -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {

    var rawDateInput by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            is AddCardUiEffect.ShowSnackbar -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            is AddCardUiEffect.BackToDashboard -> {
                Toast.makeText(context, "Card added successfully.", Toast.LENGTH_SHORT).show()
                onNavigate()
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        val trailingIcon = when {
            addCardState.cardNumber.isEmpty() -> null
            addCardState.cardNumber.first().isDigit() &&
                    addCardState.cardNumber.first().digitToInt() % 2 == 0 -> R.drawable.ic_master_card
            else -> R.drawable.ic_visa
        }

        PaycoTextField(
            value = addCardState.cardNumber,
            enabled = !addCardState.isLoading,
            onValueChange = { if(it.length <= MAX_CARD_NUMBERS) onEvent(AddCardEvent.OnCardNumberChange(it)) },
            leadingIcon = { IconFromDrawable(R.drawable.ic_numbers, modifier = Modifier.size(24.dp)) },
            trailingIcon = trailingIcon?.let { { Image(painterResource(it), contentDescription = "card logo", modifier = Modifier.size(24.dp)) } },
            label = { BodyText(text = stringResource(R.string.enter_card_number), color = Color.Black) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PaycoTextField(
                value = addCardState.cvv,
                enabled = !addCardState.isLoading,
                onValueChange = { if(it.length <= MAX_CVV_NUMBERS) onEvent(AddCardEvent.OnCvvChange(it)) },
                modifier = Modifier.weight(1f),
                leadingIcon = { IconFromDrawable(R.drawable.ic_key, modifier = Modifier.size(24.dp)) },
                label = { BodyText(text = stringResource(R.string.enter_cvv), color = Color.Black) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(4.dp))
            PaycoTextField(
                value = addCardState.expiryDate,
                enabled = !addCardState.isLoading,
                onValueChange = { chr ->
                    if(chr.length <= 5) {
//                        val digits = chr.filter { it.isDigit() }.take(4)
//                        rawDateInput = digits
                        onEvent(AddCardEvent.OnExpiryDateChange(formatExpiry(chr)))
                    }
                },
                modifier = Modifier.weight(1f),
                leadingIcon = { IconFromDrawable(R.drawable.ic_date, modifier = Modifier.size(24.dp)) },
                label = { BodyText(text = stringResource(R.string.enter_expiryDate), color = Color.Black) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        PaycoButton(
            text = stringResource(R.string.add_card),
            enabled = !addCardState.isLoading,
            onClick = { onEvent(AddCardEvent.Submit) }
        )

        if(addCardState.isLoading) {
            CircularProgressIndicator(
                color = colorScheme.secondary,
                modifier = Modifier.padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


fun formatExpiry(digits: String): String {
    val digitsOnly = digits.filter { it.isDigit() }
    return when {
        digitsOnly.length <= 2 -> digits
        else -> digitsOnly.substring(0, 2) + "/" + digitsOnly.substring(2)
    }
}