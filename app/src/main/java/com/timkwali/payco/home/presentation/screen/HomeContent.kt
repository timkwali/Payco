package com.timkwali.payco.home.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.SubTitleText
import com.timkwali.payco.core.domain.model.Card
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.home.domain.model.HomeState
import com.timkwali.payco.home.presentation.component.CardList
import com.timkwali.payco.home.presentation.component.PortfolioSummary
import com.timkwali.payco.home.presentation.viewmodel.HomeEvent
import com.timkwali.payco.home.presentation.viewmodel.HomeUiEffect

@Composable
fun HomeContent(
    homeState: HomeState,
    effect: HomeUiEffect?,
    onAddCardNavigate: () -> Unit,
    onCardDetailsNavigate: (Card) -> Unit,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    LaunchedEffect(effect) {
        when (effect) {
            is HomeUiEffect.NavigateToAddCard -> onAddCardNavigate()
            is HomeUiEffect.NavigateToCardDetails -> onCardDetailsNavigate(effect.card)
            is HomeUiEffect.ShowSnackbar -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    LaunchedEffect(Unit) {
        onEvent(HomeEvent.OnRefresh)
    }

    Column(
        modifier = modifier
            .padding(all = 16.dp)
    ) {
        IconFromDrawable(
            drawable = R.drawable.ic_app_logo,
            tint = colorScheme.tertiary,
            size = 50.dp,
            contentDescription = stringResource(R.string.app_logo)
        )

        Spacer(modifier = Modifier.height(50.dp))

        PortfolioSummary(
            homeState = homeState
        )

        Spacer(modifier = Modifier.height(60.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            SubTitleText(text = stringResource(R.string.cards), modifier = Modifier.weight(1f))
            BodyText(text = homeState.cards.size.toString())
        }

        if(homeState.cards.isEmpty()) {
            BodyText(
                text = stringResource(R.string.no_cards),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 50.dp)
            )
        } else {
            CardList(
                cards = homeState.cards,
                onCardClick = { onEvent(HomeEvent.OnCardClick(it)) }
            )
        }
    }

}

@Preview
@Composable
fun HomeContentPreview() {
    HomeContent(
        homeState = HomeState(),
        effect = null,
        onEvent = {},
        onAddCardNavigate = {},
        onCardDetailsNavigate = {}
    )
}