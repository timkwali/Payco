package com.timkwali.payco.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R
import com.timkwali.payco.core.presentation.components.image.IconFromDrawable
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.SubTitleText
import com.timkwali.payco.core.presentation.components.text.TitleText
import com.timkwali.payco.home.domain.model.HomeState
import java.util.Locale


@Composable
fun PortfolioSummary(
    homeState: HomeState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = colorScheme.onSecondary)
    ) {
        Row {
            TitleText(
                text = homeState.name.capitalize(Locale.ROOT),
                modifier = Modifier.weight(1f)
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            )

            IconFromDrawable(
                drawable = R.drawable.ic_person,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(50.dp))


        Column(
            modifier = modifier
                .padding(vertical = 16.dp, horizontal = 10.dp)
        ) {
            BodyText(
                text = stringResource(R.string.amount_owed)
            )
            SubTitleText(
                text = "$${homeState.formatedAmount}",
            )
        }
    }
}