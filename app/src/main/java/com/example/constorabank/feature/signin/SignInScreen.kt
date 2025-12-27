package com.example.constorabank.feature.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
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
import com.example.constorabank.core.common.util.Validation
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens
import com.example.constorabank.core.designsystem.components.ConstoraButton
import com.example.constorabank.core.designsystem.components.ConstoraCredentialOutlinedTextField
import com.example.constorabank.core.designsystem.components.ConstoraPage
import com.example.constorabank.core.designsystem.components.ConstoraTitleText
import com.example.constorabank.domain.auth.SignInError
import com.example.constorabank.domain.auth.SignInResult

@Composable
fun SignInScreen(
    onContinueSuccess: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    var password by remember { mutableStateOf("") }
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val isValid by remember(email, password) {
        derivedStateOf {
            Validation.areSignInDetailsValid(email, password)
        }
    }
    val focusManager = LocalFocusManager.current
    var showFailureDialog by rememberSaveable { mutableStateOf(false) }
    var signInError by rememberSaveable { mutableStateOf(SignInError.UNKNOWN.userMessage) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.signInResult.collect { signInResult ->
            when (signInResult) {
                is SignInResult.Success -> {
                    onContinueSuccess()
                    Toast.makeText(context, context.getString(R.string.signed_in_successfully), Toast.LENGTH_LONG).show()
                }

                is SignInResult.Failure -> {
                    signInError = signInResult.error.userMessage
                    showFailureDialog = true
                }
            }
        }
    }

    if (showFailureDialog) {
        AlertDialog(
            onDismissRequest = { showFailureDialog = false },
            title = { Text(stringResource(R.string.signing_in_failed)) },
            text = { Text(signInError) },
            confirmButton = {
                TextButton(onClick = { showFailureDialog = false }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }

    SignInContent(
        email = email,
        onEmailChange = viewModel::onEmailChanged,
        password = password,
        onPasswordChange = { password = it },
        isLoading = isLoading,
        onContinue = {
            if (isValid) {
                viewModel.signIn(email, password)
                focusManager.clearFocus()
            }
        },
        isValid = isValid,
    )
}

@Composable
fun SignInContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isLoading: Boolean,
    onContinue: () -> Unit,
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
            ConstoraTitleText(R.string.sign_in)

            Spacer(Modifier.height(Dimens.SpacerMedium))

            // Email
            ConstoraCredentialOutlinedTextField(
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
            ConstoraCredentialOutlinedTextField(
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

            Spacer(Modifier.height(Dimens.SpacerMedium))

            // Continue button
            ConstoraButton(
                onClick = onContinue,
                text = R.string.continue_on,
                enabled = isValid && !isLoading
            )

            // Show loading text while signing in or in preview mode
            if (isLoading || LocalInspectionMode.current) {
                Spacer(Modifier.height(Dimens.SpacerSmall))
                Text(
                    text = stringResource(R.string.signing_in),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInPreview() {
    ConstoraBankTheme {
        SignInContent(
            email = "user@example.com",
            onEmailChange = {},
            password = "password",
            onPasswordChange = {},
            onContinue = {},
            isLoading = false,
            isValid = true,
        )
    }
}