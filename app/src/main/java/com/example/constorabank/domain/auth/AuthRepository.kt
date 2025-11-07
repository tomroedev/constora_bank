package com.example.constorabank.domain.auth

/**
 * Hides the backend (Firebase or anything else) from the rest of the app.
 */
interface AuthRepository {
    /**
     * Attempts to create user credentials for the given identifier and secret.
     * Returns domain-friendly results.
     */
    suspend fun createUser(email: String, password: String): RegistrationResult
}