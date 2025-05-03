package com.timkwali.payco.core.presentation.components.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.timkwali.payco.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaycoAppBar(
    title: String = "This is a title",
    @DrawableRes navigationIcon: Int = R.drawable.ic_back,
    actions: @Composable() (RowScope.() -> Unit) = {},
    modifier: Modifier = Modifier,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
    onClick: () -> Unit = { },
) {

    CenterAlignedTopAppBar(
        colors = colors,
        navigationIcon = {
            IconButton(onClick = { onClick.invoke() }) {
                Icon(
                    modifier = Modifier
                        .width(20.dp),
                    painter = painterResource(id = navigationIcon),
                    contentDescription = title,
                    tint = colorScheme.secondary
                )
            }
        },
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 25.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
                    .copy(color = colorScheme.secondary)
            )
        },

        actions = actions,
        modifier = modifier

    )
}