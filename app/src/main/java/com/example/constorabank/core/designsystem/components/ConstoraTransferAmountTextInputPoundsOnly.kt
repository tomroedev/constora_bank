package com.example.constorabank.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.constorabank.core.common.util.L
import com.example.constorabank.core.designsystem.Dimens
import kotlinx.coroutines.launch

@Composable
fun ConstoraTransferAmountTextInputPoundsOnly(
    modifier: Modifier = Modifier,
    pounds: String,
    onPoundsChange: (String) -> Unit
) {
    val shape = RoundedCornerShape(Dimens.CornerRadius)

    val focusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .clip(shape)
            .clickable {
                coroutineScope.launch {
                    // Clicking anywhere on the Surface gives the input focus and opens the keyboard
                    focusManager.clearFocus(force = true)
                    focusRequester.requestFocus()
                    keyboardController?.show()
                }
            },
        shape = shape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(Dimens.BorderStrokeWidth, MaterialTheme.colorScheme.outline),
        shadowElevation = 0.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = modifier.padding(Dimens.PaddingSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "£",
                style = MaterialTheme.typography.displayMedium
            )

            BasicTextField(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .padding()
                    .width(IntrinsicSize.Min)
                    .focusRequester(focusRequester)
                    // Clear the initial "0" when the field gains focus so the user can start typing immediately.
                    .onFocusChanged { focusState ->
                        L.d("onFocusChanged: focus: $focusState, pounds: $pounds")
                        if (focusState.isFocused && pounds == "0") {
                            onPoundsChange("")
                        }
                    },
                value = pounds,
                onValueChange = { input ->
                    onPoundsChange(input)
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.displayMedium
                    .copy(color = MaterialTheme.colorScheme.onSurface),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConstoraTransferAmountTextInputPoundsOnlyPreview_empty_value() {
    var pounds by remember { mutableStateOf("") }

    MaterialTheme {
        ConstoraTransferAmountTextInputPoundsOnly(
            pounds = pounds,
            onPoundsChange = { pounds = it }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConstoraTransferAmountTextInputPoundsOnlyPreview_with_value() {
    var pounds by remember { mutableStateOf("123") }

    MaterialTheme {
        ConstoraTransferAmountTextInputPoundsOnly(
            pounds = pounds,
            onPoundsChange = { pounds = it }
        )
    }
}