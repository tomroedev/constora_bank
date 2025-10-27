package com.example.constorabank.core.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens
import com.example.constorabank.core.designsystem.Secondary700

private const val DISABLED_CONTAINER_ALPHA = 0.8f

@Composable
fun ConstoraButton(
    onClick: () -> Unit,
    @StringRes text:  Int,
    enabled: Boolean = true,
    filled: Boolean = true
    ) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.ButtonHeight),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(Dimens.CornerRadius),
        colors =
            ButtonDefaults.buttonColors(
            containerColor =
                if (filled) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.tertiary,
            contentColor =
                if (filled) MaterialTheme.colorScheme.onPrimary
                else Secondary700,
            disabledContainerColor =
                if (filled) MaterialTheme.colorScheme.primary.copy(alpha = DISABLED_CONTAINER_ALPHA)
                else MaterialTheme.colorScheme.tertiary.copy(alpha = DISABLED_CONTAINER_ALPHA)
        ),
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConstoraButtonPreview() {
    ConstoraBankTheme {
        ConstoraButton(onClick = {}, text = R.string.preview_text)
    }
}

@Preview(showBackground = true)
@Composable
fun ConstoraButtonDisabledPreview() {
    ConstoraBankTheme {
        ConstoraButton(onClick = {}, text = R.string.preview_text, enabled = false)
    }
}

@Preview(showBackground = true)
@Composable
fun ConstoraButtonNotFilledPreview() {
    ConstoraBankTheme {
        ConstoraButton(onClick = {}, text = R.string.preview_text, filled = false)
    }
}

@Preview(showBackground = true)
@Composable
fun ConstoraButtonNotFilledDisabledPreview() {
    ConstoraBankTheme {
        ConstoraButton(onClick = {}, text = R.string.preview_text, enabled = false, filled = false)
    }
}