package com.example.constorabank.domain.getbalance

enum class GetBalanceError(val userMessage: String) {
    UNKNOWN_ERROR("Unknown error occurred."),
    INTERNAL_ERROR("Internal error. Please contact support.")
}