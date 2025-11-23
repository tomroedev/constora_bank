package com.example.constorabank.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens

@Composable
fun ConstoraAccountBalanceCard(
    value: String
) {
    Card(
        shape = RoundedCornerShape(Dimens.CornerRadius),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    Dimens.BorderStrokeWidth,
                    MaterialTheme.colorScheme.onBackground
                ),
                shape = RoundedCornerShape(Dimens.CornerRadius)
            )

    ) {
        Column(modifier = Modifier.padding(Dimens.PaddingSmall)) {
            Text(
                text = stringResource(R.string.available_balance),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(Dimens.SpacerSmall))

            Row {
                Text(
                    text = stringResource(R.string.pound_symbol),
                    style = MaterialTheme.typography.displayMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayMedium,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConstoraAccountBalanceCardPreview() {
    ConstoraBankTheme {
        ConstoraPage {
            ConstoraAccountBalanceCard(
                value = stringResource(R.string.preview_decimal_number)
            )
        }
    }
}