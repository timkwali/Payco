package com.timkwali.payco.home.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.SubTitleText
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.domain.model.CardType

@Composable
fun CardListItem(
    card: Card,
    onCardClick: (Card) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable { onCardClick(card) }
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val image = when(card.typeOfCard) {
            CardType.MasterCard -> R.drawable.ic_master_card
            CardType.Visa -> R.drawable.ic_visa
            else -> R.drawable.ic_card
        }
        Image(
            painter = painterResource(image),
            contentDescription = "Card Image",
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        SubTitleText(text = card.maskedNumber, modifier = Modifier.weight(1f))

        Spacer(modifier = Modifier.width(5.dp))

        BodyText(text = "$${card.formatedAmount}")
    }
}