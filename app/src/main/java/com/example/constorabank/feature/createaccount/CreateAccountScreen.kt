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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.example.constorabank.domain.auth.RegistrationResult

/**
 * Create an account with an email address and password.
 * Email is stored and will persist for configuration changes etc.
 * Password is ephemeral for security.
 */
@Composable
fun CreateAccountScreen(
    onContinueSuccess: () -> Unit,
    onSignInClick: () -> Unit,
    viewModel: CreateAccountViewModel = hiltViewModel()
) {
    ConstoraBankTheme {
        val email by viewModel.email.collectAsStateWithLifecycle()
        var password by remember { mutableStateOf("") }
        val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
        val isValid = viewModel.areCredentialsValid(email, password)
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            viewModel.registrationResult.collect { registrationResult ->
                when (registrationResult) {
                    is RegistrationResult.Success -> {
                        // TODO - show a Dialog
                    }
                    is RegistrationResult.Failure -> {
                        // TODO - show a Dialog
                    }
                }
            }
        }

        CreateAccountContent(
            email = email,
            onEmailChange = viewModel::onEmailChanged,
            password = password,
            onPasswordChange = { password = it },
            isLoading = isLoading,
            onContinue = {
                if (isValid) {
                    viewModel.createAccount(email, password)
                    focusManager.clearFocus()
                }
            },
            onSignInClick = onSignInClick,
            isValid = isValid,
        )
    }
}

/**
 * Extracted UI content so previews can render without needing a real ViewModel or DI.
 */
@Composable
fun CreateAccountContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isLoading: Boolean,
    onContinue: () -> Unit,
    onSignInClick: () -> Unit,
    isValid: Boolean,
) {
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

            // Email
            ConstoraOutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                enabled = !isLoading,
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

            // Password
            ConstoraOutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                enabled = !isLoading,
                label = { Text(stringResource(R.string.password)) },
                placeholder = { Text(stringResource(R.string.placeholder_dots)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onContinue()
                        focusManager.clearFocus()
                    }
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(Dimens.SpacerSmall))

            ConstoraExtraSmallPromptText(
                text = R.string.password_requirements
            )

            Spacer(Modifier.height(Dimens.SpacerMedium))

            // Continue button
            ConstoraButton(
                onClick = onContinue,
                text = R.string.continue_on,
                enabled = isValid && !isLoading
            )

            // Show loading text while signing up or in preview mode.
            if (isLoading || LocalInspectionMode.current) {
                Spacer(Modifier.height(Dimens.SpacerSmall))
                Text(
                    text = stringResource(R.string.creating_account),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }

            Spacer(Modifier.height(Dimens.SpacerMedium))

            // Option to sign in if they already have an account
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                ConstoraSubtitleText(R.string.already_have_an_account)
                Spacer(Modifier.width(Dimens.SpacerXs))
                ConstoraTextButton(
                    textRes = R.string.sign_in,
                    onClick = { onSignInClick() } ,
                    enabled = !isLoading
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateAccountPreview() {
    ConstoraBankTheme {
        CreateAccountContent(
            email = "user@example.com",
            onEmailChange = {},
            password = "password",
            onPasswordChange = {},
            onContinue = {},
            onSignInClick = {},
            isLoading = false,
            isValid = true,
        )
    }
}