package com.timkwali.payco.core.presentation.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun SubTitleText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 24.sp,
    color: Color = MaterialTheme.colorScheme.secondary,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            color = color,
            fontWeight = fontWeight
        ),
        modifier = modifier
    )
}