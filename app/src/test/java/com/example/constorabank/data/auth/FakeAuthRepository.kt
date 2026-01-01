package com.example.constorabank.data.auth

import com.example.constorabank.domain.auth.AuthRepository
import com.example.constorabank.domain.auth.RegistrationError
import com.example.constorabank.domain.auth.RegistrationResult
import com.example.constorabank.domain.auth.SignInResult

/**
 * Fake AuthRepository for tests.
 * It returns a preconfigured result for createUser and records the last email/password.
 */
class FakeAuthRepository(
    private val resultToReturn: RegistrationResult = RegistrationResult.Success,
    private val throwOnCreateUser: Boolean = false,
) : AuthRepository {

    var lastEmail: String? = null
        private set

    var lastPassword: String? = null
        private set

    override suspend fun createUser(email: String, password: String): RegistrationResult {
        lastEmail = email
        lastPassword = password

        if (throwOnCreateUser) {
            throw RuntimeException("createUser test exception")
        }
        return resultToReturn
    }

    // Not used in CreateAccountViewModel tests, so keep it simple.
    override suspend fun signIn(email: String, password: String): SignInResult {
        // You can return a default, or even throw if used unexpectedly.
        return SignInResult.Success
    }

    companion object {
        fun success() = FakeAuthRepository(RegistrationResult.Success)

        fun failure(error: RegistrationError) =
            FakeAuthRepository(RegistrationResult.Failure(error))

        fun throws() = FakeAuthRepository(throwOnCreateUser = true)
    }
}