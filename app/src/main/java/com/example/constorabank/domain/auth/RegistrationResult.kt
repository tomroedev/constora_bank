package com.example.constorabank.domain.auth

/**
 * Closed set of outcomes for registration flows.
 */
sealed interface RegistrationResult {
    data object Success : RegistrationResult
    data class Failure(val error: RegistrationError) : RegistrationResult
}