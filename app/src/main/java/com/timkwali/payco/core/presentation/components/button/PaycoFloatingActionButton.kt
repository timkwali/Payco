package com.timkwali.payco.core.presentation.components.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PaycoFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
): @Composable () -> Unit {
    return { FloatingActionButton(
        onClick = { onClick() },
        containerColor = colorScheme.secondary,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add"
        )
    } }
}