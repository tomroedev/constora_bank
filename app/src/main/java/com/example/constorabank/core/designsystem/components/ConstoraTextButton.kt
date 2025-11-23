package com.example.constorabank.core.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme

@Composable
fun ConstoraTextButton(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    TextButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            stringResource(textRes),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ConstoraTextButtonPreview() {
    ConstoraBankTheme {
        ConstoraTextButton(
            textRes = R.string.preview_text,
            onClick = {}
        )
    }
}
