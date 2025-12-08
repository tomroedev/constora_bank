package com.example.constorabank.domain.transferfunds

sealed interface TransferFundsResult {
    data object Success : TransferFundsResult
    data class Failure(val error: TransferFundsError) : TransferFundsResult
}