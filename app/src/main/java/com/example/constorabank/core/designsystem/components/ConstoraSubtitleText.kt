package com.example.constorabank.core.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme

@Composable
fun ConstoraSubtitleText(
    @StringRes textRes: Int,
) {
    Text(
        text = stringResource(textRes),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Preview
@Composable
private fun ConstoraSubtitleTextPreview() {
    ConstoraBankTheme {
        ConstoraSubtitleText(R.string.preview_text)
    }
}
