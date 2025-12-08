package com.example.constorabank.domain.transferfunds

interface TransferFundsRepository {
    suspend fun transferFunds(amount: Long): TransferFundsResult
}