package com.example.constorabank.core.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme

@Composable
fun ConstoraExtraSmallPromptText(
    @StringRes text: Int,
) {
    Text(
        text = stringResource(text),
        style = MaterialTheme.typography.labelLarge.copy(
            fontStyle = FontStyle.Italic
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ConstoraExtraSmallPromptTextPreview() {
    ConstoraBankTheme {
        ConstoraExtraSmallPromptText(R.string.preview_text)
    }
}
