package com.example.constorabank.feature.createaccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens
import com.example.constorabank.core.designsystem.components.ConstoraButton
import com.example.constorabank.core.designsystem.components.ConstoraExtraSmallPromptText
import com.example.constorabank.core.designsystem.components.ConstoraOutlinedTextField
import com.example.constorabank.core.designsystem.components.ConstoraPage
import com.example.constorabank.core.designsystem.components.ConstoraSubtitleText
import com.example.constorabank.core.designsystem.components.ConstoraTextButton
import com.example.constorabank.core.designsystem.components.ConstoraTitleText

private const val MIN_PASSWORD_LENGTH = 8
private const val EMAIL_MUST_CONTAIN = "@"

@Composable
fun CreateAccountScreen(
    onContinue: (email: String, password: String) -> Unit,
    onSignInClick: () -> Unit,
) {
    ConstoraBankTheme {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        val isValid = email.contains(EMAIL_MUST_CONTAIN)
                && password.length >= MIN_PASSWORD_LENGTH
        val focusManager = LocalFocusManager.current

        ConstoraPage {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = Dimens.PaddingMedium),
                horizontalAlignment = Alignment.Start,
            ) {
                ConstoraTitleText(R.string.create_account)

                Spacer(Modifier.height(Dimens.SpacerMedium))

                ConstoraOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(stringResource(R.string.email)) },
                    placeholder = { Text(stringResource(R.string.example_email)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                Spacer(Modifier.height(Dimens.SpacerSmall))

                ConstoraOutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.password)) },
                    placeholder = { Text(stringResource(R.string.placeholder_dots)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (isValid) onContinue(email.trim(), password)
                            else focusManager.clearFocus()
                        }
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.height(Dimens.SpacerSmall))

                ConstoraExtraSmallPromptText(
                    text = R.string.password_requirements,
                )

                Spacer(Modifier.height(Dimens.SpacerMedium))

                ConstoraButton(
                    onClick = { onContinue(email.trim(), password) },
                    text = R.string.continue_on,
                    enabled = isValid,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ConstoraSubtitleText(R.string.already_have_an_account)

                    Spacer(Modifier.width(Dimens.SpacerXs))

                    ConstoraTextButton(
                        textRes = R.string.sign_in,
                        onClick = onSignInClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateAccountPreview() {
    CreateAccountScreen(
        onContinue = { _, _ -> },
        onSignInClick = {}
    )
}