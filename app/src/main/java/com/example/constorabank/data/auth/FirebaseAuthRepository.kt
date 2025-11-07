package com.example.constorabank.data.auth

import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.auth.AuthRepository
import com.example.constorabank.domain.auth.RegistrationError
import com.example.constorabank.domain.auth.RegistrationResult
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun createUser(email: String, password: String): RegistrationResult {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            RegistrationResult.Success
        } catch (e: FirebaseAuthException) {
            L.e("Auth error: ${e.errorCode} ${e.message}", e)
            RegistrationResult.Failure(mapError(e))
        } catch (e: FirebaseNetworkException) {
            L.e("Network error: ${e.message}", e)
            RegistrationResult.Failure(RegistrationError.NETWORK_FAILURE)
        } catch (e: Exception) {
            L.e("Unexpected error", e)
            RegistrationResult.Failure(RegistrationError.UNKNOWN)
        }
    }

    private fun mapError(e: FirebaseAuthException): RegistrationError =
        when (e.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> RegistrationError.CREDENTIAL_CONFLICT
            "ERROR_INVALID_EMAIL" -> RegistrationError.IDENTIFIER_INVALID
            "ERROR_WEAK_PASSWORD" -> RegistrationError.CREDENTIAL_WEAK
            else -> RegistrationError.UNKNOWN
        }
}