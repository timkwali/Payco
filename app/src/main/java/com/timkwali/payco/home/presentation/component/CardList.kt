package com.timkwali.payco.home.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timkwali.payco.core.domain.model.Card

@Composable
fun CardList(
    cards: List<Card>,
    onCardClick: (Card) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 16.dp)
    ) {
        items(cards) { card ->
            CardListItem(card, onCardClick = { onCardClick(card) })
            if(cards.indexOf(card) != cards.lastIndex) {
                Divider(modifier = Modifier.height(1.dp).fillMaxWidth(), color = colorScheme.secondary)
            }
        }

    }
}