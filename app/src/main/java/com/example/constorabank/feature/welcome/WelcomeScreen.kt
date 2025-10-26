package com.example.constorabank.feature.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.Accent300
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Primary500
import com.example.constorabank.core.designsystem.Secondary500
import com.example.constorabank.core.designsystem.Secondary700
import com.example.constorabank.core.designsystem.components.ConstoraPage

@Composable
fun WelcomeScreen(
    onCreateAccount: () -> Unit,
    onLogin: () -> Unit,
) {
    ConstoraBankTheme {
        ConstoraPage {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 128.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_shield),
                    contentDescription = "Constora logo",
                    modifier = Modifier
                        .size(128.dp)
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "Welcome to Constora",
                    color = Secondary700,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Modern banking, minus the faff.",
                    color = Secondary500,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(28.dp))

                // Primary button
                Button(
                    onClick = onCreateAccount,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary500,
                        contentColor = Color.White
                    )
                ) {
                    Text("Create account", fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(12.dp))

                // Secondary button (white with border)
                Button(
                    onClick = onLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Accent300),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Secondary700
                    )
                ) {
                    Text("I already have an account", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WelcomePreview() {
    WelcomeScreen(onCreateAccount = {}, onLogin = {})
}
