package com.timkwali.payco.carddetails.presentation.component

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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.carddetails.domain.model.CardDetailsState
import com.timkwali.payco.carddetails.presentation.viewmodel.CardDetailsEvent
import com.timkwali.payco.core.domain.model.CardType
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.TitleText
import java.util.Locale

@Composable
fun UserCardProfile(
    cardDetailsState: CardDetailsState,
    onEvent: (CardDetailsEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorScheme.onSecondary)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {

        val image = when(cardDetailsState.card?.typeOfCard) {
            CardType.MasterCard -> R.drawable.ic_master_card
            CardType.Visa -> R.drawable.ic_visa
            else -> R.drawable.ic_card
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Image(painter = painterResource(image), contentDescription = "card log", modifier = Modifier.size(70.dp))
            Spacer(modifier = Modifier.weight(1f))
            IconFromDrawable(R.drawable.ic_wifi, modifier = Modifier.size(24.dp))
        }

        Column(
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            BodyText(text = stringResource(R.string.card_number))
            Spacer(modifier = Modifier.height(5.dp))
            TitleText(text = cardDetailsState.card?.spacedNumber ?: "")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleBodyView(
                title = stringResource(R.string.name_heading),
                body = cardDetailsState.name.capitalize(Locale.ROOT)
            )

            Spacer(modifier = Modifier.width(10.dp))

            TitleBodyView(
                title = stringResource(R.string.exp_date_heading),
                body = cardDetailsState.card?.expiryDate ?: ""
            )

            Spacer(modifier = Modifier.width(10.dp))

            TitleBodyView(
                title = stringResource(R.string.cvv_heading),
                body = cardDetailsState.card?.cvv ?: ""
            )
        }
    }
}