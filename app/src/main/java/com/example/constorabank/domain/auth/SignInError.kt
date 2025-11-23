package com.example.constorabank.domain.auth

enum class SignInError(val userMessage: String) {
    ERROR_INVALID_CREDENTIALS("Invalid email or password."),
    ERROR_CREDENTIALS_NOT_FOUND("Credentials not found."),
    ERROR_WRONG_PASSWORD("Incorrect password."),
    ERROR_TOO_MANY_REQUESTS("Please try logging in again later."),
    ERROR_NETWORK_REQUEST_FAILED("Network error. Please check your connection."),
    UNKNOWN("An unknown error occurred.")
}