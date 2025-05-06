package com.timkwali.payco.core.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.addcard.presentation.viewmodel.AddCardEvent
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.textfield.PaycoTextField
import com.timkwali.payco.core.presentation.components.transformation.CardNumberVisualTransformation
import com.timkwali.payco.core.presentation.components.transformation.ExpiryDateVisualTransformation
import com.timkwali.payco.core.utils.MAX_CARD_NUMBERS
import com.timkwali.payco.core.utils.MAX_CVV_NUMBERS

@Composable
fun CardForm(
    cardNumber: String,
    cvv: String,
    expiryDate: String,
    enabled: Boolean,
    onCardNumberChange: (String) -> Unit,
    onCvvChange: (String) -> Unit,
    onExpiryDateChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardFocusRequester = remember { FocusRequester() }
    val cvvFocusRequester = remember { FocusRequester() }
    val expiryFocusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
    ) {
        val trailingIcon = when {
            cardNumber.length < 4 -> null
            cardNumber.first().isDigit() &&
                    cardNumber.first().digitToInt() % 2 == 0 -> R.drawable.ic_master_card
            else -> R.drawable.ic_visa
        }

        PaycoTextField(
            value = cardNumber,
            enabled = enabled,
            onValueChange = {
                val digitsOnly = it.filter { it.isDigit() }
                if(digitsOnly.length <= MAX_CARD_NUMBERS) {
                    onCardNumberChange(digitsOnly)
                    if (digitsOnly.length == MAX_CARD_NUMBERS) cvvFocusRequester.requestFocus()
                }
            },
            leadingIcon = { IconFromDrawable(R.drawable.ic_numbers, modifier = Modifier.size(24.dp)) },
            trailingIcon = trailingIcon?.let { { Image(painterResource(it), contentDescription = "card logo", modifier = Modifier.size(24.dp)) } },
            label = { BodyText(text = stringResource(R.string.enter_card_number), color = colorScheme.tertiary.copy(alpha = 0.3f)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CardNumberVisualTransformation(),
            modifier = Modifier.fillMaxWidth().focusRequester(cardFocusRequester)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PaycoTextField(
                value = cvv,
                enabled = enabled,
                onValueChange = {
                    if(it.length <= MAX_CVV_NUMBERS) {
                        onCvvChange(it)
                        if (it.length == MAX_CVV_NUMBERS) expiryFocusRequester.requestFocus()
                    }
                },
                modifier = Modifier.weight(1f).focusRequester(cvvFocusRequester),
                leadingIcon = { IconFromDrawable(R.drawable.ic_key, modifier = Modifier.size(24.dp)) },
                label = { BodyText(text = stringResource(R.string.enter_cvv), color = colorScheme.tertiary.copy(alpha = 0.3f)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(4.dp))
            PaycoTextField(
                value = expiryDate,
                enabled = enabled,
                onValueChange = { it ->
                    val digitsOnly = it.filter { it.isDigit() }
                    if(digitsOnly.length <= 4) {
                        onExpiryDateChange(digitsOnly)
                    }
                },
                modifier = Modifier.weight(1f).focusRequester(expiryFocusRequester),
                leadingIcon = { IconFromDrawable(R.drawable.ic_date, modifier = Modifier.size(24.dp)) },
                label = { BodyText(text = stringResource(R.string.enter_expiryDate), color = colorScheme.tertiary.copy(alpha = 0.3f)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = ExpiryDateVisualTransformation()
            )
        }
    }
}