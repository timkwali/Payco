package com.timkwali.payco.carddetails.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timkwali.payco.core.presentation.components.text.BodyText
import com.timkwali.payco.core.presentation.components.text.SubTitleText

@Composable
fun TitleBodyView(
    title: String,
    body: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        BodyText(text = title, fontSize = 12.sp)
        Spacer(modifier = Modifier.height(5.dp))
        SubTitleText(text = body, fontSize = 16.sp)
    }
}