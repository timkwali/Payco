package com.timkwali.payco.core.presentation.components.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ExpiryDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text.filter { it.isDigit() }.take(4)
        val formatted = StringBuilder()
        val originalToTransformedMap = mutableListOf<Int>()
        var transformedIndex = 0

        for (i in raw.indices) {
            if (i == 2) {
                formatted.append('/')
                transformedIndex++
            }
            formatted.append(raw[i])
            originalToTransformedMap.add(transformedIndex)
            transformedIndex++
        }

        val transformedText = formatted.toString()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset < originalToTransformedMap.size) {
                    originalToTransformedMap[offset]
                } else {
                    transformedText.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = 0
                var count = 0
                while (count < offset && originalOffset < raw.length) {
                    if (originalOffset == 2) count++
                    count++
                    originalOffset++
                }
                return originalOffset.coerceAtMost(raw.length)
            }
        }

        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }
}