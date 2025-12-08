package com.example.constorabank.domain.transferfunds

import com.example.constorabank.core.common.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Coordinates fund transfer logic on a background thread.
 * The repository handles the actual work.
 */
class TransferFundsUseCase @Inject constructor(
    private val repository: TransferFundsRepository,
    @IoDispatcher private val io: CoroutineDispatcher
) {
    suspend operator fun invoke(amount: Long): TransferFundsResult =
        withContext(io) {
            repository.transferFunds(amount)
        }
}