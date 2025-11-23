package com.example.constorabank.feature.transferfunds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens
import com.example.constorabank.core.designsystem.components.ConstoraButton
import com.example.constorabank.core.designsystem.components.ConstoraPage
import com.example.constorabank.core.designsystem.components.ConstoraSubtitleText
import com.example.constorabank.core.designsystem.components.ConstoraTransferAmountOutlinedTextField

// TODO - this is the next class to be worked on.
@Composable
fun TransferFundsScreen() {

    ConstoraPage {
        Column(
            modifier = Modifier
                .padding(top = Dimens.PaddingXXL)
                .statusBarsPadding()
        ) {
            ConstoraSubtitleText(R.string.amount_to_transfer)

            Spacer(modifier = Modifier.height(Dimens.SpacerMedium))

            ConstoraTransferAmountOutlinedTextField(
                value = "50.00",
                onValueChange = {},
                placeholder = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(Dimens.SpacerMedium))

            ConstoraButton(
                onClick = {},
                text = R.string.complete_transfer
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun TransferFundsScreenPreview() {
    ConstoraBankTheme {
        TransferFundsScreen()
    }
}