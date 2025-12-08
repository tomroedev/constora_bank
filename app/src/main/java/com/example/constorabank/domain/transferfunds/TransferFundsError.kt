package com.example.constorabank.domain.transferfunds

enum class TransferFundsError(val userMessage: String) {
    UNKNOWN_ERROR("An unknown error occurred. Funds have not been transferred."),
    INVALID_AMOUNT("Invalid amount. Funds have not been transferred."),
    INSUFFICIENT_FUNDS("Insufficient funds to make this transfer."),
    INTERNAL_ERROR("Internal error. Please contact support.")
}