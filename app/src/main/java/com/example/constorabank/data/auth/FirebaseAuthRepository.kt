package com.example.constorabank.data.auth

import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.auth.AuthRepository
import com.example.constorabank.domain.auth.RegistrationError
import com.example.constorabank.domain.auth.RegistrationResult
import com.example.constorabank.domain.auth.SignInError
import com.example.constorabank.domain.auth.SignInResult
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
            L.e("Create User Auth error: ${e.errorCode} ${e.message}", e)
            RegistrationResult.Failure(mapFirebaseCreateAccountError(e))
        } catch (e: FirebaseNetworkException) {
            L.e("Create User Network error: ${e.message}", e)
            RegistrationResult.Failure(RegistrationError.NETWORK_FAILURE)
        } catch (e: Exception) {
            L.e("Create User Unexpected error", e)
            RegistrationResult.Failure(RegistrationError.UNKNOWN)
        }
    }

    override suspend fun signIn(email: String, password: String): SignInResult {
        L.i("Sign in with email: $email")
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            SignInResult.Success
        } catch (e: FirebaseNetworkException) {
            L.e("Network error during sign in: ${e.message}", e)
            SignInResult.Failure(SignInError.ERROR_NETWORK_REQUEST_FAILED)
        } catch (e: FirebaseAuthException) {
            L.e("Sign In auth error: ${e.errorCode} ${e.message}", e)
            SignInResult.Failure(mapFirebaseSignInError(e))
        } catch (e: Exception) {
            L.e("Unexpected error during sign in", e)
            SignInResult.Failure(SignInError.UNKNOWN)
        }
    }

    private fun mapFirebaseCreateAccountError(e: FirebaseAuthException): RegistrationError =
        when (e.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> RegistrationError.CREDENTIAL_CONFLICT
            "ERROR_INVALID_EMAIL" -> RegistrationError.IDENTIFIER_INVALID
            "ERROR_WEAK_PASSWORD" -> RegistrationError.CREDENTIAL_WEAK
            else -> RegistrationError.UNKNOWN
        }

    private fun mapFirebaseSignInError(e: FirebaseAuthException): SignInError =
        when (e.errorCode) {
            "ERROR_INVALID_EMAIL" -> SignInError.ERROR_INVALID_CREDENTIALS
            "ERROR_USER_NOT_FOUND" -> SignInError.ERROR_CREDENTIALS_NOT_FOUND
            "ERROR_WRONG_PASSWORD" -> SignInError.ERROR_WRONG_PASSWORD
            "ERROR_TOO_MANY_REQUESTS" -> SignInError.ERROR_TOO_MANY_REQUESTS
            "ERROR_NETWORK_REQUEST_FAILED" -> SignInError.ERROR_NETWORK_REQUEST_FAILED
            else -> SignInError.UNKNOWN
        }
}