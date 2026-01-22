package com.example.constorabank.feature.createaccount

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.constorabank.core.common.util.Validation
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateAccountScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun continue_disabled_when_invalid() {
        rule.setContent {
            ConstoraBankTheme {
                CreateAccountContent(
                    email = "",
                    onEmailChange = {},
                    password = "",
                    onPasswordChange = {},
                    isLoading = false,
                    onContinue = {},
                    onSignInClick = {},
                    isValid = false,
                )
            }
        }

        rule.onNodeWithTag("create_account_continue_button")
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun can_type_email_and_password_and_enable_continue() {
        rule.setContent {
            ConstoraBankTheme {
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }

                val isValid =
                    Validation.areCreateAccountCredentialsValid(email.value, password.value)

                CreateAccountContent(
                    email = email.value,
                    onEmailChange = { email.value = it },
                    password = password.value,
                    onPasswordChange = { password.value = it },
                    isLoading = false,
                    onContinue = {},
                    onSignInClick = {},
                    isValid = isValid,
                )
            }
        }

        rule.onNodeWithTag("create_account_email_field")
            .performTextInput("user@example.com")

        rule.onNodeWithTag("create_account_password_field")
            .performTextInput("Password123!")

        rule.onNodeWithTag("create_account_continue_button")
            .assertIsEnabled()
    }

    @Test
    fun fields_disabled_while_loading() {
        rule.setContent {
            ConstoraBankTheme {
                CreateAccountContent(
                    email = "user@example.com",
                    onEmailChange = {},
                    password = "Password123!",
                    onPasswordChange = {},
                    isLoading = true,
                    onContinue = {},
                    onSignInClick = {},
                    isValid = true,
                )
            }
        }

        rule.onNodeWithTag("create_account_email_field").assertIsNotEnabled()
        rule.onNodeWithTag("create_account_password_field").assertIsNotEnabled()
        rule.onNodeWithTag("create_account_continue_button").assertIsNotEnabled()
        rule.onNodeWithTag("create_account_loading_text").assertIsDisplayed()
    }

}