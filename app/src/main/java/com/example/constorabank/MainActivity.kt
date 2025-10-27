package com.example.constorabank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.feature.createaccount.CreateAccountScreen
import com.example.constorabank.feature.login.LoginScreen
import com.example.constorabank.feature.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConstoraBankTheme {
                val bg = MaterialTheme.colorScheme.background.toArgb()

                SideEffect {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.light(
                            scrim = bg,
                            darkScrim = bg
                        ),
                        navigationBarStyle = SystemBarStyle.light(
                            scrim = bg,
                            darkScrim = bg
                        )
                    )
                }

                AppNavHost()
            }
        }
    }
}

@Composable
fun AppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = Destinations.Welcome) {
        composable(Destinations.Welcome) {
            WelcomeScreen(
                onCreateAccount = { nav.navigate(Destinations.CreateAccount) },
                onLogin = { nav.navigate(Destinations.Login) }
            )
        }
        composable(Destinations.CreateAccount) {
            CreateAccountScreen(
                onContinue = { _, _ -> nav.navigate(Destinations.VerifyEmail) },
                onSignInClick = { nav.navigate(Destinations.Login) }
            )
        }
        composable(Destinations.Login) {
            LoginScreen(
                paddingValues = PaddingValues(32.dp)
            )
        }
    }
}

object Destinations {
    const val Welcome = "welcome"
    const val CreateAccount = "create_account"
    const val VerifyEmail = "verify_email"
    const val SetPin = "set_pin"
    const val QuickUnlock = "quick_unlock"
    const val Login = "login"
    const val Home = "home"
    const val TransferFunds = "transfer_funds"
    const val TransferComplete = "transfer_complete"
}
