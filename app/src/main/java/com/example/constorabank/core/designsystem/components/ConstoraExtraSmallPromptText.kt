package com.example.constorabank.core.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ConstoraExtraSmallPromptText(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Italic
        )
    )
}
