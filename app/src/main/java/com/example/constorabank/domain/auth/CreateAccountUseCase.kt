package com.example.constorabank.domain.auth

import com.example.constorabank.core.common.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Coordinates user-creation logic on a background thread.
 * The repository handles actual work.
 */
class CreateAccountUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher private val io: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String): RegistrationResult =
        withContext(io) {
            repository.createUser(email.trim(), password)
        }
}