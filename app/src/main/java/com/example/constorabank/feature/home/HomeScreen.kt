package com.example.constorabank.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens
import com.example.constorabank.core.designsystem.components.ConstoraAccountBalanceCard
import com.example.constorabank.core.designsystem.components.ConstoraButton
import com.example.constorabank.core.designsystem.components.ConstoraPage

@Composable
fun HomeScreen(
    onTransferFundsClick: () -> Unit
) {
    ConstoraPage {
        Column(
            modifier = Modifier.padding(top = Dimens.PaddingXXL)
        ) {
            ConstoraAccountBalanceCard(stringResource(R.string.preview_decimal))

            Spacer(Modifier.height(Dimens.SpacerMedium))

            ConstoraButton(
                onClick = onTransferFundsClick,
                text = R.string.transfer_funds
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    ConstoraBankTheme {
        HomeScreen({})
    }
}