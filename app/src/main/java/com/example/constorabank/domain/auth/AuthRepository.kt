package com.example.constorabank.domain.auth

/**
 * Hides the backend (Firebase or anything else) from the rest of the app.
 */
interface AuthRepository {
    suspend fun createUser(email: String, password: String): RegistrationResult

    suspend fun signIn(email: String, password: String): SignInResult
}