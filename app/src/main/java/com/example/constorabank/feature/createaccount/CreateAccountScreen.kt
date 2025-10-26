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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.components.ConstoraButton
import com.example.constorabank.core.designsystem.components.ConstoraExtraSmallPromptText
import com.example.constorabank.core.designsystem.components.ConstoraOutlinedTextField
import com.example.constorabank.core.designsystem.components.ConstoraPage
import com.example.constorabank.core.designsystem.components.ConstoraSubtitleText
import com.example.constorabank.core.designsystem.components.ConstoraTitleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    onContinue: (email: String, password: String) -> Unit,
    onSignInClick: () -> Unit,
) {
    ConstoraBankTheme {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        val isValid = email.contains("@") && password.length >= 8
        val focusManager = LocalFocusManager.current

        ConstoraPage {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                ConstoraTitleText("Create account")

                Spacer(Modifier.height(24.dp))

                ConstoraOutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("email") },
                    placeholder = { Text("you@example.com") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )

                Spacer(Modifier.height(12.dp))

                ConstoraOutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("password") },
                    placeholder = { Text("••••••••") },
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

                Spacer(Modifier.height(8.dp))

                ConstoraExtraSmallPromptText(
                    text = "8+ chars, mix upper/lower/number",
                )

                Spacer(Modifier.height(16.dp))

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
                    ConstoraSubtitleText("Already have an account?")

                    Spacer(Modifier.width(4.dp))

                    TextButton(
                        onClick = onSignInClick
                    ) {
                        Text(
                            "Sign in",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateAccountPreview() {
    CreateAccountScreen(onContinue = { _, _ -> }, onSignInClick = {})
}