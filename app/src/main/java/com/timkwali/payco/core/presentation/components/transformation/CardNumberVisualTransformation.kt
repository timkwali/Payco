package com.timkwali.payco.core.presentation.components.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text.take(16)
        val formatted = StringBuilder()
        val originalToTransformedMap = mutableListOf<Int>()
        var transformedIndex = 0

        for (i in original.indices) {
            if (i > 0 && i % 4 == 0) {
                formatted.append(' ')
                transformedIndex++
            }
            formatted.append(original[i])
            originalToTransformedMap.add(transformedIndex)
            transformedIndex++
        }

        val transformedText = formatted.toString()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return if (offset <= originalToTransformedMap.lastIndex) {
                    originalToTransformedMap[offset]
                } else {
                    transformedText.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = 0
                var count = 0
                while (count < offset && originalOffset < original.length) {
                    if (originalOffset % 4 == 0 && originalOffset != 0) count++
                    count++
                    originalOffset++
                }
                return originalOffset.coerceAtMost(original.length)
            }
        }

        return TransformedText(AnnotatedString(transformedText), offsetMapping)
    }
}