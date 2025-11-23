package com.example.constorabank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.feature.createaccount.CreateAccountScreen
import com.example.constorabank.feature.home.HomeScreen
import com.example.constorabank.feature.signin.SignInScreen
import com.example.constorabank.feature.transferfunds.TransferFundsScreen
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
                onSignIn = { nav.navigate(Destinations.SignIn) }
            )
        }
        composable(Destinations.CreateAccount) {
            CreateAccountScreen(
                onContinueSuccess = { nav.navigate(Destinations.SignIn) },
                onSignInClick = { nav.navigate(Destinations.SignIn) }
            )
        }
        composable(Destinations.SignIn) {
            SignInScreen(
                onContinueSuccess = { nav.navigate(Destinations.Home) }
            )
        }
        composable(Destinations.Home) {
            HomeScreen(
                onTransferFundsClick = { nav.navigate((Destinations.TransferFunds)) }
            )
        }
        composable(Destinations.TransferFunds) {
            TransferFundsScreen()
        }
    }
}

object Destinations {
    const val Welcome = "welcome"
    const val CreateAccount = "create_account"
    const val SignIn = "sign_in"
    const val Home = "home"
    const val TransferFunds = "transfer_funds"
}
