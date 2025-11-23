package com.example.constorabank.domain.auth

import com.example.constorabank.core.common.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val io: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String): SignInResult =
        withContext(io) {
            repository.signIn(email.trim(), password)
        }
}