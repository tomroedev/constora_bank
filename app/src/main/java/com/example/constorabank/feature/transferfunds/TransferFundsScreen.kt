package com.example.constorabank.feature.transferfunds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.constorabank.R
import com.example.constorabank.core.common.util.L
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens
import com.example.constorabank.core.designsystem.components.ConstoraButton
import com.example.constorabank.core.designsystem.components.ConstoraPage
import com.example.constorabank.core.designsystem.components.ConstoraSubtitleText
import com.example.constorabank.core.designsystem.components.ConstoraTransferAmountTextInputPoundsOnly
import com.example.constorabank.domain.transferfunds.TransferFundsError
import com.example.constorabank.domain.transferfunds.TransferFundsResult

@Composable
fun TransferFundsScreen(
    viewModel: TransferFundsViewModel = hiltViewModel(),
    returnToHomeScreen: () -> Unit
) {
    val transferAmount by viewModel.transferAmount.collectAsStateWithLifecycle()
    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }
    var showFailureDialog by rememberSaveable { mutableStateOf(false) }
    var transferFundsError by rememberSaveable { mutableStateOf(TransferFundsError.UNKNOWN_ERROR.userMessage) }

    LaunchedEffect(Unit) {
        viewModel.transferAPIResult.collect { transferAPIResult ->
            when (transferAPIResult) {
                is TransferFundsResult.Failure -> {
                    transferFundsError = transferAPIResult.error.userMessage
                    showFailureDialog = true
                }
                is TransferFundsResult.Success -> {
                    showSuccessDialog = true
                }
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                returnToHomeScreen()
            },
            title = { Text(stringResource(R.string.success)) },
            text = { Text(stringResource(R.string.transfer_success)) },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    returnToHomeScreen()
                }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    if (showFailureDialog) {
        AlertDialog(
            onDismissRequest = {
                showFailureDialog = false
                returnToHomeScreen()
            },
            title = { Text(stringResource(R.string.failure)) },
            text = { Text(transferFundsError) },
            confirmButton = {
                TextButton(onClick = {
                    showFailureDialog = false
                    returnToHomeScreen()
                }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    TransferFundsContent(
        transferAmount = transferAmount,
        onValueChange = viewModel::onTransferAmountChanged,
        onCompleteTransferSelected = {
            transferAmount.toLongOrNull()?.let { viewModel.makeTransfer(it) }
                ?: L.i("Invalid transfer amount: $transferAmount")
        }
    )
}

@Composable
private fun TransferFundsContent(
    transferAmount: String,
    onValueChange: (String) -> Unit,
    onCompleteTransferSelected: () -> Unit
) {
    ConstoraPage {
        Column(
            modifier = Modifier
                .padding(top = Dimens.PaddingXXL)
                .statusBarsPadding()
        ) {
            ConstoraSubtitleText(R.string.amount_to_transfer)

            Spacer(modifier = Modifier.height(Dimens.SpacerMedium))

            ConstoraTransferAmountTextInputPoundsOnly(
                modifier = Modifier,
                pounds = transferAmount,
                onPoundsChange = onValueChange
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerMedium))

            ConstoraButton(
                onClick = {
                    onCompleteTransferSelected()
                },
                text = R.string.complete_transfer
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun TransferFundsScreenPreview() {
    ConstoraBankTheme {
        TransferFundsContent(
            "237",
            {},
            {})
    }
}