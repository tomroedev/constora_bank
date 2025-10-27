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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.Dimens

// To Be Completed
@Composable
fun LoginScreen(paddingValues: PaddingValues) {
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
            stringResource(R.string.login_screen),
            modifier = Modifier
                .padding(Dimens.PaddingMedium),
            fontSize = 30.sp
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        PaddingValues()
    )
}