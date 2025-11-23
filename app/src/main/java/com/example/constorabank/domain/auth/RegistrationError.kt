package com.example.constorabank.domain.auth

/**
 * Registration error taxonomy for email/phone/passkeys/etc.
 */
enum class RegistrationError(val userMessage: String) {
    IDENTIFIER_INVALID("Invalid identifier format."),
    CREDENTIAL_WEAK("Password is too weak."),
    CREDENTIAL_CONFLICT("Already registered."),
    NETWORK_FAILURE("Network error. Please check your connection."),
    UNKNOWN("An unknown error occurred.")
}