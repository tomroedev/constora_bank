package com.example.constorabank.domain.getbalance

import com.example.constorabank.core.common.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val repository: GetBalanceRepository,
    @IoDispatcher private val io: CoroutineDispatcher
) {
    suspend operator fun invoke(): GetBalanceResult =
        withContext(io) {
            repository.getBalance()
        }
}