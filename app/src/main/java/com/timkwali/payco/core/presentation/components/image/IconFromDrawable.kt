package com.timkwali.payco.core.presentation.components.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconFromDrawable(
    drawable: Int,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    tint: Color = MaterialTheme.colorScheme.secondary,
    contentDescription: String = "",
    onClick: () -> Unit = {}
) {
    Icon(
        painter = painterResource(id = drawable),
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .size(size)
            .clickable { onClick() }
    )
}