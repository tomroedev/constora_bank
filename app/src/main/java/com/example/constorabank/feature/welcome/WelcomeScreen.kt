package com.example.constorabank.feature.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens
import com.example.constorabank.core.designsystem.components.ConstoraButton
import com.example.constorabank.core.designsystem.components.ConstoraPage

@Composable
fun WelcomeScreen(
    onCreateAccount: () -> Unit,
    onSignIn: () -> Unit,
) {
    ConstoraBankTheme {
        ConstoraPage {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Dimens.PaddingXXL),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_shield),
                    contentDescription = "Constora logo",
                    modifier = Modifier
                        .size(Dimens.MainAppIcon)
                )

                Spacer(Modifier.height(Dimens.SpacerMedium))

                Text(
                    text = stringResource(R.string.welcome_to_constora),
                    style = MaterialTheme.typography.displayLarge,
                )

                Spacer(Modifier.height(Dimens.SpacerXs))

                Text(
                    text = stringResource(R.string.welcome_screen_tagline),
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(Dimens.SpacerMedium))

                ConstoraButton(
                    onClick = onCreateAccount,
                    text = R.string.create_account
                )

                Spacer(Modifier.height(Dimens.SpacerSmall))

                ConstoraButton(
                    onClick = onSignIn,
                    text = R.string.i_already_have_an_account,
                    filled = false
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomePreview() {
    WelcomeScreen(onCreateAccount = {}, onSignIn = {})
}