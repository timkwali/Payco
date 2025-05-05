package com.timkwali.payco.core.presentation.components.progress

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaycoCircularProgress(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        color = colorScheme.secondary,
        modifier = modifier.size(25.dp)
    )
}