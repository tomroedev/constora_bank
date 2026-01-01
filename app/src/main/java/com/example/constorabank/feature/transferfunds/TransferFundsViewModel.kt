package com.example.constorabank.feature.transferfunds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.transferfunds.TransferFundsError
import com.example.constorabank.domain.transferfunds.TransferFundsResult
import com.example.constorabank.domain.transferfunds.TransferFundsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferFundsViewModel @Inject constructor(
    private val transferFundsUseCase: TransferFundsUseCase
) : ViewModel() {

    // Using a String state so an empty input can be represented and displayed as empty
    private val _transferAmount = MutableStateFlow("0")
    val transferAmount: StateFlow<String> = _transferAmount

    private val _transferAPIResult = MutableSharedFlow<TransferFundsResult>()
    val transferAPIResult: SharedFlow<TransferFundsResult> = _transferAPIResult

    fun onTransferAmountChanged(newAmount: String) {
        val trimmedAmount = newAmount.trim()

        // Allow empty string to be shown in the UI as will be the case when the field first has focus
        if (trimmedAmount.isEmpty()) {
            _transferAmount.value = ""
            return
        }

        _transferAmount.value = trimmedAmount
        if (trimmedAmount.toLongOrNull() == null) {
            L.e("Invalid transfer amount input: $trimmedAmount")
        }
    }

    fun makeTransfer(amount: Long) {
        viewModelScope.launch {
            val result = try {
                transferFundsUseCase(amount)
            } catch (e: Exception) {
                L.e("makeTransfer(): Error calling debit-balance", e)
                TransferFundsResult.Failure(TransferFundsError.UNKNOWN_ERROR)
            }
            _transferAPIResult.emit(result)
            L.i("makeTransfer(): result: $result")
        }
    }
}