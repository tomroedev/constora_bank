package com.example.constorabank.domain.auth

/**
 * Registration error taxonomy for email/phone/passkeys/etc.
 */
enum class RegistrationError {
    IDENTIFIER_INVALID,   // e.g. invalid email format
    CREDENTIAL_WEAK,      // password too weak / rules not met
    CREDENTIAL_CONFLICT,  // identifier already registered
    NETWORK_FAILURE,      // connectivity / timeout
    UNKNOWN               // anything unmapped
}