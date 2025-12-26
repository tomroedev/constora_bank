package com.example.constorabank.domain.getbalance

interface GetBalanceRepository {
    suspend fun getBalance(): GetBalanceResult
}