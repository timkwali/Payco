package com.timkwali.payco.core.presentation.components.button

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.timkwali.payco.core.presentation.components.text.BodyText

@Composable
fun PaycoButton(
    modifier: Modifier = Modifier.fillMaxWidth(),
    shape: CornerBasedShape = shapes.medium,
    text: String = "button",
    maxLines: Int = 2,
    contentPaddingValues: PaddingValues = PaddingValues(
        top = 17.dp,
        bottom = 17.dp,
        start = 20.dp,
        end = 20.dp
    ),
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = colorScheme.secondary,
        disabledContainerColor = colorScheme.secondary.copy(alpha = 0.3f)
    ),
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        interactionSource = interactionSource,
        colors = colors,
        contentPadding = contentPaddingValues,
        enabled = enabled
    ) {
        BodyText(
            text = text,
            overflow = TextOverflow.Ellipsis,
            textStyle = textStyle,
            textAlign = TextAlign.Center,
            color = colorScheme.primary
        )
    }
}