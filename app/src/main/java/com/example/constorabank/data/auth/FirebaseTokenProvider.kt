package com.example.constorabank.data.auth

import com.example.constorabank.core.common.util.L
import com.example.constorabank.domain.auth.TokenProvider
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class FirebaseTokenProvider @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : TokenProvider {
    override fun getToken(): String? {
        val user = firebaseAuth.currentUser ?: run {
            L.e("FirebaseTokenProvider: No current user, cannot get ID token")
            return null
        }

        return try {
            val result = Tasks.await(user.getIdToken(false))
            val token = result.token
            if (token == null) {
                L.e("FirebaseTokenProvider: Firebase returned null ID token")
            }
            token
        } catch (e: FirebaseNetworkException) {
            L.e("FirebaseTokenProvider: Network error getting ID token: ${e.message}")
            null
        } catch (e: FirebaseAuthException) {
            L.e("FirebaseTokenProvider: Auth error getting ID token: ${e.message}")
            null
        } catch (e: CancellationException) {
            // Just rethrow if you want cancellation to propagate
            L.e("FirebaseTokenProvider: Token request was cancelled: ${e.message}")
            throw e
        } catch (e: Exception) {
            L.e("FirebaseTokenProvider: Unexpected error getting ID token: ${e.message}")
            null
        }
    }
}