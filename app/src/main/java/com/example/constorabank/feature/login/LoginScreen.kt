package com.example.constorabank.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.constorabank.core.L

@Composable
fun LoginScreen(paddingValues: PaddingValues) {
    L.d("LoginScreen enter")
    Login(paddingValues)
}

@Composable
fun Login(paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        Text(
            "Login Screen",
            modifier = Modifier
                .padding(32.dp),
            fontSize = 30.sp
        )

    }
}