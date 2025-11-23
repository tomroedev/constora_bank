package com.example.constorabank.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.constorabank.R
import com.example.constorabank.core.designsystem.ConstoraBankTheme
import com.example.constorabank.core.designsystem.Dimens

@Composable
fun ConstoraPage (
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.PaddingMedium),
            contentAlignment = Alignment.TopCenter,
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun ConstoraPagePreview() {
    ConstoraBankTheme {
        ConstoraPage {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 128.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConstoraButton(
                    onClick = {} ,
                    text = R.string.preview_text
                )

                Spacer(Modifier.height(Dimens.SpacerSmall))

                ConstoraButton(
                    onClick = {} ,
                    text = R.string.preview_text,
                    filled = false
                )
            }
        }
    }
}